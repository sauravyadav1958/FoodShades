package com.example.project.order;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.project.Activity.LoginActivity;
import com.example.project.Adapter.ApiService;
import com.example.project.R;
import com.example.project.utils.GenerateRandomNum;
import com.example.project.utils.JSONParser;
import com.example.project.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// TODO payment details not connected with backend
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener /*, PaymentStatusListener, PaytmPaymentTransactionCallback*/ {

    public String TotalAmount;
    public LinearLayout mCODView, mCardView, mUpiView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String USER_LIST = "UserList";
    public String CART_ITEMS = "CartItems";
    public String USER_ORDERS = "UserOrders";
    public String[] getItemsArr, getOrderedItemsArr;
    public String upiID, resName, resUid, userAddress, mid, extraInst, userPhone, uid, userName, resSpotImage, resDelTime;
    private long customerID;
    private long orderID;
    public String totalTxt;
    TextView mAmountText;
    String token = "";

    Retrofit orderRetrofit = new Retrofit.Builder()
            .baseUrl(Utils.hostname)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //*MADE WITH LOVE BY ADESH
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        TextView mTotalAmount = findViewById(R.id.totalAmountItems);
        ImageView mGoBackBtn = findViewById(R.id.cartBackBtn);
        getItemsArr = getIntent().getStringArrayExtra("ITEM_NAMES");
        getOrderedItemsArr = getIntent().getStringArrayExtra("ITEM_ORDERED_NAME");
        userAddress = getIntent().getStringExtra("USER_ADDRESS");
        resDelTime = getIntent().getStringExtra("DELIVERY_TIME");
        extraInst = getIntent().getStringExtra("EXTRA_INS");
//        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        userName = getIntent().getStringExtra("USER_NAME");
        userPhone = getIntent().getStringExtra("USER_PHONE");
        db = FirebaseFirestore.getInstance();

       /* mTotalAmount = getIntent().getStringExtra("TOTAL_AMOUNT");
        mAmountText = findViewById(R.id.Txttotal);
        mAmountText.setText("Amount to be paid \u20b9" + mTotalAmount);*/
       /* TextView textView = findViewById(R.id.totalAmountItems);
        textView.setText(String.format(
                "Amount to be paid %s", getIntent().getStringExtra("totalamount")*/
        TextView textView = findViewById(R.id.totalAmountItems);
        textView.setText(String.format("Amount to be paid %s", getIntent().getStringExtra("total")));
        TotalAmount = getIntent().getStringExtra("total");
        showResPaymentMethods();
//        mCODView = findViewById(R.id.cashMethodContainer);
//        mCardView = findViewById(R.id.creditCardMethodContainer);
//        mUpiView=  findViewById(R.id.upiMethodContainer);
        mCODView.setOnClickListener(this);
        mCardView.setOnClickListener(this);
        mUpiView.setOnClickListener(this);

        mid = "YOUR_OWN_MID";
        // customerID = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());
        //orderID = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());

        if (ContextCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CheckoutActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
        }

        mGoBackBtn.setOnClickListener(view -> {
            this.onBackPressed();
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cashMethodContainer:
                uploadOrderDetails("COD", TotalAmount);
                //     deleteCartItems();
                break;

            case R.id.creditCardMethodContainer:
                razorpayGatewayCall();


                break;

            case R.id.upiMethodContainer:
                razorpayGatewayCall();
//                upi("UPI", TotalAmount);
                break;

        }

    }

    private void razorpayGatewayCall() {
        ApiService apiService = orderRetrofit.create(ApiService.class);
        Bundle bundle = getIntent().getExtras();

        Map<String, Object> map = new HashMap();
        bundle.keySet().forEach(k -> map.put(k, bundle.get(k)));
        JsonObject jsonObject = LoginActivity.jwtToken;
        if (jsonObject != null) {
            token = jsonObject.getAsJsonPrimitive("accessToken").getAsString();
        }
        Call<JsonObject> orderJsonObject = apiService.placeOrder("Bearer " + token, map);
        orderJsonObject.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 401) {
                    Checkout.clearUserData(getApplicationContext());
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(CheckoutActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    JsonObject orderTicket = response.body();

                    razorpayCheckout("Razorpay", orderTicket);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void showResPaymentMethods() {
//        DocumentReference restaurantRef = db.collection(USER_LIST).document(uid);
//        restaurantRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()){
//                DocumentSnapshot documentSnapshot = task.getResult();
        mCODView = findViewById(R.id.cashMethodContainer);
        mCardView = findViewById(R.id.creditCardMethodContainer);
        mUpiView = findViewById(R.id.upiMethodContainer);
        mCODView.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.VISIBLE);
        mUpiView.setVisibility(View.VISIBLE);
        //     upiID = upiPay;
//                    }else {
//                        mCODView.setVisibility(View.GONE);
//                        mCardView.setVisibility(View.GONE);
//                        mUpiView.setVisibility(View.GONE);
//                        upiID = "YES";
//                    }
//
//                });

    }


    private void paytmGateway() {
        sendUserDetailToServer dl = new sendUserDetailToServer();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void upi(String paymentMethod, String totalAmount) {
        @SuppressLint("SimpleDateFormat") String timeStampDate1 = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
        @SuppressLint("SimpleDateFormat") String timeStampDate2 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

        String orderID = GenerateRandomNum.Companion.generateRandNum();
        Checkout razorpay = new Checkout();
        razorpay.setKeyID("rzp_test_gqSj9eurmiorFO");
        JSONObject orderRequest = new JSONObject();
        try {
            String totalAmountExl = totalAmount.replace("Rs.", "");
            int amount = Math.round(Float.parseFloat(totalAmountExl) * 100);
            Map<String, Object> orderedItemsMap = new HashMap<>();
            // orderedItemsMap.put("ordered_items", FieldValue.arrayUnion((Object[]) getOrderedItemsArr));
            orderedItemsMap.put("total_amount", "\u20b9" + totalAmountExl);
            orderRequest.put("amount", amount);
            orderedItemsMap.put("ordered_time", timeStampDate1 + " at " + timeStampDate2);
            orderedItemsMap.put("ordered_id", orderID);
            orderedItemsMap.put("payment_method", paymentMethod);
            orderedItemsMap.put("customer_uid", uid);
            db.collection(USER_ORDERS).add(orderedItemsMap).addOnCompleteListener(task -> {
            });
            db.collection(USER_ORDERS).get().addOnCompleteListener(task -> {
            });
// opens razorpayUI
            razorpay.open(CheckoutActivity.this, orderRequest);
            Intent intent = new Intent(this, OrderSuccessfulActivity.class);
            startActivity(intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*long transactionId = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());
        long transactionRefId = Long.parseLong(GenerateRandomNum.Companion.generateRandNum());
        EasyUpiPayment mEasyUPIPayment = new EasyUpiPayment.Builder()
                .with(this)
                .setPayeeVpa(upiID)
                .setTransactionId(String.valueOf(transactionId))
              .setTransactionRefId(String.valueOf(transactionRefId))
                .setDescription("Payment to " +  " for food ordering")
                .setAmount(100 + ".00")
                .build();

            mEasyUPIPayment.setPaymentStatusListener(this);
            mEasyUPIPayment.startPayment();*/

    }

    private void deleteCartItems() {
        for (int i = 0; i < Objects.requireNonNull(getItemsArr).length; i++) {
            db.collection(USER_LIST).document(uid).collection(CART_ITEMS).document(getItemsArr[i]).delete().addOnSuccessListener(aVoid -> {
            });
            Intent intent = new Intent(this, OrderSuccessfulActivity.class);
            intent.putExtra("RES_UID", resUid);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class sendUserDetailToServer extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(CheckoutActivity.this);
        //private String orderId , mid, custid, amt;
        String url = "GENERATE_CHECKSUM_URL";
        String verifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        // "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID"+orderId;
        String CHECKSUMHASH = "";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(CheckoutActivity.this);
            String param =
                    "MID=" + mid +
                            "&ORDER_ID=" + orderID +
                            "&CUST_ID=" + customerID +
                            "&CHANNEL_ID=WAP&TXN_AMOUNT=" + 100 + "&WEBSITE=WEBSTAGING" +
                            "&CALLBACK_URL=" + verifyurl + "&INDUSTRY_TYPE_ID=Retail";
            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);

            /*
                This will receive checksum and order_id
             */
            Log.e("CheckSum result >>", jsonObject.toString());
            Log.e("CheckSum result >>", jsonObject.toString());
            try {
                CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                Log.e("CheckSum result >>", CHECKSUMHASH);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ", "  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
//            PaytmPGService Service = PaytmPGService.getStagingService("YOUR_PAYTM_TEST_URL");
            // when app is ready to publish use production service
            // PaytmPGService  Service = PaytmPGService.getProductionService();
            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", String.valueOf(orderID));
            paramMap.put("CUST_ID", String.valueOf(customerID));
            paramMap.put("CHANNEL_ID", "WAP");
            //    paramMap.put("TXN_AMOUNT", mTotalAmount);
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL", verifyurl);
            paramMap.put("CHECKSUMHASH", CHECKSUMHASH);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
//            PaytmOrder Order = new PaytmOrder(paramMap);
//            Log.e("checksum ", "param "+ paramMap.toString());
//            Service.initialize(Order,null);
//            // start payment service call here
//            Service.startPaymentTransaction(CheckoutActivity.this, true, true,
//                    CheckoutActivity.this);
        }
    }

    private void uploadOrderDetails(String paymentMethod, String TotalAmount) {
        @SuppressLint("SimpleDateFormat") String timeStampDate1 = new SimpleDateFormat("dd MMM yyyy").format(Calendar.getInstance().getTime());
        @SuppressLint("SimpleDateFormat") String timeStampDate2 = new SimpleDateFormat("hh:mm a").format(Calendar.getInstance().getTime());

        String orderID = GenerateRandomNum.Companion.generateRandNum();

        Map<String, Object> orderedItemsMap = new HashMap<>();
        // orderedItemsMap.put("ordered_items", FieldValue.arrayUnion((Object[]) getOrderedItemsArr));
        orderedItemsMap.put("total_amount", TotalAmount);
        orderedItemsMap.put("ordered_time", timeStampDate1 + " at " + timeStampDate2);
        orderedItemsMap.put("ordered_id", orderID);
        orderedItemsMap.put("payment_method", paymentMethod);
        orderedItemsMap.put("customer_uid", uid);
        db.collection(USER_ORDERS).add(orderedItemsMap).addOnCompleteListener(task -> {
        });
        db.collection(USER_ORDERS).get().addOnCompleteListener(task -> {
        });

        Intent intent = new Intent(this, OrderSuccessfulActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void razorpayCheckout(String paymentMethod, JsonObject orderTicket) {


        Checkout razorpay = new Checkout();
        razorpay.setKeyID("rzp_test_agjUYIzweMaoav");
        JSONObject orderRequest = new JSONObject();
        try {

            Map<String, Object> orderedItemsMap = new HashMap<>();
            // orderedItemsMap.put("ordered_items", FieldValue.arrayUnion((Object[]) getOrderedItemsArr));
            // TODO order_id instead of id is giving error
            orderRequest.put("id", orderTicket.get("orderId"));
            orderRequest.put("amount", orderTicket.get("amount").getAsString());
            orderRequest.put("amount_paid", orderTicket.get("amount_paid").getAsString());
            orderRequest.put("notes", orderTicket.get("notes"));
            orderRequest.put("created_at", orderTicket.get("created_at").getAsString());
            orderRequest.put("amount_due", orderTicket.get("amount_due").getAsString());
            orderRequest.put("currency", orderTicket.get("currency").getAsString());
            orderRequest.put("receipt", orderTicket.get("receipt").getAsString());
            orderRequest.put("entity", orderTicket.get("entity"));
            orderRequest.put("offer_id", orderTicket.get("offer_id"));
            orderRequest.put("attempts", orderTicket.get("attempts"));
            orderRequest.put("status", orderTicket.get("status"));


//            orderedItemsMap.put("ordered_time", timeStampDate1 + " at " + timeStampDate2);
//            orderedItemsMap.put("ordered_id", orderID);
//            orderedItemsMap.put("payment_method", paymentMethod);
//            orderedItemsMap.put("customer_uid", uid);
//            db.collection(USER_ORDERS).add(orderedItemsMap).addOnCompleteListener(task -> {
//            });
//            db.collection(USER_ORDERS).get().addOnCompleteListener(task -> {
//            });
// opens razorpayUI
            razorpay.open(CheckoutActivity.this, orderRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        // TODO update razorpay paymentId in DB.
// TODO signature verification, orderId and signature were coming as null
        Intent intent = new Intent(this, OrderSuccessfulActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }


//    @Override
//    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//
//    }
//
//    @Override
//    public void onTransactionSuccess() {
//        uploadOrderDetails("PAID","");
//        deleteCartItems();
//        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTransactionSubmitted() {
//
//    }
//
//    @Override
//    public void onTransactionFailed() {
//        Toast.makeText(this, "Transaction Has Failed!", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onTransactionCancelled() {
//        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onAppNotFound() {
//        Toast.makeText(this, "NO UPI Apps Found On Your Device", Toast.LENGTH_SHORT).show();
//    }
//
//
//
//    @Override
//    public void onTransactionResponse(Bundle inResponse) {
//        uploadOrderDetails("PAID","");
//        deleteCartItems();
//    }
//
//    @Override
//    public void networkNotAvailable() {
//        Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void clientAuthenticationFailed(String inErrorMessage) {
//        Toast.makeText(this, "Client Authentication Failed", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void someUIErrorOccurred(String inErrorMessage) {
//        Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//        Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onBackPressedCancelTransaction() {
//        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//        Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show();
//    }
}