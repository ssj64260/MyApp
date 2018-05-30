package com.example.lenovo.myapp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * 属性
 */
@Entity(nameInDb = "GD_Property")
public class GDProperty {

    @Id
    private String id;//属性ID
    private String name;//属性名称
    private String en_name;//属性英文名

    @ToMany
    @JoinEntity(
            entity = JoinPokemonToProperty.class,
            sourceProperty = "propertyId",
            targetProperty = "pokemonId"
    )
    @Transient
    private List<GDPokemon> pokemonList;
    @Generated(hash = 325558189)
    public GDProperty(String id, String name, String en_name) {
        this.id = id;
        this.name = name;
        this.en_name = en_name;
    }

    @Generated(hash = 772754348)
    public GDProperty() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEn_name() {
        return this.en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

}
