package com.example.lenovo.myapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class JoinPokemonToProperty {

    @Id(autoincrement = true)
    private Long id;

    private String pokemonId;//pokemonID
    private String propertyId;//属性ID

    @Generated(hash = 1474556633)
    public JoinPokemonToProperty(Long id, String pokemonId, String propertyId) {
        this.id = id;
        this.pokemonId = pokemonId;
        this.propertyId = propertyId;
    }

    @Generated(hash = 1130606882)
    public JoinPokemonToProperty() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPokemonId() {
        return this.pokemonId;
    }

    public void setPokemonId(String pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }


}
