package com.example.lenovo.myapp.ui.adapter.nestlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.cxb.tools.utils.DisplayUtil;
import com.cxb.tools.utils.ListFixedHeightUtils;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.testbean.NestTestBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;
import com.example.lenovo.myapp.ui.adapter.PokemonListAdapter;
import com.example.lenovo.myapp.utils.ToastMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView 嵌套 ListView，GridView，RecyclerView
 */

public class ListToOhterAdapter extends BaseAdapter {

    public static final int LISTVIEW = 0;
    public static final int GRIDVIEW = 1;
    public static final int RECYCLERVIEW = 2;

    private Context context;
    private List<NestTestBean> list;
    private LayoutInflater inflater;
    private int listType;

    public ListToOhterAdapter(Context context, List<NestTestBean> list, int listType) {
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
            holder.lvItemList = (ListView) convertView.findViewById(R.id.lv_item_list);
            holder.gvItemList = (GridView) convertView.findViewById(R.id.gv_item_grid);
            holder.rvItemList = (RecyclerView) convertView.findViewById(R.id.rv_item_recycler);

            holder.pmList = new ArrayList<>();

            if (listType == LISTVIEW) {
                holder.lvAdapter = new ListItemAdapter(context, holder.pmList);
                holder.lvItemList.setAdapter(holder.lvAdapter);
                holder.lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("ListView：" + holder.pmList.get(position).getName());
                    }
                });
            } else if (listType == GRIDVIEW) {
                holder.gvAdapter = new ListItemAdapter(context, holder.pmList);
                holder.gvItemList.setAdapter(holder.gvAdapter);
                holder.gvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastMaster.toast("GridView：" + holder.pmList.get(position).getName());
                    }
                });
            } else if (listType == RECYCLERVIEW) {
                holder.rvAdapter = new PokemonListAdapter(context, "all");
                holder.rvAdapter.setOnListClickListener(new OnListClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        ToastMaster.toast("RecyclerView：" + holder.pmList.get(position).getName());
                    }

                    @Override
                    public void onTagClick(int tag, int position) {

                    }
                });
                holder.layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                holder.rvItemList.setHasFixedSize(true);
                holder.rvItemList.setNestedScrollingEnabled(false);
                holder.rvItemList.setLayoutManager(holder.layoutManager);
                holder.rvItemList.setAdapter(holder.rvAdapter);
            }

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

        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle;
        private ListView lvItemList;
        private GridView gvItemList;
        private RecyclerView rvItemList;

        private List<PokemonBean> pmList;

        private ListItemAdapter lvAdapter;
        private ListItemAdapter gvAdapter;

        private LinearLayoutManager layoutManager;
        private PokemonListAdapter rvAdapter;
    }
}
