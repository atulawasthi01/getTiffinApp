package com.yumtiff.mohitkumar.tiffin.Activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yumtiff.mohitkumar.tiffin.Global;
import com.yumtiff.mohitkumar.tiffin.HomeActivity;
import com.yumtiff.mohitkumar.tiffin.R;
import com.yumtiff.mohitkumar.tiffin.Utility.GPSTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationActivity extends AppCompatActivity {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    String currentAddress;
    double latitude,longitude;
    RelativeLayout allowRl, denyRl;
    Context context = this;
    Dialog dialog = null;
    public static String address,street,city,state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
            try {
                getcurrentLocation();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("gpserror",""+e.getMessage());
            }

        } else {
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser() {

        dialog = new Dialog(context);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gps_popup_layout);
        dialog.show();
        allowRl = (RelativeLayout) dialog.findViewById(R.id.allowRl);
        denyRl = (RelativeLayout) dialog.findViewById(R.id.denyRl);

        allowRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isAllowloc=true;

                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
                try {
                    getcurrentLocation();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("gpserror",""+e.getMessage());
                }
                dialog.dismiss();



            }
        });

        denyRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isDenyloc=true;

                Intent intent=new Intent(LocationActivity.this,UserlocationActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

    }

    private void getcurrentLocation() throws IOException {

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        GPSTracker gps = new GPSTracker(LocationActivity.this);


        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude, 1);

        if (addresses != null && !addresses.isEmpty()) {


            address = addresses.get(0).getAddressLine(0);
            street = addresses.get(0).getSubLocality();
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            currentAddress = address + " , " + street + " , " + city + " " + state + " , " + country + " , " + postalCode;
            Log.d("currentAddress",currentAddress);
            Intent i=new Intent(LocationActivity.this,HomeActivity.class);
            startActivity(i);

        }
        else {
            Toast.makeText(getApplicationContext(),"Address is Empty",Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "Address: "+currentAddress, Toast.LENGTH_SHORT).show();
        Log.d("Latlong", Double.toString(latitude) + " " + Double.toString(longitude));
        //Log.d("Address", currentAddress);

       /* GPSTracker GTracker = new GPSTracker(LoginActivity.this);

        if (GTracker != null) {
            System.out.print("location is NOT null.... ");


            latitude = GTracker.getLatitude();
            longitude = GTracker.getLongitude();


            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                currentCityName = addresses.get(0).getAddressLine(2);
                //String stateName = addresses.get(0).getAddressLine(2);
                //String countryName = addresses.get(0).getAddressLine(3);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            latitude = Double.parseDouble(null);
            longitude = Double.parseDouble(null);

            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            Log.d("Latlong", Double.toString(latitude) + " " + Double.toString(longitude));
            Log.d("Address", currentCityName);

        }*/
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(LocationActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
