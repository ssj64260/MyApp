package com.example.lenovo.myapp.ui.adapter.nestlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cxb.tools.CustomList.NoScrollGridView;
import com.cxb.tools.CustomList.NoScrollListView;
import com.cxb.tools.CustomList.OnlyClickRecyclerview;
import com.cxb.tools.utils.ToastUtil;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.testbean.NestTestBean;
import com.example.lenovo.myapp.ui.adapter.MainAdapter;
import com.example.lenovo.myapp.ui.adapter.PokemenListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页列表适配器
 */

public class RecyclerToOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    public enum ListType {
        LISTVIEW,
        GRIDVIEW,
        RECYCLERVIEW
    }

    private Context context;
    private List<NestTestBean> list;
    private LayoutInflater inflater;
    private RecyclerToOtherAdapter.ListType listType;

    public RecyclerToOtherAdapter(Context context, List<NestTestBean> list, RecyclerToOtherAdapter.ListType listType) {
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

        if (listType == RecyclerToOtherAdapter.ListType.LISTVIEW) {
            holder.lvItemList.setVisibility(View.VISIBLE);
            holder.lvAdapter.notifyDataSetChanged();
        } else if (listType == RecyclerToOtherAdapter.ListType.GRIDVIEW) {
            holder.gvItemList.setVisibility(View.VISIBLE);
            holder.gvAdapter.notifyDataSetChanged();
        } else if (listType == RecyclerToOtherAdapter.ListType.RECYCLERVIEW) {
            holder.rvItemList.setVisibility(View.VISIBLE);
            holder.rvAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private NoScrollListView lvItemList;
        private NoScrollGridView gvItemList;
        private OnlyClickRecyclerview rvItemList;

        private List<PokemonBean> pmList;

        private ListItemAdapter lvAdapter;
        private ListItemAdapter gvAdapter;

        private LinearLayoutManager layoutManager;
        private PokemenListAdapter rvAdapter;

        private ListViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            lvItemList = (NoScrollListView) view.findViewById(R.id.lv_item_list);
            gvItemList = (NoScrollGridView) view.findViewById(R.id.gv_item_grid);
            rvItemList = (OnlyClickRecyclerview) view.findViewById(R.id.rv_item_recycler);

            pmList = new ArrayList<>();

            if (listType == RecyclerToOtherAdapter.ListType.LISTVIEW) {
                lvAdapter = new ListItemAdapter(context, pmList, false);
                lvItemList.setAdapter(lvAdapter);
                lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("ListView：" + pmList.get(position).getName());
                    }
                });
            } else if (listType == RecyclerToOtherAdapter.ListType.GRIDVIEW) {
                gvAdapter = new ListItemAdapter(context, pmList, true);
                gvItemList.setAdapter(gvAdapter);
                gvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("GridView：" + pmList.get(position).getName());
                    }
                });
            } else if (listType == RecyclerToOtherAdapter.ListType.RECYCLERVIEW) {
                rvItemList.setVisibility(View.VISIBLE);
            }

            rvAdapter = new PokemenListAdapter(context, pmList, "all");
            layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            rvItemList.setLayoutManager(layoutManager);
            rvItemList.setAdapter(rvAdapter);
            rvAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToastUtil.toast("RecyclerView：" + pmList.get(position).getName());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
