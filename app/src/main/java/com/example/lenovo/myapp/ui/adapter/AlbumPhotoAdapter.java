package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cxb.tools.utils.DisplayUtil;
import com.example.lenovo.myapp.R;

import java.util.List;

/**
 * Created by lenovo on 17/1/12.
 */

public class AlbumPhotoAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private List<String> list;
    private LayoutInflater inflater;
    private RequestManager requestManager;
    private int itemWidth;

    public AlbumPhotoAdapter(Context context, List<String> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        itemWidth = DisplayUtil.getScreenWidth(context) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_album_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {

//        holder.ivPhoto.getLayoutParams().width = itemWidth;

        requestManager.load(list.get(position))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_no_image_circle)
                .error(R.mipmap.ic_no_image_circle)
                .dontAnimate()
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;

        private ListViewHolder(View view) {
            super(view);
            ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
