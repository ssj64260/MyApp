package com.example.lenovo.myapp.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * 口袋妖怪
 */
@Entity(nameInDb = "GD_Pokemon")
public class GDPokemon {
    @Id
    private String id;//pokemon ID
    private String name;//pokemon 名称
    private String mega;//pokemon 形态

    @ToOne(joinProperty = "id")
    private GDPokemonName pmName;//pokemon 名称

    @ToMany
    @JoinEntity(
            entity = JoinPokemonToProperty.class,
            sourceProperty = "pokemonId",
            targetProperty = "propertyId"
    )
    private List<GDProperty> property;//pokemon 属性

    @ToMany
    @JoinEntity(
            entity = JoinPokemonToCharacteristic.class,
            sourceProperty = "pokemonId",
            targetProperty = "characteristicId"
    )
    private List<GDCharacteristic> characteristic;//pokemon 特性

    private String hp;//pokemon 血量
    private String attack;//pokemon 攻击
    private String defense;//pokemon 防御
    private String s_attack;//pokemon 特攻
    private String s_defense;//pokemon 特防
    private String speed;//pokemon 速度
    private String ethnic_value;//pokemon 种族值
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 241780631)
    private transient GDPokemonDao myDao;

    @Generated(hash = 1266257761)
    public GDPokemon(String id, String name, String mega, String hp, String attack,
            String defense, String s_attack, String s_defense, String speed,
            String ethnic_value) {
        this.id = id;
        this.name = name;
        this.mega = mega;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.s_attack = s_attack;
        this.s_defense = s_defense;
        this.speed = speed;
        this.ethnic_value = ethnic_value;
    }

    @Generated(hash = 1292305100)
    public GDPokemon() {
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

    public String getMega() {
        return this.mega;
    }

    public void setMega(String mega) {
        this.mega = mega;
    }

    public String getHp() {
        return this.hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getAttack() {
        return this.attack;
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getDefense() {
        return this.defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getS_attack() {
        return this.s_attack;
    }

    public void setS_attack(String s_attack) {
        this.s_attack = s_attack;
    }

    public String getS_defense() {
        return this.s_defense;
    }

    public void setS_defense(String s_defense) {
        this.s_defense = s_defense;
    }

    public String getSpeed() {
        return this.speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getEthnic_value() {
        return this.ethnic_value;
    }

    public void setEthnic_value(String ethnic_value) {
        this.ethnic_value = ethnic_value;
    }

    @Generated(hash = 1369578685)
    private transient String pmName__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1685868269)
    public GDPokemonName getPmName() {
        String __key = this.id;
        if (pmName__resolvedKey == null || pmName__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GDPokemonNameDao targetDao = daoSession.getGDPokemonNameDao();
            GDPokemonName pmNameNew = targetDao.load(__key);
            synchronized (this) {
                pmName = pmNameNew;
                pmName__resolvedKey = __key;
            }
        }
        return pmName;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1930409867)
    public void setPmName(GDPokemonName pmName) {
        synchronized (this) {
            this.pmName = pmName;
            id = pmName == null ? null : pmName.getId();
            pmName__resolvedKey = id;
        }
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1912001740)
    public List<GDProperty> getProperty() {
        if (property == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GDPropertyDao targetDao = daoSession.getGDPropertyDao();
            List<GDProperty> propertyNew = targetDao._queryGDPokemon_Property(id);
            synchronized (this) {
                if (property == null) {
                    property = propertyNew;
                }
            }
        }
        return property;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 485873664)
    public synchronized void resetProperty() {
        property = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1520279940)
    public List<GDCharacteristic> getCharacteristic() {
        if (characteristic == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            GDCharacteristicDao targetDao = daoSession.getGDCharacteristicDao();
            List<GDCharacteristic> characteristicNew = targetDao
                    ._queryGDPokemon_Characteristic(id);
            synchronized (this) {
                if (characteristic == null) {
                    characteristic = characteristicNew;
                }
            }
        }
        return characteristic;
    }

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1932435975)
    public synchronized void resetCharacteristic() {
        characteristic = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1688829919)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGDPokemonDao() : null;
    }
}
