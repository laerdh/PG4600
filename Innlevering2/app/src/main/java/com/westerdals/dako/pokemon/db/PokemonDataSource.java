package com.westerdals.dako.pokemon.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.westerdals.dako.pokemon.CustomListAdapter;
import com.westerdals.dako.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonDataSource {
    private static CustomListAdapter adapter;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = {
            SQLiteHelper.POKEMON_COLUMN_LOCATION_ID,
            SQLiteHelper.POKEMON_COLUMN_ID,
            SQLiteHelper.POKEMON_COLUMN_NAME,
            SQLiteHelper.POKEMON_COLUMN_IMAGEURL};


    public PokemonDataSource(Context context) { dbHelper = new SQLiteHelper(context); }


    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }


    public void close() { dbHelper.close(); }


    public static void setAdapter(Activity context, List<Pokemon> pokemons) {
        adapter = new CustomListAdapter(context, pokemons);
    }


    public static CustomListAdapter getAdapter() {
        return adapter;
    }


    public long createPokemon(Pokemon p) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.POKEMON_COLUMN_LOCATION_ID, p.getLocationId());
        values.put(SQLiteHelper.POKEMON_COLUMN_ID, p.getId());
        values.put(SQLiteHelper.POKEMON_COLUMN_NAME, p.getName());
        values.put(SQLiteHelper.POKEMON_COLUMN_IMAGEURL, p.getImageUrl());

        return database.insert(SQLiteHelper.POKEMON_TABLE_NAME, null, values);
    }


    public List<Pokemon> getPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        Cursor cursor = database.query(SQLiteHelper.POKEMON_TABLE_NAME,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pokemon pokemon = cursorToPokemon(cursor);
            pokemons.add(pokemon);
            cursor.moveToNext();
        }

        return pokemons;
    }


    private Pokemon cursorToPokemon(Cursor cursor) {
        Pokemon pokemon = new Pokemon();
        pokemon.setLocationId(cursor.getString(0));
        pokemon.setId(cursor.getString(1));
        pokemon.setName(cursor.getString(2));
        pokemon.setImageUrl(cursor.getString(3));
        return pokemon;
    }
}
