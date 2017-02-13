package com.example.lenovo.myapp.db;

import android.content.Context;

import com.cxb.tools.utils.LiteOrmHelper;
import com.example.lenovo.myapp.model.PokemonBean;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.List;

/**
 * 口袋妖怪数据库
 */

public class PokemonDBHelper {

    public static List<PokemonBean> getPokemonList(Context context) {
        return LiteOrmHelper.getInstance(context)
                .cascade()
                .query(PokemonBean.class);
    }

    public static List<PokemonBean> selectPokemonById(Context context, String id) {
        return LiteOrmHelper.getInstance(context)
                .cascade()
                .query(new QueryBuilder<>(PokemonBean.class)
                        .where("id = ?", id));
    }

    public static void addPokemon(Context context, PokemonBean pokemon) {
        LiteOrmHelper.getInstance(context)
                .cascade()
                .save(pokemon);
    }

    public static void addPokemons(Context context, List<PokemonBean> pokemons) {
        LiteOrmHelper.getInstance(context)
                .cascade()
                .save(pokemons);
    }

    public static void updatePokemon(Context context, PokemonBean pokemon) {
        LiteOrmHelper.getInstance(context)
                .cascade()
                .update(pokemon);
    }

    public static void deletePokemon(Context context, PokemonBean pokemon) {
        LiteOrmHelper.getInstance(context)
                .delete(pokemon);
    }

    public static void deletePokemonById(Context context, String id) {
        LiteOrmHelper.getInstance(context)
                .delete(new WhereBuilder(PokemonBean.class)
                .where("id = ?", id));
    }

    public static void deleteAll(Context context) {
        LiteOrmHelper.getInstance(context).cascade().delete(PokemonBean.class);
    }

}
