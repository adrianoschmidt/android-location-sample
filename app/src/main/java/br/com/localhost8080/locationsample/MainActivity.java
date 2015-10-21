package br.com.localhost8080.locationsample;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (this.checkIfGooglePlayServicesAreAvailable()) {
            this.buildGoogleApiClient();
            this.mGoogleApiClient.connect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        TextView textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        TextView textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            textViewLatitude.setText("Lat: " + String.valueOf(mLastLocation.getLatitude()));
            textViewLongitude.setText("Lng: " + String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean checkIfGooglePlayServicesAreAvailable() {
        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            System.out.println("GooglePlayServices errorCode: " + errorCode);
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show();
            return false;
        }
        return true;
    }

}