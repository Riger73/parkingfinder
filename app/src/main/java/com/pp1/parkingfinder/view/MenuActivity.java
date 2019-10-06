package com.pp1.parkingfinder.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.pp1.parkingfinder.R;

import static com.pp1.parkingfinder.model.Constants.ERROR_DIALOG_REQUEST;
import static com.pp1.parkingfinder.model.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.pp1.parkingfinder.model.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MenuActivity extends AppCompatActivity implements
        View.OnClickListener {
    // widgets
    private EditText mEmail, mPassword;
    private ProgressBar mProgressBar;
    private boolean mLocationPermissionGranted = false;

    private static final String TAG = "MenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        findViewById(R.id.selectMap).setOnClickListener(this);
        findViewById(R.id.button_logout).setOnClickListener(this);
        findViewById(R.id.listLeases).setOnClickListener(this);
        findViewById(R.id.makeRating).setOnClickListener(this);
        findViewById(R.id.listBooking).setOnClickListener(this);
    }

    /*
    =========================Google location services permissions check-----------------------------
    */
    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MenuActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MenuActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){

                }
                else{
                    getLocationPermission();
                }
            }
        }

    }

/**
 * -------------------------End of device permissions check-------------------------------------
 **/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectMap: {
                // Calls Google services permissions checks
                if(checkMapServices()) {
                    if(mLocationPermissionGranted) {
                        Intent intent = new Intent(MenuActivity.this,
                                MapActivity.class);
                        startActivity(intent);
                    } else {
                        getLocationPermission();
                    }
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            MapActivity.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.listLeases: {
                // Calls Google services permissions checks
                if(checkMapServices()) {
                    if(mLocationPermissionGranted) {
                        Intent intent = new Intent(MenuActivity.this,
                                SearchListingFragment.class);
                        startActivity(intent);
                    } else {
                        getLocationPermission();
                    }
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            SearchListingFragment.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.makeRating: {
                Intent intent = new Intent(MenuActivity.this,
                        RatingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.listBooking: {
                // Calls Google services permissions checks
                if(checkMapServices()) {
                    if(mLocationPermissionGranted) {
                        Intent intent = new Intent(MenuActivity.this,
                                ManageBookingsFragment.class);
                        startActivity(intent);
                    } else {
                        getLocationPermission();
                    }
                } else {
                    Intent intent = new Intent(MenuActivity.this,
                            ManageBookingsFragment.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.button_logout: {

                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(MenuActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                break;
            }


        }
    }
}