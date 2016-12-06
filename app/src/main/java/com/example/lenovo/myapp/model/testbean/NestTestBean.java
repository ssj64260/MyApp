package com.example.lenovo.myapp.model.testbean;

import com.example.lenovo.myapp.model.PokemonBean;

import java.io.Serializable;
import java.util.List;

/**
 * 列表嵌套
 */

public class NestTestBean implements Serializable {

    private String title;

    private List<PokemonBean> pokemons;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PokemonBean> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<PokemonBean> pokemons) {
        this.pokemons = pokemons;
    }
}
