# FoodShades
![Cover Image](https://github.com/TeraiyaAdesh/FoodShades/assets/97627129/90fbbb68-e92f-4bd5-930d-9f1c5a97b3e0)
### Show some ❤️ and star the repo to show support for the project


<h1 align="center"> FoodShades Android App </h1>

<p align="center">
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/license-MIT-green"></a>
  <a href="https://github.com/TeraiyaAdesh/FoodShades/pulls"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square"></a>
  <a href="https://github.com/TeraiyaAdesh/FoodShades/pulls"><img alt="Contributions Welcome" src="https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat-square"></a>
</p>

FoodShades is an open source food ordering android application developed in **Java** and **Kotlin**. FoodShades is mainly developed so I can improve my android development skills by building a real world application and experiment with different android components.

# Demo
https://github.com/TeraiyaAdesh/FoodShades/assets/97627129/aab10510-1e80-4672-9b3c-570e1fd74433
# ☁️ Inspiration

FoodShades's main inspiration for it's UI comes from the <a href="https://play.google.com/store/apps/details?id=in.swiggy.android">Swiggy's</a> android app. I have incorporated some of the UI elements and features which the swiggy app had when I was developing the app.

# ✨ Features

Some of the features of FoodShades app are:

- User Authentication using Phone Number, Google, Email Id.
- Browse different food items.
- Add or remove food items in your cart.
- Place order for different items and see them in a cart.
- Set your delivery location on the app accurately.
- Payment options include : <a href="https://www.npci.org.in/what-we-do/upi/product-overview">UPI</a> (Unified Payment Interface), <a href="https://developer.paytm.com/docs/v1/android-sdk/">Paytm</a>, <a href="https://razorpay.com/docs/#home-payments">Razorpay</a> and COD.
- See and write reviews for food.
- Help & Support page.
- and more...

# 📚 Major Libraries Used

- Firebase Suite - For Auth, Database and Storage
- Mapbox - Mapbox is used to integrate the map and build the route generation functionality.
- Glide - For Image Loading.
- Paytm SDK - For Paytm Payment Integration.
- Rozarpay API - For multiple payment options.
- Liquid Swipe - Liquid animation welcome pages.
- Lottie - To Display native `json` animations in android.
- Smiley Rating Bar.

# 💻 Build Instructions

1. Clone or download the repository :

```shell
git clone https://github.com/TeraiyaAdesh/FoodShades.git
```

2. Import the project into Android Studio.

3. Create a firebase project and add this android app to the project.

4. Run the below command in the terminal to get your **SHA-1** key and upload it in the project settings in your firebase console, without this you cannot authenticate users using their phone numbers.

```shell
keytool -exportcert -list -v \
-alias androiddebugkey -keystore ~/.android/debug.keystore
```

5. Enable Phone Number sign in Firebase Authentication Tab in the left side.

6. Download and add the `google-services.json` file from the firebase project you created earlier and add it to the project under **app** folder.

7. Get your Mapbox Token and paste it in the `strings.xml` file

```
<string name="mapbox_access_token">REPLACE WITH YOUR OWN MAPBOX TOKEN</string>
```

8. Also paste the token in the `gradle.properties` file

```
MAPBOX_DOWNLOADS_TOKEN=REPLACE WITH YOUR OWN MAPBOX TOKEN
```

9. Setup a server which will generate a **CHECKSUM** Hash for the paytm sdk to work.After creating the server please paste the url which will return the **checksum** hash in the `CheckoutActivity.java` under the *ui/order* folder.Replace the GENERATE_CHECKSUM_URL with your own server url.

```java
public class sendUserDetailToServer extends AsyncTask<ArrayList<String>, Void, String> {
        private ProgressDialog dialog = new ProgressDialog(CheckoutActivity.this);
        //private String orderId , mid, custid, amt;
        String url ="GENERATE_CHECKSUM_URL";
```

10. Run the project into an emulator or a physical device.

# 👨 Made By

`Adesh Teraiya`








