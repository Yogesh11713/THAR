package com.example.tharapk.ui.map

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas

import com.google.android.gms.maps.model.BitmapDescriptor

import androidx.core.content.ContextCompat
import com.blikoon.qrcodescanner.QrCodeActivity

import com.example.tharapk.R
import com.google.maps.android.ui.IconGenerator


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    private val cameraPosition: CameraPosition = CameraPosition.Builder()    //initial camera position
        .target(LatLng(25.142090,75.807688))
        .zoom(17.5f)
        .bearing(300f)
        .tilt(80f)                       //initial tilt
        .build()

    companion object {
        private val MY_PERMISSION_FINE_LOCATION = 101
    }

    private val TAG = MapActivity::class.java.simpleName

    private fun setMapStyle(map: GoogleMap) {            //adding custom style with mapstylewithgoogle
        try {
            // Customize the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style
                )
            )
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        }catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        setMapPermission()
        mapFragment.getMapAsync(this)


    }

    fun setMapPermission() {

        //todo: Location PERMISSIONS
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(
                    this as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSION_FINE_LOCATION
                )

            }
        }


    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSION_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(
                        this,
                        "Permission Denied, MAP Will not work",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val icongen =IconGenerator(this)    //generating an icon

        mMap.setOnMarkerClickListener { marker ->                    //can be removed
            if (marker.isInfoWindowShown) {
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            true
        }
        val Tpnt = LatLng(25.142090,75.807688)//T point
        mMap.addMarker(MarkerOptions().position(Tpnt).title("T Point")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("T-point"))))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Tpnt))

        val libra = LatLng(25.142490,75.806798) // library
        mMap.addMarker(MarkerOptions().position(libra).title("Library")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("Library"))))

        val Dblock = LatLng(25.144265,75.804995) // D block
        mMap.addMarker(MarkerOptions().position(Dblock).title("D block")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("D-block")))
        )

        val Cblock = LatLng(25.143976,75.805427)//C block
        mMap.addMarker(MarkerOptions().position(Cblock).title("C Block - Mechanical department")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("C-block")))
        )

        val Bblock = LatLng(25.143772,75.805821) // B block
        mMap.addMarker(MarkerOptions().position(Bblock).title("b block- Civil department")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("B-block")))
        )

        val Ablock = LatLng(25.143555,75.806224) // A block
        mMap.addMarker( MarkerOptions().position(Ablock)
            .title("A block")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("A-block")))
            //            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_a_block))       //adding svg file as icon
        )

        val PNB = LatLng(25.143150,75.807046) // PNB
        mMap.addMarker(MarkerOptions().position(PNB).title("Punjab National Bank")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("PNB"))))

        val LT = LatLng(25.144329,75.806465) // LT
        mMap.addMarker(MarkerOptions().position(LT).title("Lecture Theatere(LT)")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("LT"))))

        val Proc = LatLng(25.145161,75.805693) // Proctor
        mMap.addMarker(MarkerOptions().position(Proc).title("Proctor office")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("proctor"))))


        val Agora = LatLng(25.142775,75.806358) // Proctor
        mMap.addMarker(MarkerOptions().position(Agora).title("Agora")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("Agora"))))

        val RMH = LatLng(25.144544,75.805954) // RMH
        mMap.addMarker(MarkerOptions().position(RMH).title("ramanuj hall-RMH")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("RMH"))))

        val petro = LatLng(25.142439,75.805619) // petro building
        mMap.addMarker(MarkerOptions().position(petro).title("Petroleum building")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("pertroleum Dep."))))


        val CS  = LatLng(25.141700,75.806839)
        mMap.addMarker(MarkerOptions().position(CS).title("Computer Science Department")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("CSE Dep."))))


        val dispensary = LatLng(25.141212,75.807399)
        mMap.addMarker(MarkerOptions().position(dispensary).title("Dispensary")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("dispensary"))))


        val canteen  = LatLng(25.140987,75.807285)
        mMap.addMarker(MarkerOptions().position(canteen).title("Canteen")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("Canteen"))))


        val girlshostel  = LatLng(25.143552,75.809605)
        mMap.addMarker(MarkerOptions().position(girlshostel).title("Girls Hostel")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("Girls hostel"))))


        val pnb_atm = LatLng(25.139682,75.810956)
        mMap.addMarker(MarkerOptions().position(pnb_atm).title("PNB ATM")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("ATM"))))


        val main_gate = LatLng(25.139733,75.811074)
        mMap.addMarker(MarkerOptions().position(main_gate).title("Main Gate")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("Main Gate"))))


        val hpoint = LatLng(25.140770,75.809473)
        mMap.addMarker(MarkerOptions().position(hpoint).title("H- point")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("H-point"))))


        val gym  = LatLng(25.139913,75.808180)
        mMap.addMarker(MarkerOptions().position(gym).title("Gymnasium")
            .icon(BitmapDescriptorFactory.fromBitmap(icongen.makeIcon("gym"))))

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))


        setMapStyle(mMap)
    }


    //function for adding svg(vector asset) as icon
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
    // for adding text as icon
    private fun BitmapDescriptorFactory(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }



}