package com.example.lenovo.myapp.ui.adapter.systemres;

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
import com.example.lenovo.myapp.model.testbean.PhotoBean;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;

import java.util.List;

import static com.example.lenovo.myapp.ui.intefaces.OnListClickListener.LINEARLAYOUT;

/**
 * 相片列表
 */

public class PhotoListAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private List<PhotoBean> list;
    private LayoutInflater inflater;
    private RequestManager requestManager;

    private int itemWidth;
    private int itemHeight;

    public PhotoListAdapter(Context context, List<PhotoBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        itemHeight = itemWidth = DisplayUtil.getScreenWidth(context) / 3;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_photo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {

        ViewGroup.LayoutParams params = holder.view.getLayoutParams();
        params.width = itemWidth;
        params.height = itemHeight;
        holder.view.setLayoutParams(params);

        PhotoBean photo = list.get(position);

        requestManager.load(photo.getData())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_image_circle)
                .error(R.drawable.ic_no_image_circle)
                .dontAnimate()
                .into(holder.ivPhoto);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onItemClick(position);
                }
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onTagClick(LINEARLAYOUT, position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView ivPhoto;

        private ListViewHolder(View view) {
            super(view);
            this.view = view;
            ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
