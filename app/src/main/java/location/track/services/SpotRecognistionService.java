package location.track.services;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;

import location.track.dbhelper.DBHelper;
import location.track.dbhelper.LocationContract;

/**
 * Created by Lokesh on 28-09-2015.
 */
public class SpotRecognistionService extends Service {

    private static final String TAG = SpotRecognistionService.class.getSimpleName();
    public static final String NOTIFICATION = "location.track.services.receiver";

    //Google Api Client for Location
    private GoogleApiClient mGoogleApiClient;

    // private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private GPSLocationListener gpsLocationListener;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(gpsLocationListener)
                .addOnConnectionFailedListener(gpsLocationListener)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service creating");

        gpsLocationListener = new GPSLocationListener();
        buildGoogleApiClient();

        //Connect  to get Location
        mGoogleApiClient.connect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service destroying");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    private class GPSLocationListener implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {

                //String s = location.getLatitude() + " " + String.valueOf(location.getLongitude());

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String d = format.format(date);

                DBHelper db = new DBHelper(getApplicationContext());

                ContentValues cv = new ContentValues();
                cv.put(LocationContract.LocationEntry.DATE, d);
                cv.put(LocationContract.LocationEntry.CORD_LAT, location.getLatitude());
                cv.put(LocationContract.LocationEntry.CORD_LONG, location.getLongitude());

                long l = db.insertInTable(LocationContract.LocationEntry.TABLE_NAME, null, cv);
                //Toast.makeText(getApplicationContext(), "test - "+l, Toast.LENGTH_SHORT).show();
            }

            //Update Result
            publishResults(location.toString(), 1);
        }

        private void publishResults(String lat_Long, int result) {
            Intent intent = new Intent(NOTIFICATION);
            intent.putExtra(Intent.EXTRA_TEXT, lat_Long);
            sendBroadcast(intent);
        }

        @Override
        public void onConnected(Bundle bundle) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(5000);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        @Override
        public void onConnectionSuspended(int i) {
            reConnect();
        }

        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            reConnect();
        }

        public void reConnect() {
            if (mGoogleApiClient != null && !mGoogleApiClient.isConnected())
                mGoogleApiClient.connect();
        }
    }

}
