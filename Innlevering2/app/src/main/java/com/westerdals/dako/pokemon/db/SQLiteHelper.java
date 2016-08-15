package com.westerdals.dako.pokemon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Tables Names
    public static final String POKEMON_TABLE_NAME = "pokemons";
    public static final String LOCATION_TABLE_NAME = "locations";

    // Location Table - column names
    public static final String LOCATION_COLUMN_ID = "_id";
    public static final String LOCATION_COLUMN_NAME = "name";
    public static final String LOCATION_COLUMN_LNG =  "lng";
    public static final String LOCATION_COLUMN_LAT = "lat";
    public static final String LOCATION_COLUMN_HINT = "hint";

    // Pokemon Table - column names
    public static final String POKEMON_COLUMN_LOCATION_ID = "locationId";
    public static final String POKEMON_COLUMN_ID = "id";
    public static final String POKEMON_COLUMN_NAME = "name";
    public static final String POKEMON_COLUMN_IMAGEURL = "imageUrl";

    // Database Name
    private static final String DATABASE_NAME = "locations.db";

    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Table Create Statements
    // Locations Table create statement
    private static final String CREATE_TABLE_LOCATIONS = "create table "
            + LOCATION_TABLE_NAME + "("
            + LOCATION_COLUMN_ID + " varchar(50), "
            + LOCATION_COLUMN_NAME + " varchar(30), "
            + LOCATION_COLUMN_LAT + " double, "
            + LOCATION_COLUMN_LNG + " double, "
            + LOCATION_COLUMN_HINT + " varchar(100)); ";

    // Pokemons Table create statement
    private static final String CREATE_TABLE_POKEMONS = "create table "
            + POKEMON_TABLE_NAME + "("
            + POKEMON_COLUMN_LOCATION_ID + " varchar(50), "
            + POKEMON_COLUMN_ID + " varchar(50), "
            + POKEMON_COLUMN_NAME + " varchar(30), "
            + POKEMON_COLUMN_IMAGEURL + " varchar(100));";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_LOCATIONS);
        database.execSQL(CREATE_TABLE_POKEMONS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + POKEMON_TABLE_NAME);
        onCreate(db);
    }
}
