package com.cxb.tools.MainTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cxb.tools.R;
import com.cxb.tools.utils.StringCheck;

import java.util.List;

/**
 * 服务页面RecyclerView的adapter
 */
public class MainTabAdapter extends RecyclerView.Adapter<MainTabAdapter.MainBoxViewHoder> {

    private OnItemSelectedListener mOnItemSelectedListener;
    //    private WeakReference<Context> context;
    private List<MainTab> list;
    private LayoutInflater mInflater;

    private RequestManager requestManager;

    public MainTabAdapter(Context context, List<MainTab> list) {
//        this.context = new WeakReference<Context>(context);
        this.list = list;
        mInflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
    }

    @Override
    public MainBoxViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_tools_tab, parent, false);
        return new MainBoxViewHoder(view);
    }

    @Override
    public void onBindViewHolder(MainBoxViewHoder holder, int position) {
        bindItem(holder, position);
    }

    private void bindItem(MainBoxViewHoder holder, final int position) {
        MainTab mainTab = list.get(position);

        holder.tv.setText(mainTab.getName());
        if (!StringCheck.isEmpty(mainTab.getUrl())) {
            requestManager.load(mainTab.getUrl())
                    .into(holder.iv);
        } else {
            holder.iv.setImageResource(mainTab.getLogoResource());
        }

        holder.llTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item点击回调
                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemClick(v, position);
                }
            }
        });
    }

    public interface OnItemSelectedListener {
        public void onItemClick(View v, int postion);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener mOnItemSelectedListener) {
        this.mOnItemSelectedListener = mOnItemSelectedListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MainBoxViewHoder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;
        private LinearLayout llTab;
//        public ImageView sign;

        public MainBoxViewHoder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_icon);
            tv = (TextView) view.findViewById(R.id.tv_title);
            llTab = (LinearLayout) view.findViewById(R.id.ll_tab);
        }
    }
}
