package com.example.lenovo.myapp.ui.adapter.nestlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
 * ListView 嵌套 ListView，GridView，RecyclerView
 */

public class ListToOhterAdapter extends BaseAdapter {

    public enum ListType {
        LISTVIEW,
        GRIDVIEW,
        RECYCLERVIEW
    }

    private Context context;
    private List<NestTestBean> list;
    private LayoutInflater inflater;
    private ListType listType;

    private boolean isGrid;

    public ListToOhterAdapter(Context context, List<NestTestBean> list, ListType listType) {
        this.context = context;
        this.listType = listType;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_to_other, parent, false);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.lvItemList = (NoScrollListView) convertView.findViewById(R.id.lv_item_list);
            holder.gvItemList = (NoScrollGridView) convertView.findViewById(R.id.gv_item_grid);
            holder.rvItemList = (OnlyClickRecyclerview) convertView.findViewById(R.id.rv_item_recycler);

            holder.pmList = new ArrayList<>();

            if (listType == ListType.LISTVIEW) {
                holder.lvAdapter = new ListItemAdapter(context, holder.pmList, false);
                holder.lvItemList.setAdapter(holder.lvAdapter);
                holder.lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("ListView：" + holder.pmList.get(position).getName());
                    }
                });
            } else if (listType == ListType.GRIDVIEW) {
                holder.gvAdapter = new ListItemAdapter(context, holder.pmList, true);
                holder.gvItemList.setAdapter(holder.gvAdapter);
                holder.gvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.toast("GridView：" + holder.pmList.get(position).getName());
                    }
                });
            } else if (listType == ListType.RECYCLERVIEW) {
                holder.rvItemList.setVisibility(View.VISIBLE);
            }

            holder.rvAdapter = new PokemenListAdapter(context, holder.pmList, "all");
            holder.layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            holder.rvItemList.setLayoutManager(holder.layoutManager);
            holder.rvItemList.setAdapter(holder.rvAdapter);
            holder.rvAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToastUtil.toast("RecyclerView：" + holder.pmList.get(position).getName());
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NestTestBean nt = list.get(position);

        holder.tvTitle.setText(nt.getTitle());

        holder.lvItemList.setVisibility(View.GONE);
        holder.gvItemList.setVisibility(View.GONE);
        holder.rvItemList.setVisibility(View.GONE);

        holder.pmList.clear();
        holder.pmList.addAll(nt.getPokemons());

        if (listType == ListType.LISTVIEW) {
            holder.lvItemList.setVisibility(View.VISIBLE);
            holder.lvAdapter.notifyDataSetChanged();
        } else if (listType == ListType.GRIDVIEW) {
            holder.gvItemList.setVisibility(View.VISIBLE);
            holder.gvAdapter.notifyDataSetChanged();
        } else if (listType == ListType.RECYCLERVIEW) {
            holder.rvItemList.setVisibility(View.VISIBLE);
            holder.rvAdapter.notifyDataSetChanged();
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle;
        private NoScrollListView lvItemList;
        private NoScrollGridView gvItemList;
        private OnlyClickRecyclerview rvItemList;

        private List<PokemonBean> pmList;

        private ListItemAdapter lvAdapter;
        private ListItemAdapter gvAdapter;

        private LinearLayoutManager layoutManager;
        private PokemenListAdapter rvAdapter;
    }
}
