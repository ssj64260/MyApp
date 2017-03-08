package com.example.lenovo.myapp.model;

import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 口袋妖怪
 */
@Table("Pokemon")
public class PokemonBean implements Serializable {

    public static final String POKEMON_TABLE = "Pokemon";
    public static final String POKEMON_ID = "id";
    public static final String POKEMON_NAME = "name";


    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int _id;

    private String id;//pokemon ID
    private String name;//pokemon 名称
    private String mega;//pokemon 形态

    @Mapping(Relation.OneToOne)
    private PokemonNameBean pmName;//pokemon 名称

    @Mapping(Relation.ManyToMany)
    private ArrayList<PropertyBean> property;//pokemon 属性

    @Mapping(Relation.ManyToMany)
    private ArrayList<CharacteristicBean> characteristic;//pokemon 特性

    private String hp;//pokemon 血量
    private String attack;//pokemon 攻击
    private String defense;//pokemon 防御
    private String s_attack;//pokemon 特攻
    private String s_defense;//pokemon 特防
    private String speed;//pokemon 速度
    private String ethnic_value;//pokemon 种族值

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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

    public String getMega() {
        return mega;
    }

    public void setMega(String mega) {
        this.mega = mega;
    }

    public PokemonNameBean getPmName() {
        return pmName;
    }

    public void setPmName(PokemonNameBean pmName) {
        this.pmName = pmName;
    }

    public ArrayList<PropertyBean> getProperty() {
        return property;
    }

    public void setProperty(ArrayList<PropertyBean> property) {
        this.property = property;
    }

    public ArrayList<CharacteristicBean> getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(ArrayList<CharacteristicBean> characteristic) {
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
