package com.example.lenovo.myapp.ui.adapter.nestlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.ListFixedHeightUtils;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.testbean.NestTestBean;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.PokemonListAdapter;
import com.example.lenovo.myapp.utils.ToastMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页列表适配器
 */

public class RecyclerToOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LISTVIEW = 0;
    public static final int GRIDVIEW = 1;
    public static final int RECYCLERVIEW = 2;

    private Context context;
    private List<NestTestBean> list;
    private LayoutInflater inflater;
    private int listType;

    public RecyclerToOtherAdapter(Context context, List<NestTestBean> list, int listType) {
        this.context = context;
        this.listType = listType;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_list_to_other, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {
        NestTestBean nt = list.get(position);

        holder.tvTitle.setText(nt.getTitle());

        holder.lvItemList.setVisibility(View.GONE);
        holder.gvItemList.setVisibility(View.GONE);
        holder.rvItemList.setVisibility(View.GONE);

        holder.pmList.clear();
        holder.pmList.addAll(nt.getPokemons());

        if (listType == LISTVIEW) {
            holder.lvItemList.setVisibility(View.VISIBLE);

            int itemHeight = DisplayUtil.dip2px(context, 57);
            ListFixedHeightUtils.getListViewHeight(holder.lvItemList, itemHeight, holder.pmList.size());

            holder.lvAdapter.notifyDataSetChanged();
        } else if (listType == GRIDVIEW) {
            holder.gvItemList.setVisibility(View.VISIBLE);

            int itemHeight = DisplayUtil.dip2px(context, 57);
            ListFixedHeightUtils.getGridViewHeight(holder.gvItemList, 2, itemHeight, holder.pmList.size());

            holder.gvAdapter.notifyDataSetChanged();
        } else if (listType == RECYCLERVIEW) {
            holder.rvItemList.setVisibility(View.VISIBLE);
            holder.rvAdapter.setList(holder.pmList);
            holder.rvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ListView lvItemList;
        private GridView gvItemList;
        private RecyclerView rvItemList;

        private List<PokemonBean> pmList;

        private ListItemAdapter lvAdapter;
        private ListItemAdapter gvAdapter;

        private LinearLayoutManager layoutManager;
        private PokemonListAdapter rvAdapter;

        private ListViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            lvItemList = (ListView) view.findViewById(R.id.lv_item_list);
            gvItemList = (GridView) view.findViewById(R.id.gv_item_grid);
            rvItemList = (RecyclerView) view.findViewById(R.id.rv_item_recycler);

            pmList = new ArrayList<>();

            if (listType == LISTVIEW) {
                lvAdapter = new ListItemAdapter(context, pmList);
                lvItemList.setAdapter(lvAdapter);
                lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("ListView：" + pmList.get(position).getName());
                    }
                });
            } else if (listType == GRIDVIEW) {
                gvAdapter = new ListItemAdapter(context, pmList);
                gvItemList.setAdapter(gvAdapter);
                gvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("GridView：" + pmList.get(position).getName());
                    }
                });
            } else if (listType == RECYCLERVIEW) {
                rvAdapter = new PokemonListAdapter(context, "all");
                rvAdapter.setOnListClickListener(new OnListClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ToastMaster.toast("RecyclerView：" + pmList.get(position).getName());
                    }

                    @Override
                    public void onTagClick(int tag, int position) {

                    }
                });
                layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                rvItemList.setHasFixedSize(true);
                rvItemList.setNestedScrollingEnabled(false);
                rvItemList.setLayoutManager(layoutManager);
                rvItemList.setAdapter(rvAdapter);
            }
        }
    }
}
