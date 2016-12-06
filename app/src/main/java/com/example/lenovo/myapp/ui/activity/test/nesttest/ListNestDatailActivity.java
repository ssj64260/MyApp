package com.example.lenovo.myapp.ui.activity.test.nesttest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cxb.tools.CustomList.NoScrollGridView;
import com.cxb.tools.CustomList.NoScrollListView;
import com.cxb.tools.CustomList.OnlyClickRecyclerview;
import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.testbean.NestTestBean;
import com.example.lenovo.myapp.ui.adapter.MainAdapter;
import com.example.lenovo.myapp.ui.adapter.PokemenListAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.GridToOhterAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.ListItemAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.ListToOhterAdapter;
import com.example.lenovo.myapp.ui.adapter.nestlist.RecyclerToOtherAdapter;
import com.example.lenovo.myapp.ui.base.BaseActivity;
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

    public static final int LIST_TO_LIST = 11;
    public static final int LIST_TO_GRID = 12;
    public static final int LIST_TO_RECYCLER = 13;

    public static final int RECYCLER_TO_LIST = 21;
    public static final int RECYCLER_TO_GRID = 22;
    public static final int RECYCLER_TO_RECYCLER = 23;

    public static final int GRID_TO_LIST = 31;
    public static final int GRID_TO_GRID = 32;
    public static final int GRID_TO_RECYCLER = 33;

    public static final int SCROLL_TO_LIST = 41;
    public static final int SCROLL_TO_GRID = 42;
    public static final int SCROLL_TO_RECYCLER = 43;

    private int nestType;

    private TextView tvBack;

    private ListView lvList;
    private RecyclerView rvRecycler;
    private GridView gvGrid;

    private ScrollView svScroll;
    private NoScrollListView lvScroll;
    private OnlyClickRecyclerview rvScroll;
    private NoScrollGridView gvScroll;

    private List<PokemonBean> pmList = new ArrayList<>();
    private List<NestTestBean> nestList = new ArrayList<>();
    private List<NestTestBean> gridList = new ArrayList<>();
    private List<PokemonBean> scrollList = new ArrayList<>();

    private ListToOhterAdapter ltoAdapter;
    private RecyclerToOtherAdapter rtoAdapter;
    private GridToOhterAdapter gtoAdapter;
    private ListItemAdapter stoAdapter;
    private PokemenListAdapter pmAdapter;

    private RecyclerToOtherAdapter.ListType listType = RecyclerToOtherAdapter.ListType.LISTVIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nest_detail);

        initData();
        initView();
        setData();

    }

    private void initData() {
        nestType = getIntent().getIntExtra(KEY_NEST_TYPE, LIST_TO_LIST);

        String json = AssetsUtil.getAssetsTxtByName(this, "all");
        if (!StringCheck.isEmpty(json)) {
            Type type = new TypeToken<List<PokemonBean>>() {
            }.getType();
            Gson gson = new Gson();
            List<PokemonBean> temp = gson.fromJson(json, type);
            pmList.addAll(temp);
        }

        for (int i = 0; i < 15; i++) {
            NestTestBean nt = new NestTestBean();
            nt.setTitle("外层List控件#" + (i + 1));
            List<PokemonBean> pmTemp = new ArrayList<>();
            for (int j = 0; j < (i % 5) + 1; j++) {
                pmTemp.add(pmList.get(i * 10 + j));
            }
            nt.setPokemons(pmTemp);
            nestList.add(nt);
        }

        for (int i = 0; i < 15; i++) {
            NestTestBean nt = new NestTestBean();
            nt.setTitle("外层List控件#" + (i + 1));
            List<PokemonBean> pmTemp = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                pmTemp.add(pmList.get(i * 10 + j));
            }
            nt.setPokemons(pmTemp);
            gridList.add(nt);
        }

        for (int i = 0; i < 5; i++) {
            scrollList.add(pmList.get(i * nestType));
        }

    }

    private void initView() {
        tvBack = (TextView) findViewById(R.id.tv_back);

        lvList = (ListView) findViewById(R.id.lv_list);
        rvRecycler = (RecyclerView) findViewById(R.id.rv_recycler);
        gvGrid = (GridView) findViewById(R.id.gv_grid);

        svScroll = (ScrollView) findViewById(R.id.sv_scroll);
        lvScroll = (NoScrollListView) findViewById(R.id.lv_scroll_list);
        rvScroll = (OnlyClickRecyclerview) findViewById(R.id.rv_scroll_recycler);
        gvScroll = (NoScrollGridView) findViewById(R.id.gv_scroll_grid);

    }

    private void setData() {
        tvBack.setOnClickListener(click);

        if (nestType < 20) {
            lvList.setVisibility(View.VISIBLE);

            if (nestType == LIST_TO_LIST) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.ListType.LISTVIEW);
                lvList.setAdapter(ltoAdapter);
            } else if (nestType == LIST_TO_GRID) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.ListType.GRIDVIEW);
                lvList.setAdapter(ltoAdapter);
            } else if (nestType == LIST_TO_RECYCLER) {
                ltoAdapter = new ListToOhterAdapter(this, nestList, ListToOhterAdapter.ListType.RECYCLERVIEW);
                lvList.setAdapter(ltoAdapter);
            }
        } else if (nestType < 30) {
            rvRecycler.setVisibility(View.VISIBLE);

            if (nestType == RECYCLER_TO_LIST) {
                listType = RecyclerToOtherAdapter.ListType.LISTVIEW;
            } else if (nestType == RECYCLER_TO_GRID) {
                listType = RecyclerToOtherAdapter.ListType.GRIDVIEW;
            } else if (nestType == RECYCLER_TO_RECYCLER) {
                listType = RecyclerToOtherAdapter.ListType.RECYCLERVIEW;
            }
        } else if (nestType < 40) {
            gvGrid.setVisibility(View.VISIBLE);

            if (nestType == GRID_TO_LIST) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.ListType.LISTVIEW);
                gvGrid.setAdapter(gtoAdapter);
            } else if (nestType == GRID_TO_GRID) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.ListType.GRIDVIEW);
                gvGrid.setAdapter(gtoAdapter);
            } else if (nestType == GRID_TO_RECYCLER) {
                gtoAdapter = new GridToOhterAdapter(this, gridList, GridToOhterAdapter.ListType.RECYCLERVIEW);
                gvGrid.setAdapter(gtoAdapter);
            }
        } else if (nestType < 50) {
            svScroll.setVisibility(View.VISIBLE);
            if (nestType == SCROLL_TO_LIST) {
                lvScroll.setVisibility(View.VISIBLE);

                stoAdapter = new ListItemAdapter(this, scrollList, false);
                lvScroll.setAdapter(stoAdapter);
                lvScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("ListView：" + scrollList.get(position).getName());
                    }
                });
            } else if (nestType == SCROLL_TO_GRID) {
                gvScroll.setVisibility(View.VISIBLE);

                stoAdapter = new ListItemAdapter(this, scrollList, true);
                gvScroll.setAdapter(stoAdapter);
                gvScroll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("GridView：" + scrollList.get(position).getName());
                    }
                });
            } else if (nestType == SCROLL_TO_RECYCLER) {
                rvScroll.setVisibility(View.VISIBLE);
            }
        }

        rtoAdapter = new RecyclerToOtherAdapter(this, nestList, listType);
        rvRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvRecycler.setAdapter(rtoAdapter);

        pmAdapter = new PokemenListAdapter(this, scrollList, "all");
        rvScroll.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvScroll.setAdapter(pmAdapter);
        pmAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.toast("RecyclerView：" + scrollList.get(position).getName());
            }
        });
    }

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
}
