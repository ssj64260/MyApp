package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.lenovo.myapp.R;

import java.util.List;

/**
 * 已安装APP列表
 */

public class AppInfoListAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private List<PackageInfo> list;
    private LayoutInflater inflater;
    private RequestManager requestManager;
    private PackageManager pm;

    public AppInfoListAdapter(Context context, List<PackageInfo> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        pm = context.getPackageManager();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_app_info_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {

        PackageInfo pi = list.get(position);

        holder.ivAppIcon.setImageDrawable(pi.applicationInfo.loadIcon(pm));
        holder.tvAppName.setText(pi.applicationInfo.loadLabel(pm).toString());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView ivAppIcon;
        private TextView tvAppName;

        private ListViewHolder(View view) {
            super(view);
            this.view = view;
            ivAppIcon = (ImageView) view.findViewById(R.id.iv_app_icon);
            tvAppName = (TextView) view.findViewById(R.id.tv_app_name);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }

}
