package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;

import java.util.List;

import static com.example.lenovo.myapp.ui.intefaces.OnListClickListener.TEXTVIEW;

/**
 * 我的工具
 */

public class MyToolsAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private List<String> list;
    private LayoutInflater inflater;

    public MyToolsAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_my_tools, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {
        holder.tvTools.setText(list.get(position));
        holder.tvTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onTagClick(TEXTVIEW, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTools;

        private ListViewHolder(View view) {
            super(view);
            tvTools = (TextView) view.findViewById(R.id.tv_tools);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
