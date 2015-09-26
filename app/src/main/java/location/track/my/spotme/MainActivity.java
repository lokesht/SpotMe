package location.track.my.spotme;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = getClass().getName();

    //Google Api Client for Location
    private GoogleApiClient mGoogleApiClient;
   // private Location mLastLocation;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        // TO get Last known location
        //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Connect Client
        reConnectLocationService();
    }

    @Override
    protected void onStop() {
        //Disconnect Client
        disConnectLocationService();

        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended ->"+ i);
        reConnectLocationService();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed ->"+ connectionResult.toString());
        reConnectLocationService();
    }

    private void reConnectLocationService() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    private void disConnectLocationService() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            TextView textView = (TextView) findViewById(R.id.tv_lat_long);
            String s = location.getLatitude() + " " + String.valueOf(location.getLongitude());
            textView.setText(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

//        if (mLastLocation != null) {
//            TextView textView = (TextView)findViewById(R.id.tv_lat_long);
//            String s = mLastLocation.getLatitude()+" "+String.valueOf(mLastLocation.getLongitude());
//            textView.setText(s);
//            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//        }
    }
}
