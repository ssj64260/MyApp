package com.example.lenovo.myapp.model;

import java.util.List;

/**
 * 口袋妖怪
 */

public class PokemonBean {

    private String id;
    private String name;
    private List<PropertyBean> property;
    private List<CharacteristicBean> characteristic;
    private String hp;
    private String attack;
    private String defense;
    private String s_attack;
    private String s_defense;
    private String speed;
    private String ethnic_value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PropertyBean> getProperty() {
        return property;
    }

    public void setProperty(List<PropertyBean> property) {
        this.property = property;
    }

    public List<CharacteristicBean> getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(List<CharacteristicBean> characteristic) {
        this.characteristic = characteristic;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getS_attack() {
        return s_attack;
    }

    public void setS_attack(String s_attack) {
        this.s_attack = s_attack;
    }

    public String getS_defense() {
        return s_defense;
    }

    public void setS_defense(String s_defense) {
        this.s_defense = s_defense;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getEthnic_value() {
        return ethnic_value;
    }

    public void setEthnic_value(String ethnic_value) {
        this.ethnic_value = ethnic_value;
    }
}
