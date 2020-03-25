package com.example.onlineshop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button button;
    TextView textView;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    LocationListener locationListener;

    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.btn_location);
        textView = findViewById(R.id.coordinates);

        //add permissions

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                //Check gps is enable or not
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    OnGPS();
                }else{
                    //GPS is on
                    getLocation();
                }
            }
        });

    }

    private void getLocation() {

        //check permission again
        if(ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else{
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(locationGPS != null){
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                textView.append("\n Latitude:  " + latitude + "\n" + "Longitude:  "+ longitude);
            }
            else if(locationNetwork != null){
                double lat = locationNetwork.getLatitude();
                double longi = locationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                textView.append("\n Latitude:  " + latitude + "\n" + "Longitude:  "+ longitude);
            }
            else if(locationPassive != null){
                double lat = locationPassive.getLatitude();
                double longi = locationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                textView.append("\n Latitude:  " + latitude + "\n" + "Longitude:  "+ longitude);
            }
            else{
                Toast.makeText(this,"Can't get the location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) configureButton();
                return;
        }
    }

    private void configureButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.INTERNET}, 10);
                        return;
                    }else{
                        configureButton();
                    }
                }
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });

    }
}
