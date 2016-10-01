package mfaizhasan.com.ftsmexplorer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by FAIZ on 27/9/2016.
 */

public class LocationDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LOCATIONDB.db";
    public static final String TABLE_NAME = "Location";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMG = "imgURL";
    public static final String COLUMN_FLOOR = "floor";
    public static final String COLUMN_BlOCK = "block";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONGI = "longi";
    public static final String CREATE_TABLE_LOCATION = "CREATE TABLE "+TABLE_NAME+" (" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + " TEXT, "+ COLUMN_IMG + " TEXT, "+ COLUMN_FLOOR + " TEXT, "+ COLUMN_BlOCK + " TEXT, "+ COLUMN_LAT + " TEXT, "+ COLUMN_LONGI + " TEXT" + ")";


    public LocationDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IS EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public void addLocation(LocationData locationData){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE,locationData.getTitle());
        contentValues.put(COLUMN_IMG,locationData.getImageURL());
        contentValues.put(COLUMN_FLOOR,locationData.getFloor());
        contentValues.put(COLUMN_BlOCK,locationData.getBlock());
        contentValues.put(COLUMN_LAT,locationData.getLat());
        contentValues.put(COLUMN_LONGI,locationData.getLongi());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public List<LocationData>  findLocation(String title){
        List<LocationData> locationDataList = new LinkedList<LocationData>();
        String findAllQuery = "SELECT * FROM "+ TABLE_NAME + " WHERE "+ COLUMN_BlOCK + " = \"" + title + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(findAllQuery, null);

        LocationData locationData = null;
        if (cursor.moveToFirst()){

            do {
                locationData = new LocationData();
                locationData.setTitle(cursor.getString(1));
                locationDataList.add(locationData);
            }while (cursor.moveToNext());
        }
        return locationDataList;
    }

    public LocationData findLocationByName(String name){
        String queryFIndStudent = "Select * from " + TABLE_NAME + " WHERE "+ COLUMN_TITLE + " =\'" + name + "\'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryFIndStudent, null);
        LocationData locationData = new LocationData();
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            locationData.setTitle(cursor.getString(1));
            locationData.setImageURL(cursor.getString(2));
            locationData.setFloor(cursor.getString(3));
            locationData.setBlock(cursor.getString(4));
            locationData.setLat(cursor.getString(5));
            locationData.setLongi(cursor.getString(6));
            cursor.close();
        } else {
            locationData = null;
        }
        db.close();
        return locationData;
    }
}
