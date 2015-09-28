package location.track.my.spotme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import location.track.adapter.ListAdapter;
import location.track.dbhelper.DBHelper;
import location.track.dbhelper.LocationContract;
import location.track.model.Item;
import location.track.services.SpotRecognistionService;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    private ActivityDetectionRecevier receiver;

    // For the SimpleCursorAdapter to match the UserDictionary columns to layout items.
    private static final String[] COLUMNS_TO_BE_BOUND = new String[]{
            LocationContract.LocationEntry.CORD_LAT,
            LocationContract.LocationEntry.CORD_LONG
    };

    private static final int[] LAYOUT_ITEMS_TO_FILL = new int[]{
            android.R.id.text1,
            android.R.id.text2
    };

    private ListView latlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    public void initialize() {
        receiver = new ActivityDetectionRecevier();
        Intent in = new Intent(this, SpotRecognistionService.class);
        startService(in);

        latlong = (ListView) findViewById(R.id.lv_latlong);
        setupList();
    }

    public void setupList() {
        // Get a Cursor containing all of the rows in the Words table.
        Cursor cursor = new DBHelper(getApplicationContext()).getTableValue(LocationContract.LocationEntry.TABLE_NAME, null, null);

        ArrayList<Item> lsItem = new ArrayList<Item>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String lat = cursor.getString(cursor.getColumnIndex(LocationContract.LocationEntry.CORD_LAT));
                String longi = cursor.getString(cursor.getColumnIndex(LocationContract.LocationEntry.CORD_LONG));
                String date = cursor.getString(cursor.getColumnIndex(LocationContract.LocationEntry.DATE));

                Item i = new Item(lat, longi, date);
                lsItem.add(i);
            } while (cursor.moveToNext());

            cursor.close();
        }

        ListAdapter adapter = new ListAdapter(this, lsItem);
        // Attach the adapter to the ListView.
        latlong.setAdapter(adapter);
        latlong.setSelection(lsItem.size()-1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(SpotRecognistionService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public class ActivityDetectionRecevier extends BroadcastReceiver {
        private final String TAG = ActivityDetectionRecevier.class.getName();

        @Override
        public void onReceive(Context context, Intent intent) {
            setupList();
            //Log.i(TAG, "onReceive");
            //Toast.makeText(MainActivity.this, "ActivityDetectionRecevier", Toast.LENGTH_SHORT).show();
        }
    }
}
