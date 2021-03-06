package com.example.lenovo.myapp.ui.activity.test.nesttest;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxb.tools.customlist.NoScrollGridView;
import com.cxb.tools.customlist.NoScrollListView;
import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.testbean.NestTestBean;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.PokemonListAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.GridToOhterAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.ListItemAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.ListToOhterAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.RecyclerToOtherAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
import com.example.lenovo.myapp.utils.ToastMaster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 列表嵌套详情页面
 */

public class ListNestDatailActivity extends BaseActivity {

    public static final String KEY_NEST_TYPE = "nest_type";

    public static final int OUTER_LIST = 1 << 3;
    public static final int OUTER_RECYCLER = 1 << 4;
    public static final int OUTER_GRID = 1 << 5;
    public static final int OUTER_SCROLL = 1 << 6;

    public static final int INNER_LIST = 1;
    public static final int INNER_RECYCLER = 1 << 1;
    public static final int INNER_GRID = 1 << 2;

    private int nestType;

    private TextView tvBack;

    private ListView lvList;
    private RecyclerView rvRecycler;
    private GridView gvGrid;

    private NestedScrollView svScroll;
    private NoScrollListView lvScroll;
    private RecyclerView rvScroll;
    private NoScrollGridView gvScroll;

    private List<PokemonBean> pmList = new ArrayList<>();
    private List<NestTestBean> nestList = new ArrayList<>();
    private List<NestTestBean> gridList = new ArrayList<>();
    private List<PokemonBean> scrollList = new ArrayList<>();

    private ListToOhterAdapter ltoAdapter;
    private RecyclerToOtherAdapter rtoAdapter;
    private GridToOhterAdapter gtoAdapter;
    private ListItemAdapter stoAdapter;
    private PokemonListAdapter pmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nest_detail);

        initData();
        initView();
        setData();

    }

    private void initData() {
        nestType = getIntent().getIntExtra(KEY_NEST_TYPE, OUTER_LIST | INNER_LIST);

        String json = AssetsUtil.getAssetsTxtByName(this, "all.txt");
        if (!StringCheck.isEmpty(json)) {
            Type type = new TypeToken<List<PokemonBean>>() {
            }.getType();
            Gson gson = new Gson();
            List<PokemonBean> temp = gson.fromJson(json, type);
            pmList.addAll(temp);
        }

        for (int i = 0; i < 30; i++) {
            NestTestBean nt = new NestTestBean();
            nt.setTitle("外层List控件#" + (i + 1));
            List<PokemonBean> pmTemp = new ArrayList<>();
            for (int j = 0; j < (i % 5) + 1; j++) {
                pmTemp.add(pmList.get(i * 10 + j));
            }
            nt.setPokemons(pmTemp);
            nestList.add(nt);
        }

        for (int i = 0; i < 30; i++) {
            NestTestBean nt = new NestTestBean();
            nt.setTitle("外层List控件#" + (i + 1));
            List<PokemonBean> pmTemp = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                pmTemp.add(pmList.get(i * 10 + j));
            }
            nt.setPokemons(pmTemp);
            gridList.add(nt);
        }

        for (int i = 0; i < 10; i++) {
            scrollList.add(pmList.get((int) ((i + 1) * Math.random() * 10)));
        }

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);

        lvList = (ListView) findViewById(R.id.lv_list);
        rvRecycler = (RecyclerView) findViewById(R.id.rv_recycler);
        gvGrid = (GridView) findViewById(R.id.gv_grid);

        svScroll = (NestedScrollView) findViewById(R.id.sv_scroll);
        lvScroll = (NoScrollListView) findViewById(R.id.lv_scroll_list);
        rvScroll = (RecyclerView) findViewById(R.id.rv_scroll_recycler);
        gvScroll = (NoScrollGridView) findViewById(R.id.gv_scroll_grid);
    }

    private void setData() {
        tvBack.setOnClickListener(click);

        if (checkNestType(OUTER_LIST)) {
            lvList.setVisibility(View.VISIBLE);

            if (checkNestType(INNER_LIST)) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.LISTVIEW);
                lvList.setAdapter(ltoAdapter);
            } else if (checkNestType(INNER_GRID)) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.GRIDVIEW);
                lvList.setAdapter(ltoAdapter);
            } else if (checkNestType(INNER_RECYCLER)) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.RECYCLERVIEW);
                lvList.setAdapter(ltoAdapter);
            }
        } else if (checkNestType(OUTER_RECYCLER)) {
            rvRecycler.setVisibility(View.VISIBLE);

            rvRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            if (checkNestType(INNER_LIST)) {
                rtoAdapter = new RecyclerToOtherAdapter(this, nestList, RecyclerToOtherAdapter.LISTVIEW);
                rvRecycler.setAdapter(rtoAdapter);
            } else if (checkNestType(INNER_GRID)) {
                rtoAdapter = new RecyclerToOtherAdapter(this, nestList, RecyclerToOtherAdapter.GRIDVIEW);
                rvRecycler.setAdapter(rtoAdapter);
            } else if (checkNestType(INNER_RECYCLER)) {
                rtoAdapter = new RecyclerToOtherAdapter(this, nestList, RecyclerToOtherAdapter.RECYCLERVIEW);
                rvRecycler.setAdapter(rtoAdapter);
            }
        } else if (checkNestType(OUTER_GRID)) {
            gvGrid.setVisibility(View.VISIBLE);

            if (checkNestType(INNER_LIST)) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.LISTVIEW);
                gvGrid.setAdapter(gtoAdapter);
            } else if (checkNestType(INNER_GRID)) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.GRIDVIEW);
                gvGrid.setAdapter(gtoAdapter);
            } else if (checkNestType(INNER_RECYCLER)) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.RECYCLERVIEW);
                gvGrid.setAdapter(gtoAdapter);
            }
        } else if (checkNestType(OUTER_SCROLL)) {
            svScroll.setVisibility(View.VISIBLE);

            if (checkNestType(INNER_LIST)) {
                lvScroll.setVisibility(View.VISIBLE);

                stoAdapter = new ListItemAdapter(this, scrollList);
                lvScroll.setAdapter(stoAdapter);
                lvScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("ListView：" + scrollList.get(position).getName());
                    }
                });
                lvScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        svScroll.scrollTo(0, 0);
                    }
                });
            } else if (checkNestType(INNER_GRID)) {
                gvScroll.setVisibility(View.VISIBLE);

                stoAdapter = new ListItemAdapter(this, scrollList);
                gvScroll.setAdapter(stoAdapter);
                gvScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("GridView：" + scrollList.get(position).getName());
                    }
                });
                gvScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        svScroll.scrollTo(0, 0);
                    }
                });
            } else if (checkNestType(INNER_RECYCLER)) {
                rvScroll.setVisibility(View.VISIBLE);

                pmAdapter = new PokemonListAdapter(this, "all");
                pmAdapter.setList(scrollList);
                pmAdapter.setOnListClickListener(listClick);
                rvScroll.setHasFixedSize(true);
                rvScroll.setNestedScrollingEnabled(false);
                rvScroll.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                rvScroll.setAdapter(pmAdapter);
                rvScroll.post(new Runnable() {
                    @Override
                    public void run() {
                        svScroll.scrollTo(0, 0);
                    }
                });
            }
        }
    }

    private OnListClickListener listClick = new OnListClickListener() {
        @Override
        public void onItemClick(int position) {
            ToastMaster.toast("RecyclerView：" + scrollList.get(position).getName());
        }

        @Override
        public void onTagClick(int tag, int position) {

        }
    };

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    finish();
                    break;
            }
        }
    };

    private boolean checkNestType(int checkType) {
        return (nestType & checkType) != 0;
    }
}
