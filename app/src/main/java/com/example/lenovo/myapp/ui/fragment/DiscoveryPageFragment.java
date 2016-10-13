package com.example.lenovo.myapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxb.tools.utils.AssetsUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.ui.adapter.PokemenListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 发现页page
 */

public class DiscoveryPageFragment extends Fragment {

    private static final String KEY_PROPERTY = "property";

    private View view;
    private RecyclerView mRecyclerView;

    private List<PokemonBean> pmList = new ArrayList<>();
    private String property;

    public static DiscoveryPageFragment newInstance(String property) {
        DiscoveryPageFragment fragment = new DiscoveryPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PROPERTY, property);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_discovery_page, null);

            if (getArguments() != null) {
                property = getArguments().getString(KEY_PROPERTY);
            }

            initView();
            setData();
        }

        return view;
    }

    private void initView() {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_pm_list);

    }

    private void setData() {
        String json = AssetsUtil.getAssetsTxtByName(getActivity(), property);
        if (!StringCheck.isEmpty(json)) {
            Type type = new TypeToken<List<PokemonBean>>() {
            }.getType();
            Gson gson = new Gson();
            List<PokemonBean> temp = gson.fromJson(json, type);
            pmList.addAll(temp);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        PokemenListAdapter adapter = new PokemenListAdapter(getActivity(), pmList);
        mRecyclerView.setAdapter(adapter);

    }
}