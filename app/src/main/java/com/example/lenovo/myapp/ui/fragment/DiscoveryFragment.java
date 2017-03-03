package com.example.lenovo.myapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxb.tools.customviewpager.CustomDepthPageTransformer;
import com.cxb.tools.newstab.HorizontalTabListScrollView;
import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.FastClick;
import com.cxb.tools.utils.StringCheck;
import com.cxb.tools.utils.ThreadPoolUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.PropertyBean;
import com.example.lenovo.myapp.ui.activity.PokemonDetailActivity;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.PokemonListAdapter;
import com.example.lenovo.myapp.ui.adapter.ViewPagerAdapter;
import com.example.lenovo.myapp.ui.base.BaseFragment;
import com.example.lenovo.myapp.ui.dialog.DefaultProgressDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.ui.activity.PokemonDetailActivity.POKEMON;

/**
 * 附近
 */

public class DiscoveryFragment extends BaseFragment {

    private Handler mainThread = new Handler(Looper.getMainLooper());//回调到主线程

    private HorizontalTabListScrollView svNewsTabs;

    private ViewPager mViewPager;
    private List<PokemonListAdapter> adapterList;
    private List<PokemonBean> pmList;

    private List<PropertyBean> propertyList;
    private List<String> propertyNames;

    private List<View> rvList;

    private DefaultProgressDialog progressDialog;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discovery, null);

            initData();
            initView();
            setData();
        }

        return view;
    }

    @Override
    public void onDestroy() {
        if (mainThread != null) {
            mainThread.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void initData(){
        rvList = new ArrayList<>();
        adapterList = new ArrayList<>();
        pmList = new ArrayList<>();
        propertyList = new ArrayList<>();
        propertyNames = new ArrayList<>();

        propertyNames.add(getString(R.string.text_all));
        propertyNames.add(getString(R.string.text_general));
        propertyNames.add(getString(R.string.text_fighting));
        propertyNames.add(getString(R.string.text_flight));
        propertyNames.add(getString(R.string.text_poison));
        propertyNames.add(getString(R.string.text_ground));
        propertyNames.add(getString(R.string.text_rock));
        propertyNames.add(getString(R.string.text_insect));
        propertyNames.add(getString(R.string.text_ghost));
        propertyNames.add(getString(R.string.text_steel));
        propertyNames.add(getString(R.string.text_fire));
        propertyNames.add(getString(R.string.text_water));
        propertyNames.add(getString(R.string.text_grass));
        propertyNames.add(getString(R.string.text_electricity));
        propertyNames.add(getString(R.string.text_superpower));
        propertyNames.add(getString(R.string.text_ice));
        propertyNames.add(getString(R.string.text_dragon));
        propertyNames.add(getString(R.string.text_evil));
        propertyNames.add(getString(R.string.text_fairy));
    }

    private void initView() {
        progressDialog = new DefaultProgressDialog(getActivity());

        mViewPager = (ViewPager) view.findViewById(R.id.vp_fragment);

        svNewsTabs = (HorizontalTabListScrollView) view.findViewById(R.id.htlsv_list);
        svNewsTabs.setOnItemSelectedListener(new HorizontalTabListScrollView.OnItemSelectedListener() {
            @Override
            public void onItemClick(View v, int postion) {
                mViewPager.setCurrentItem(postion);
            }
        });
    }

    private void setData() {
        progressDialog.setMessage(getString(R.string.text_loading));
        progressDialog.showDialog();

        ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                String json = AssetsUtil.getAssetsTxtByName(getActivity(), "property.txt");
                if (!StringCheck.isEmpty(json)) {
                    final List<PropertyBean> propertyTemp = gson.fromJson(json, new TypeToken<List<PropertyBean>>() {
                    }.getType());
                    if (propertyTemp != null) {
                        propertyList.addAll(propertyTemp);
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                svNewsTabs.addTabList(propertyNames);
                                for (int i = 0; i < propertyList.size(); i++) {
                                    final PokemonListAdapter pmAdapter = new PokemonListAdapter(getActivity(), propertyList.get(i).getEn_name());
                                    pmAdapter.setOnListClickListener(new OnListClickListener() {
                                        @Override
                                        public void onItemClick(int position) {
                                            if (!FastClick.isFastClick()) {
                                                Intent intent = new Intent();
                                                intent.setClass(getActivity(), PokemonDetailActivity.class);
                                                intent.putExtra(POKEMON, pmAdapter.getList().get(position));
                                                startActivity(intent);
                                            }
                                        }

                                        @Override
                                        public void onTagClick(int tag, int position) {

                                        }
                                    });
                                    View page = LayoutInflater.from(getActivity()).inflate(R.layout.item_pokemon_viewpager, null);
                                    RecyclerView recyclerView = (RecyclerView) page.findViewById(R.id.rv_pm_page);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(layoutManager);
                                    recyclerView.setAdapter(pmAdapter);
                                    adapterList.add(pmAdapter);
                                    rvList.add(page);
                                }
                                mViewPager.setAdapter(new ViewPagerAdapter(rvList));
                                mViewPager.setPageTransformer(true, new CustomDepthPageTransformer());
                                mViewPager.addOnPageChangeListener(pageChange);
                                mViewPager.setOffscreenPageLimit(2);
                            }
                        });
                    }
                }

                String pmJson = AssetsUtil.getAssetsTxtByName(getActivity(), "all.txt");
                if (!StringCheck.isEmpty(pmJson)) {
                    List<PokemonBean> temp = gson.fromJson(pmJson, new TypeToken<List<PokemonBean>>() {
                    }.getType());
                    if (temp != null && temp.size() > 0) {
                        pmList.addAll(temp);
                    }
                }

                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        if (adapterList.size() > 0) {
                            adapterList.get(0).setList(pmList);
                            adapterList.get(0).notifyDataSetChanged();
                        }
                        progressDialog.dismissDialog();
                    }
                });
            }
        });
    }

    private ViewPager.OnPageChangeListener pageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            svNewsTabs.changeTabByPostion(position);
            if (position < adapterList.size()) {
                progressDialog.showDialog();
                ThreadPoolUtil.getInstache().cachedExecute(new Runnable() {
                    @Override
                    public void run() {
                        final PokemonListAdapter pAdapter = adapterList.get(position);
                        List<PokemonBean> temp = new ArrayList<>();
                        if (pAdapter.getList().size() <= 0) {
                            PropertyBean pb = propertyList.get(position);
                            for (PokemonBean pm : pmList) {
                                List<PropertyBean> pList = pm.getProperty();
                                if (pList != null) {
                                    for (PropertyBean p : pList) {
                                        if (p.getId().equals(pb.getId())) {
                                            temp.add(pm);
                                            break;
                                        }
                                    }
                                }
                            }
                            pAdapter.setList(temp);
                        }
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                pAdapter.notifyDataSetChanged();
                                progressDialog.dismissDialog();
                            }
                        });
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
