package com.westerdals.dako.pokemon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.westerdals.dako.pokemon.model.Location;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDataSource {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.LOCATION_COLUMN_ID,
            SQLiteHelper.LOCATION_COLUMN_NAME,
            SQLiteHelper.LOCATION_COLUMN_LAT,
            SQLiteHelper.LOCATION_COLUMN_LNG,
            SQLiteHelper.LOCATION_COLUMN_HINT};


    public LocationDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close() {
        dbHelper.close();
    }


    public Location createLocation(String id, String name, double lat, double lng, String hint) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.LOCATION_COLUMN_ID, id);
        values.put(SQLiteHelper.LOCATION_COLUMN_NAME, name);
        values.put(SQLiteHelper.LOCATION_COLUMN_LAT, lat);
        values.put(SQLiteHelper.LOCATION_COLUMN_LNG, lng);
        values.put(SQLiteHelper.LOCATION_COLUMN_HINT, hint);
        long insertId = database.insert(SQLiteHelper.LOCATION_TABLE_NAME, null, values);
        Cursor cursor = database.query(SQLiteHelper.LOCATION_TABLE_NAME,
                allColumns, SQLiteHelper.LOCATION_COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Location newLocation = cursorToLocation(cursor);
        cursor.close();
        return newLocation;
    }


    public void deleteLocation(Location location) {
        String id = location.getId();
        System.out.println("Feedback deleted with id: " + id);
        database.delete(SQLiteHelper.LOCATION_TABLE_NAME, SQLiteHelper.LOCATION_COLUMN_ID
                + " = " + id, null);
    }


    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.LOCATION_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Location location = cursorToLocation(cursor);
            locations.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return locations;
    }


    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setId(cursor.getString(0));
        location.setName(cursor.getString(1));
        location.setLatitude(cursor.getDouble(2));
        location.setLongitude(cursor.getDouble(3));
        location.setHint(cursor.getString(4));
        return location;
    }
}
