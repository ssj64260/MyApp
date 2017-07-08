package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.DensityInfo;

import java.util.List;

/**
 *
 */

public class DensityCalculatorAdapter extends RecyclerView.Adapter {

    private List<DensityInfo> mList;
    private LayoutInflater mInflater;

    public DensityCalculatorAdapter(Context context, List<DensityInfo> list) {
        this.mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(mInflater.inflate(R.layout.item_density_calculator, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {
        final DensityInfo density = mList.get(position);
        holder.tvDensityName.setText(density.getDensityName());
        holder.tvDensityDP.setText(Html.fromHtml(density.getDp() + "<font color='yellow'> (dp)</font>"));
        holder.tvDensityPX.setText(Html.fromHtml(density.getPx() + "<font color='green'> (px)</font>"));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvDensityName;
        private TextView tvDensityDP;
        private TextView tvDensityPX;

        private ListViewHolder(View itemView) {
            super(itemView);
            tvDensityName = (TextView) itemView.findViewById(R.id.tv_density_name);
            tvDensityDP = (TextView) itemView.findViewById(R.id.tv_density_dp);
            tvDensityPX = (TextView) itemView.findViewById(R.id.tv_density_px);
        }
    }


}
