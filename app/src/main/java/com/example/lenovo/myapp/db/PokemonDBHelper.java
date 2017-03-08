package com.example.lenovo.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cxb.tools.utils.SQLiteHelper;
import com.example.lenovo.myapp.model.PokemonBean;

/**
 * pokemon 数据库帮助
 */

public class PokemonDBHelper {

    private SQLiteHelper dbHelper;

    public PokemonDBHelper(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public PokemonBean getPokemonById(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(PokemonBean.POKEMON_TABLE,
                new String[]{PokemonBean.POKEMON_ID, PokemonBean.POKEMON_NAME},
                PokemonBean.POKEMON_ID + "=?",
                new String[]{id},
                null, null, null);
        cursor.moveToFirst();

        PokemonBean pokemon = new PokemonBean();
        pokemon.setId(cursor.getString(cursor.getColumnIndex(PokemonBean.POKEMON_ID)));
        pokemon.setName(cursor.getString(cursor.getColumnIndex(PokemonBean.POKEMON_NAME)));

        cursor.close();
        db.close();

        return pokemon;
    }

    public void update(PokemonBean pokemon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PokemonBean.POKEMON_NAME, pokemon.getName());

        db.update(PokemonBean.POKEMON_TABLE, values, PokemonBean.POKEMON_ID + "=?", new String[]{String.valueOf(pokemon.getId())});
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PokemonBean.POKEMON_TABLE, PokemonBean.POKEMON_ID + "=?", new String[]{id});
        db.close();
    }

}
