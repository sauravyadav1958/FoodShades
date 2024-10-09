package com.example.project.Activity;

import android.app.ProgressDialog
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project.R
import com.example.project.order.CheckoutActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.Locale
// TODO this is temp Map
class LocationTrackingActivity : AppCompatActivity() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val uid: String? = null
    private var city: String? = null
    private var postalCode: String? = null
    private var knownName: String? = null
    private var subLocality: String? = null
    private var mIntentKey: String? = null
    private var mLocationText: EditText? = null
    private var addresses: List<Address>? = null
    private var geocoder: Geocoder? = null
    private var mSaveLocationBtn: Button? = null
    private var mProgressDialog: ProgressDialog? = null
    private var Txttotal: TextView? = null

    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.mapboxMap.setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.mapboxMap.setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.mapboxMap.pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_location)
        mapView = findViewById(R.id.mapView)
        // mapView.onCreate(savedInstanceState);
        init()
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private fun init() {
        mIntentKey = intent.getStringExtra("INT")
        db = FirebaseFirestore.getInstance()
        //  uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        mProgressDialog = ProgressDialog(this)
        mLocationText = findViewById(R.id.locationActualText)
        geocoder = Geocoder(this, Locale.getDefault())
        mSaveLocationBtn = findViewById(R.id.saveLocationBtn)
        Txttotal = findViewById(R.id.Txttotal)
        /* mTotalAmount = getIntent().getStringExtra("TOTAL_AMOUNT");
        mAmountText = findViewById(R.id.totalTxt);
        mAmountText.setText("Amount :\u20b9" + mTotalAmount);*/
        val textView = findViewById<TextView>(R.id.Txttotal)
        textView.text = String.format(
            "%s", intent.getStringExtra("total")
        )
    }

    private fun onMapReady() {
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.mapboxMap.loadStyle(
            Style.STANDARD
        ) {
            try {
                addresses = geocoder!!.getFromLocation(1.1, 2.0, 1)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            initLocationComponent()
            setupGesturesListener()
            mSaveLocationBtn!!.setOnClickListener { view: View? ->
                mProgressDialog!!.setMessage("Updating Address, Please wait...")
                mProgressDialog!!.show()
                val updateLocMap: MutableMap<String, Any> =
                    HashMap()
                updateLocMap["latitude"] = 1.1
                updateLocMap["longitude"] = 2.0
                updateLocMap["address"] = mLocationText!!.text.toString()
//                updateLocMap["knownname"] = knownName!!
//                updateLocMap["sublocality"] = subLocality!!
//                updateLocMap["city"] = city!!
//                updateLocMap["postalcode"] = postalCode!!

                db.collection("UserList").add(updateLocMap)
                    .addOnCompleteListener { task: Task<DocumentReference?>? -> }
                db.collection("UserList").get()
                    .addOnCompleteListener { task: Task<QuerySnapshot?>? -> }
                mProgressDialog!!.dismiss()

                /* Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                   startActivity(intent);
                   finish();*/
                val intent = Intent(
                    applicationContext,
                    CheckoutActivity::class.java
                )
                intent.putExtra("total", Txttotal!!.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            puckBearing = PuckBearing.COURSE
            puckBearingEnabled = true
            enabled = true

            if (addresses != null && addresses!!.isNotEmpty()) {
                city = addresses!![0].locality
                postalCode = addresses!![0].postalCode
                knownName = addresses!![0].featureName
                subLocality = addresses!![0].subLocality
                val finalAddress = "$knownName, $subLocality, $city, $postalCode"
                mLocationText!!.setText(finalAddress)
            }

            locationPuck = LocationPuck2D(
                bearingImage = ImageHolder.from(R.drawable.mapbox_user_puck_icon),
                shadowImage = ImageHolder.from(R.drawable.mapbox_user_icon_shadow),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(2.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener
        )
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener
        )
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

