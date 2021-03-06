package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cxb.tools.myprogressbar.MyProgressBar;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.MainListBean;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;

import java.util.List;

/**
 * 主页列表适配器
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnListClickListener onListClickListener;

    private List<MainListBean> list;
    private LayoutInflater layoutInflater;

    private static final int NO_HEAD_ITEM = 0;
    private static final int HEAD_ITEM = 1;

    public MainAdapter(Context context, List<MainListBean> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return HEAD_ITEM;
        }

        int prePosition = position - 1;
        MainListBean pmlb = list.get(prePosition);
        MainListBean cmlb = list.get(position);

        if (pmlb.getHead().equals(cmlb.getHead())) {
            return NO_HEAD_ITEM;
        }

        return HEAD_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == NO_HEAD_ITEM) {
            return new NoHeadViewHolder(layoutInflater.inflate(R.layout.item_main_list_base, parent, false));
        } else {
            return new HeadViewHolder(layoutInflater.inflate(R.layout.item_main_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            bindHeadItem((HeadViewHolder) holder, position);
        } else {
            bindNoHeadItem((NoHeadViewHolder) holder, position, true);
        }
    }

    private void bindNoHeadItem(NoHeadViewHolder holder, final int position, boolean isNoHead) {

        MainListBean ml = list.get(position);

        holder.name.setText(String.valueOf(ml.getName()));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onItemClick(position);
                }
            }
        });
        holder.line.setVisibility(isNoHead ? View.VISIBLE : View.GONE);

        holder.mpbProgress.setCurrentNumber(ml.getNumber());
        if (ml.isShowAnimation()) {
            ml.setShowAnimation(false);
            holder.mpbProgress.showAnimation();
        }
    }

    private void bindHeadItem(HeadViewHolder holder, int position) {
        bindNoHeadItem(holder, position, false);

        holder.head.setText(list.get(position).getHead());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class HeadViewHolder extends NoHeadViewHolder {
        private TextView head;

        private HeadViewHolder(View view) {
            super(view);
            head = (TextView) view.findViewById(R.id.tv_header);
        }
    }

    private class NoHeadViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private View line;
        private MyProgressBar mpbProgress;

        private NoHeadViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            line = view.findViewById(R.id.view_line);
            mpbProgress = (MyProgressBar) view.findViewById(R.id.mpb_progress);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
