package com.example.lenovo.myapp.ui.adapter.systemres;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.AlbumListBean;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;

import java.util.List;

/**
 * 相册列表
 */

public class AlbumListAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private List<AlbumListBean> list;
    private LayoutInflater inflater;
    private RequestManager requestManager;

    public AlbumListAdapter(Context context, List<AlbumListBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_album_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {
        AlbumListBean al = list.get(position);

        requestManager.load(al.getIcon())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_image_circle)
                .error(R.drawable.ic_no_image_circle)
                .dontAnimate()
                .into(holder.ivIcon);
        holder.tvName.setText(al.getName() + " (" + al.getPhotos().size() + ")");

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
        private ImageView ivIcon;
        private TextView tvName;

        private ListViewHolder(View view) {
            super(view);
            this.view = view;
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
