package com.example.lenovo.myapp.ui.activity.test.dbtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.LiteOrmHelper;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.db.PokemonDBHelper;
import com.example.lenovo.myapp.model.CharacteristicBean;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.PokemonNameBean;
import com.example.lenovo.myapp.model.PropertyBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.PokemonListAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.ui.activity.test.dbtest.DatebaseDetailActivity.POKEMON_ID;

/**
 * 数据库操作
 */

public class DatebaseTestActivity extends BaseActivity {

    private RecyclerView rvPmList;
    private PokemonListAdapter mAdapter;
    private List<PokemonBean> list;

    private Button btnInsertAll;
    private Button btnDeleteDB;
    private Button btnDeleteTable;

    private DefaultProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datebase_test);

        initView();
        setData();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            progressDialog.showDialog();
            ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
                @Override
                public void run() {
                    getPokemons();
                }
            });
        }
    }

    private void initView() {
        progressDialog = new DefaultProgressDialog(this);
        progressDialog.setMessage("加载中...");

        rvPmList = (RecyclerView) findViewById(R.id.rv_pm_list);

        btnInsertAll = (Button) findViewById(R.id.btn_insert_all);
        btnDeleteDB = (Button) findViewById(R.id.btn_delete_db);
        btnDeleteTable = (Button) findViewById(R.id.btn_delete_table);
    }

    private void setData() {
        mAdapter = new PokemonListAdapter(this, "all");
        mAdapter.setOnListClickListener(listClick);

        rvPmList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvPmList.setAdapter(mAdapter);

        btnInsertAll.setOnClickListener(click);
        btnDeleteDB.setOnClickListener(click);
        btnDeleteTable.setOnClickListener(click);

        progressDialog.showDialog();
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                getPokemons();
            }
        });
    }

    private void getPokemons() {
        list = PokemonDBHelper.getPokemonList(DatebaseTestActivity.this);
        mAdapter.setList(list);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                progressDialog.dismissDialog();
            }
        });
    }

    private void insertByTxt() {
        progressDialog.showDialog();

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String pmJson = AssetsUtil.getAssetsTxtByName(DatebaseTestActivity.this, "all.txt");
                if (!StringCheck.isEmpty(pmJson)) {
                    List<PokemonBean> pmTemp = gson.fromJson(pmJson, new TypeToken<List<PokemonBean>>() {
                    }.getType());
                    if (pmTemp != null && pmTemp.size() > 0) {

                        List<PokemonNameBean> pmNameTemp = new ArrayList<>();
                        List<PropertyBean> pTemp = new ArrayList<>();
                        List<CharacteristicBean> cTemp = new ArrayList<>();

                        String pmNmaeJson = AssetsUtil.getAssetsTxtByName(DatebaseTestActivity.this, "pokemon_name.txt");
                        if (!StringCheck.isEmpty(pmNmaeJson)) {
                            pmNameTemp = gson.fromJson(pmNmaeJson, new TypeToken<List<PokemonNameBean>>() {
                            }.getType());
                        }

                        String pJson = AssetsUtil.getAssetsTxtByName(DatebaseTestActivity.this, "property.txt");
                        if (!StringCheck.isEmpty(pJson)) {
                            pTemp = gson.fromJson(pJson, new TypeToken<List<PropertyBean>>() {
                            }.getType());
                        }

                        String cJson = AssetsUtil.getAssetsTxtByName(DatebaseTestActivity.this, "characteristic.txt");
                        if (!StringCheck.isEmpty(cJson)) {
                            cTemp = gson.fromJson(cJson, new TypeToken<List<CharacteristicBean>>() {
                            }.getType());
                        }

                        for (PokemonBean pm : pmTemp) {
                            for (PokemonNameBean pmName : pmNameTemp) {
                                if (pm.getId().equals(pmName.getId())) {
                                    pm.setPmName(pmName);
                                }
                            }

                            for (PropertyBean p : pm.getProperty()) {
                                for (PropertyBean pt : pTemp) {
                                    if (p.getId().equals(pt.getId())) {
                                        p.setName(pt.getName());
                                        p.setEn_name(pt.getEn_name());
                                    }
                                }
                            }

                            for (CharacteristicBean c : pm.getCharacteristic()) {
                                for (CharacteristicBean ct : cTemp) {
                                    if (c.getId().equals(ct.getId())) {
                                        c.setName(ct.getName());
                                        c.setEn_name(ct.getEn_name());
                                        c.setJp_name(ct.getJp_name());
                                        c.setDescription(ct.getDescription());
                                    }
                                }
                            }
                        }

                        PokemonDBHelper.addPokemons(DatebaseTestActivity.this, pmTemp);
                        getPokemons();
                    }
                }
            }
        });
    }

    private void deleteAll() {
        progressDialog.showDialog();
        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                PokemonDBHelper.deleteAll(DatebaseTestActivity.this);
                list = PokemonDBHelper.getPokemonList(DatebaseTestActivity.this);
                mAdapter.setList(list);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        ToastUtil.toast("删除表成功");
                        progressDialog.dismissDialog();
                    }
                });
            }
        });
    }

    //点击监听
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_insert_all:
                    insertByTxt();
                    break;
                case R.id.btn_delete_table:
                    deleteAll();
                    break;
                case R.id.btn_delete_db:
                    if (LiteOrmHelper.deleteDB()) {
                        ToastUtil.toast("删除数据库成功");
                        finish();
                    } else {
                        ToastUtil.toast("数据库不存在或删除失败");
                    }
                    break;
            }
        }
    };

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent();
            intent.setClass(DatebaseTestActivity.this, DatebaseDetailActivity.class);
            intent.putExtra(POKEMON_ID, list.get(position).getId());
            startActivityForResult(intent, 1);
        }

        @Override
        public void onTagClick(Tag tag, int position) {

        }
    };

}
