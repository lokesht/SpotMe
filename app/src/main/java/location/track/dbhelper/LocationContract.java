package location.track.dbhelper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Lokesh on 23-09-2015.
 */
public final class LocationContract {

    private final String TAG = LocationContract.class.getName();

    // To make it easy query Julian day at UTC.
    public static long normalizeDate(long startDate) {
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);

    }

    public LocationContract() {
    }

    /* table contents */
    public static abstract class LocationEntry implements BaseColumns {

        public static String TABLE_NAME = "location";
        public static String CORD_LAT = "coord_lat";
        public static String CORD_LONG = "coord_lang";
        public static String DATE = "date";

        public static final String SQL_CREATE = T.CREATE_TABLE + TABLE_NAME
                + T.OPEN_BRACE
                + _ID + T.TYPE_INTEGER + T.PRIMARY_KEY + T.AUTO_INCREMENT + T.SEP_COMMA
                + CORD_LAT + T.TYPE_REAL + T.NOT_NULL + T.SEP_COMMA
                + CORD_LONG + T.TYPE_REAL + T.NOT_NULL + T.SEP_COMMA
                + DATE + T.TYPE_TEXT + T.NOT_NULL
                + T.CLOSE_BRACE + T.SEMICOLON;

        public static final String SQL_DROP = T.DROP_TABLE + TABLE_NAME;
    }
}
