package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cxb.tools.glide.GlideCircleTransform;
import com.cxb.tools.newstab.NewsTabResoureUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.PokemonBean;
import com.example.lenovo.myapp.model.PropertyBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.myapp.ui.adapter.OnListClickListener.Tag.LONGCLICK;

/**
 * 口袋妖怪列表adapter
 */

public class PokemonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ALL = "all";

    private OnListClickListener onListClickListener;

    private LayoutInflater layoutInflater;
    private List<PokemonBean> list;
    private RequestManager requestManager;
    private String type;

    private GlideCircleTransform transform;

    public PokemonListAdapter(Context context, String type) {
        this.list = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        this.type = type;

        transform = new GlideCircleTransform(context)
                .setColor(192, 192, 192, 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if (ALL.equals(type)) {
            layout = R.layout.item_pokemon_list_all;
        } else {
            layout = R.layout.item_pokemon_list;
        }

        return new PmViewHolder(layoutInflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindPmItem((PmViewHolder) holder, position);
    }

    private void bindPmItem(PmViewHolder holder, final int position) {
        PokemonBean pm = list.get(position);
        String id = pm.getId();
        String mega = pm.getMega();

        if (StringCheck.isEmpty(mega)) {
            mega = "00";
        }

        String noBackgroundLogo = "http://res.pokemon.name/sprites/core/xy/front/" + id + "." + mega + ".png";
        String smallLogo = "http://res.pokemon.name/common/pokemon/icons/" + id + "." + mega + ".png";
        String littleBigLogo = "http://res.pokemon.name/common/pokemon/pgl/" + id + "." + mega + ".png";

        String genuineSmallLogo = "http://www.koudai8.com/pmdex/img/pm/cg/" + id + ".png";
        String detailWeb = "https://wiki.52poke.com/wiki/" + pm.getName();

        String url;

        if (ALL.equals(type)) {
            url = smallLogo;
        } else {
            url = noBackgroundLogo;
        }

        requestManager.load(url)
                .transform(transform)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_image_circle)
                .error(R.drawable.ic_no_image_circle)
                .into(holder.ivLogo);
        holder.tvId.setText("No." + id);
        holder.tvName.setText(pm.getName());

        List<PropertyBean> pList = pm.getProperty();
        if (pList != null) {
            if (pList.size() > 0) {
                PropertyBean pb = pList.get(0);

                holder.tvProperty1.setText(pb.getName());
                holder.tvProperty1.setVisibility(View.VISIBLE);

                if (!StringCheck.isEmpty(pb.getId())) {
                    holder.tvProperty1.setBackgroundResource(NewsTabResoureUtil.property_bg_color[Integer.parseInt(pb.getId()) - 1]);
                }
            } else {
                holder.tvProperty1.setVisibility(View.GONE);
            }
            if (pList.size() == 2) {
                PropertyBean pb = pList.get(1);

                holder.tvProperty2.setText(pb.getName());
                holder.tvProperty2.setVisibility(View.VISIBLE);

                if (!StringCheck.isEmpty(pb.getId())) {
                    holder.tvProperty2.setBackgroundResource(NewsTabResoureUtil.property_bg_color[Integer.parseInt(pb.getId()) - 1]);
                }
            } else {
                holder.tvProperty2.setVisibility(View.GONE);
            }
        }

        holder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onItemClick(position);
                }
            }
        });

        holder.all.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onTagClick(LONGCLICK,position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class PmViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout all;
        private ImageView ivLogo;
        private TextView tvId;
        private TextView tvName;
        private TextView tvProperty1;
        private TextView tvProperty2;

        public PmViewHolder(View itemView) {
            super(itemView);
            all = (RelativeLayout) itemView.findViewById(R.id.rl_all);
            ivLogo = (ImageView) itemView.findViewById(R.id.iv_pm_logo);
            tvId = (TextView) itemView.findViewById(R.id.tv_pm_id);
            tvName = (TextView) itemView.findViewById(R.id.tv_pm_name);
            tvProperty1 = (TextView) itemView.findViewById(R.id.tv_pm_property1);
            tvProperty2 = (TextView) itemView.findViewById(R.id.tv_pm_property2);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }

    public void setList(List<PokemonBean> list) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.list.clear();
        this.list.addAll(list);
    }

    public List<PokemonBean> getList() {
        return list;
    }
}
