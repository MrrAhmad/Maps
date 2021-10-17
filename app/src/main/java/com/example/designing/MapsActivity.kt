package com.example.designing

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.designing.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var currentMarker : Marker?= null
    private lateinit var binding: ActivityMapsBinding
    var fusedLocationProviderClient : FusedLocationProviderClient?= null
    var CurrentLocation : Location ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchlocation()
    }

    private fun fetchlocation(){
        if( ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
           !=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
            !=PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1000)
            return

        }
        val task =fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener { location ->
            if(location != null){
                this.CurrentLocation = location
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)

            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000 -> if(grantResults.size > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                fetchlocation()
            }
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       /* // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/

        val latlong = LatLng(CurrentLocation?.latitude!!, CurrentLocation?.longitude!!)
        drawmarker(latlong)

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker?) {

            }

            override fun onMarkerDragEnd(p0: Marker?) {
                if (currentMarker!=null)
                    currentMarker?.remove()

                val newlatlng = LatLng(p0?.position!!.latitude, p0?.position.longitude)
                drawmarker(newlatlng)
            }

            override fun onMarkerDragStart(p0: Marker?) {

            }
        }
        )

    }
    private fun drawmarker(latlong:LatLng ){
        val Markeropt = MarkerOptions().position(latlong).title("Latitude & Longitude")
            .snippet(latlong.toString()).draggable(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlong))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong, 15f))
        mMap.uiSettings.isZoomControlsEnabled= true

        currentMarker= mMap.addMarker(Markeropt)
        currentMarker?.showInfoWindow()
    }
}