package com.uni.pis

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val LOCATION_PERMISSION_CODE=1234


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId==EditorInfo.IME_ACTION_DONE
                ||event.action== KeyEvent.ACTION_DOWN
                ||event.action== KeyEvent.KEYCODE_ENTER)
            {
                //search
                geoLocate()
            }
            return@setOnEditorActionListener true }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          == PackageManager.PERMISSION_GRANTED) {
          mMap.isMyLocationEnabled = true;
      } else {
          ActivityCompat.requestPermissions(this, arrayOf( Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_CODE )
      }
        // Add a marker in Sydney and move the camera
        val Amman = LatLng(31.9, 35.9)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Amman,10f))
        ic_gps.setOnClickListener {
          var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
          if (isLocationEnabled(locationManager)) {
              val location=locationManager.getLastKnownLocation((LocationManager.GPS_PROVIDER))
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location!!.latitude,location.longitude),15f))
          }

      }
        mMap.setOnMapLongClickListener {latlng ->
            val location = LatLng(latlng.latitude,latlng.longitude)
            mMap.addMarker(MarkerOptions().position(location).draggable(true)).isDraggable=true
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15.0f))
            mMap.setOnMarkerClickListener {
                var builder= AlertDialog.Builder (this)
                var result= "Mapcoordinate=${latlng.latitude}&${latlng.longitude}"
                builder.setTitle ("Is this the location you selected?")
                builder.setMessage ("Are you sure of what you are selecting")
                builder.setPositiveButton ("OK") { _, _ ->
                    var resultIntent= Intent ()
                    resultIntent.putExtra ("location", result)
                    setResult (Activity.RESULT_OK, resultIntent)
                    finish() }

                builder.setNegativeButton ("Cancel") { _, _ ->

                }


                builder.create ().show ()
                return@setOnMarkerClickListener true
            }

        }


    }
    fun geoLocate()
    {
        var search=et_search.text.toString()
        var geocoder=Geocoder(this)
        var list= arrayListOf<Address>()
        try
        {

            list= geocoder.getFromLocationName(search,1) as ArrayList<Address>
            var address:Address=list.get(0)
            var builder= AlertDialog.Builder (this)
            val location = LatLng(address.latitude,address.longitude)

            mMap.addMarker(MarkerOptions().position(location).title(address.getAddressLine(0)))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,15.0f))
            var result= "Mapcoordinate=${address.latitude}&${address.longitude}"
            builder.setTitle ("Is this the location you selected?")
            builder.setMessage ("${address.getAddressLine(0)}")
            builder.setPositiveButton ("OK", {dialogInterface, n ->
                var resultIntent= Intent ()
                resultIntent.putExtra ("location", result)
                setResult (Activity.RESULT_OK, resultIntent)
                finish() })

            builder.setNegativeButton ("Cancel", { dialogInterface, n ->
            })


            builder.create ().show ()


        }
        catch (e: IOException)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        mMap.isMyLocationEnabled = false
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    mMap.isMyLocationEnabled=true
                    // permission are good

                }
            }
        }
    }

}
