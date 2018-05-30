package com.example.lenovo.myapp.ui.activity.test.greendao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cxb.tools.utils.AssetsUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.DaoSession;
import com.example.lenovo.myapp.model.GDCharacteristic;
import com.example.lenovo.myapp.model.GDCharacteristicDao;
import com.example.lenovo.myapp.model.GDPokemon;
import com.example.lenovo.myapp.model.GDPokemonDao;
import com.example.lenovo.myapp.model.GDPokemonName;
import com.example.lenovo.myapp.model.GDPokemonNameDao;
import com.example.lenovo.myapp.model.GDProperty;
import com.example.lenovo.myapp.model.GDPropertyDao;
import com.example.lenovo.myapp.model.JoinPokemonToCharacteristic;
import com.example.lenovo.myapp.model.JoinPokemonToCharacteristicDao;
import com.example.lenovo.myapp.model.JoinPokemonToProperty;
import com.example.lenovo.myapp.model.JoinPokemonToPropertyDao;
import com.example.lenovo.myapp.ui.adapter.GDPokemonListAdapter;
import com.example.lenovo.myapp.ui.base.BaseAppCompatActivity;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;
import com.example.lenovo.myapp.utils.GreenDaoHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class GreenDaoTestActivity extends BaseAppCompatActivity {

    private RecyclerView rvPokemonList;

    private List<GDPokemon> mPokemonList;
    private GDPokemonListAdapter mPokemonAdapter;

    public static void show(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, GreenDaoTestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao_test);

        initData();
        initView();
        getPokemonList();
    }

    private void initData() {
        mPokemonList = new ArrayList<>();
        mPokemonAdapter = new GDPokemonListAdapter(this, mPokemonList);
        mPokemonAdapter.setListClick(mListClick);
    }

    private void initView() {
        rvPokemonList = findViewById(R.id.rv_pokemon_list);
        rvPokemonList.setLayoutManager(new LinearLayoutManager(this));
        rvPokemonList.setAdapter(mPokemonAdapter);
    }

    private void initPokemonDB(final DaoSession daoSession) {
        final Gson gson = new GsonBuilder().create();
        final String pokemonJson = AssetsUtil.getAssetsTxtByName(this, "all.txt");
        final String nameJson = AssetsUtil.getAssetsTxtByName(this, "pokemon_name.txt");
        final String propertyJson = AssetsUtil.getAssetsTxtByName(this, "property.txt");
        final String characteristicJson = AssetsUtil.getAssetsTxtByName(this, "characteristic.txt");

        final List<GDPokemon> pokemonList = gson.fromJson(pokemonJson, new TypeToken<List<GDPokemon>>() {
        }.getType());
        final List<GDPokemonName> nameList = gson.fromJson(nameJson, new TypeToken<List<GDPokemonName>>() {
        }.getType());
        final List<GDProperty> propertyList = gson.fromJson(propertyJson, new TypeToken<List<GDProperty>>() {
        }.getType());
        final List<GDCharacteristic> characteristicList = gson.fromJson(characteristicJson, new TypeToken<List<GDCharacteristic>>() {
        }.getType());
        final List<JoinPokemonToProperty> joinPTPList = new ArrayList<>();
        final List<JoinPokemonToCharacteristic> joinPTCList = new ArrayList<>();

        if (pokemonList == null || pokemonList.isEmpty()) {
            return;
        }

        final GDPokemonDao pokemonDao = daoSession.getGDPokemonDao();
        final GDPokemonNameDao nameDao = daoSession.getGDPokemonNameDao();
        final GDPropertyDao propertyDao = daoSession.getGDPropertyDao();
        final GDCharacteristicDao characteristicDao = daoSession.getGDCharacteristicDao();
        final JoinPokemonToPropertyDao joinPTPDao = daoSession.getJoinPokemonToPropertyDao();
        final JoinPokemonToCharacteristicDao joinPTCDao = daoSession.getJoinPokemonToCharacteristicDao();

        for (GDPokemon pokemon : pokemonList) {
            final String id = pokemon.getId();
            final List<GDProperty> pList = pokemon.getProperty();
            final List<GDCharacteristic> cList = pokemon.getCharacteristic();

            if (pList != null && !pList.isEmpty()) {
                for (GDProperty property : pList) {
                    final String pid = property.getId();
                    final JoinPokemonToProperty ptp = new JoinPokemonToProperty();
                    ptp.setPokemonId(id);
                    ptp.setPropertyId(pid);
                    joinPTPList.add(ptp);
                }
            }

            if (cList != null && !cList.isEmpty()) {
                for (GDCharacteristic characteristic : cList) {
                    final String cid = characteristic.getId();
                    final JoinPokemonToCharacteristic ptc = new JoinPokemonToCharacteristic();
                    ptc.setPokemonId(id);
                    ptc.setCharacteristicId(cid);
                    joinPTCList.add(ptc);
                }
            }
        }

        joinPTPDao.insertInTx(joinPTPList);
        joinPTCDao.insertInTx(joinPTCList);
        pokemonDao.insertInTx(pokemonList);
        nameDao.insertInTx(nameList);
        propertyDao.insertInTx(propertyList);
        characteristicDao.insertInTx(characteristicList);
    }

    private void getPokemonList() {
        final DaoSession daoSession = new GreenDaoHelper().getDaoSession();
        final GDPokemonDao pokemonDao = daoSession.getGDPokemonDao();

        final QueryBuilder<GDPokemon> qb = pokemonDao.queryBuilder();
        qb.orderAsc(GDPokemonDao.Properties.Id);
        Query<GDPokemon> query = qb.build();

        List<GDPokemon> pokemonList = query.list();
        if (pokemonList == null || pokemonList.isEmpty()) {
            initPokemonDB(daoSession);
            pokemonList = query.list();
        }

        if (pokemonList != null && !pokemonList.isEmpty()) {
            mPokemonList.clear();
            mPokemonList.addAll(pokemonList);
            mPokemonAdapter.notifyDataSetChanged();
        }
    }

    private OnListClickListener mListClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            final GDPokemon pokemon = mPokemonList.get(position);
            final String id = pokemon.getId();
            GDPokemonDetailActivity.show(GreenDaoTestActivity.this, id);
        }

        @Override
        public void onTagClick(int tag, int position) {

        }
    };
}
