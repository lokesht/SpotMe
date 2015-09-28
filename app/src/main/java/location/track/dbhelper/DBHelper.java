package location.track.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import location.track.constant.AppConstant;
import location.track.exception.ValueNotInsertedException;

/**
 * Created by Lokesh on 23-09-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    /** */
    private String TAG = getClass().getName();

    /** */
    private SQLiteDatabase db;

    /**
     * If want change the database schema, you must increment the database version.
     */
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "loc.sqlite";

    private Context myContext;

    public DBHelper(Context context) {
        //What is this SQLiteDatabase.CursorFactory exactly is when is best sciniero to use it
        //SQLiteDatabase.CursorFactory factory = null;

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LocationContract.LocationEntry.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // db.execSQL(LocationContract.LocationEntry.SQL_DROP);
        // onCreate(db);
    }

    /* Insert Saved Status of List */
    public long insertInTable(String tableName, String columnHack, ContentValues cv) {
        long rowid = 0;
        try {
            db = this.getWritableDatabase();
            rowid = db.insert(tableName, columnHack, cv);

            if (rowid < 0) {
                throw new ValueNotInsertedException();
            }

        } catch (Exception e) {
            if (AppConstant.DEBUG)
                Log.e(TAG + " insert ", e.toString());
        } finally {
            if (db != null)
                db.close();
        }
        return rowid;
    }

    /**
     * @param TableName
     * @param columns
     * @param where
     * @return Cursor
     */
    public Cursor getTableValue(String TableName, String[] columns, String where) {
        Cursor c = null;
        try {
            db = this.getReadableDatabase();
            c = db.query(TableName, columns, where, null, null, null, null, null);
        } catch (Exception e) {
            if (AppConstant.DEBUG)
                Log.e(TAG, e.toString() + "--> getTableValue()");
        } finally {
            /** If database does not contain anything immediately close database */
            if (c == null)
                db.close();
        }
        return c;
    }
}
