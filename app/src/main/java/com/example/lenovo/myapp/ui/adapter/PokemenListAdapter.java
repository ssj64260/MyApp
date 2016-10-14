package com.example.lenovo.myapp.ui.adapter;

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
import com.cxb.tools.NewsTab.NewsTabResoureUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.PropertyBean;

import java.util.List;

/**
 * 口袋妖怪列表adapter
 */

public class PokemenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;

    private List<PokemonBean> list;

    private RequestManager requestManager;

    public PokemenListAdapter(Context context, List<PokemonBean> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PmViewHolder(layoutInflater.inflate(R.layout.item_pokemon_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindPmItem((PmViewHolder) holder, position);
    }

    private void bindPmItem(PmViewHolder holder, int position) {
        PokemonBean pm = list.get(position);
        String name = pm.getName();

        int mega = 0;

        if (name.contains("超级")) {
            mega = 1;
        }

        if (name.lastIndexOf("Y") == name.length() -1) {
            mega = 2;
        }

        String logoNoBackground = "http://res.pokemon.name/sprites/core/xy/front/" + pm.getId() + ".0" + mega + ".png";
        String smallLogo = "http://res.pokemon.name/common/pokemon/icons/" + pm.getId() + ".0" + mega + ".png";
        String littleBigLogo = "http://res.pokemon.name/common/pokemon/pgl/" + pm.getId() + ".0" + mega + ".png";

        requestManager.load(logoNoBackground)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.mipmap.ic_no_image)
                .error(R.mipmap.ic_no_image)
                .into(holder.ivLogo);
        holder.tvId.setText("No." + pm.getId());
        holder.tvName.setText(pm.getName());

        List<PropertyBean> pList = pm.getProperty();
        if (pList != null) {
            if (pList.size() > 0) {
                PropertyBean pb = pList.get(0);

                holder.tvProperty1.setText(pb.getName());
                holder.tvProperty1.setVisibility(View.VISIBLE);

                if (!StringCheck.isEmpty(pb.getId())) {
                    holder.tvProperty1.setBackgroundResource(NewsTabResoureUtil.characteristic_bg_color[Integer.parseInt(pb.getId()) - 1]);
                }
            } else {
                holder.tvProperty1.setVisibility(View.GONE);
            }
            if (pList.size() == 2) {
                PropertyBean pb = pList.get(1);

                holder.tvProperty2.setText(pb.getName());
                holder.tvProperty2.setVisibility(View.VISIBLE);

                if (!StringCheck.isEmpty(pb.getId())) {
                    holder.tvProperty2.setBackgroundResource(NewsTabResoureUtil.characteristic_bg_color[Integer.parseInt(pb.getId()) - 1]);
                }
            } else {
                holder.tvProperty2.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class PmViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLogo;
        private TextView tvId;
        private TextView tvName;
        private TextView tvProperty1;
        private TextView tvProperty2;
        private TextView tvCharacteristic;

        public PmViewHolder(View itemView) {
            super(itemView);
            ivLogo = (ImageView) itemView.findViewById(R.id.iv_pm_logo);
            tvId = (TextView) itemView.findViewById(R.id.tv_pm_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_pm_name);
            tvProperty1 = (TextView) itemView.findViewById(R.id.tv_pm_property1);
            tvProperty2 = (TextView) itemView.findViewById(R.id.tv_pm_property2);
        }
    }
}
