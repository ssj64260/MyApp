package com.example.lenovo.myapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cxb.tools.glide.GlideCircleTransform;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.GDPokemon;
import com.example.lenovo.myapp.model.GDProperty;
import com.example.lenovo.myapp.ui.intefaces.OnListClickListener;

import java.util.List;

public class GDPokemonListAdapter extends RecyclerView.Adapter {

    private final int[] property_bg_color = {
            R.drawable.shape_bg_general, R.drawable.shape_bg_fighting, R.drawable.shape_bg_flight,
            R.drawable.shape_bg_poison, R.drawable.shape_bg_ground, R.drawable.shape_bg_rock,
            R.drawable.shape_bg_insect, R.drawable.shape_bg_ghost, R.drawable.shape_bg_steel,
            R.drawable.shape_bg_fire, R.drawable.shape_bg_water, R.drawable.shape_bg_grass,
            R.drawable.shape_bg_electricity, R.drawable.shape_bg_superpower, R.drawable.shape_bg_ice,
            R.drawable.shape_bg_dragon, R.drawable.shape_bg_evil, R.drawable.shape_bg_fairy
    };

    private Context mContext;
    private List<GDPokemon> mDatas;
    private LayoutInflater mInflater;
    private OnListClickListener mListClick;

    private GlideCircleTransform transform;

    public GDPokemonListAdapter(Context context, List<GDPokemon> datas) {
        mContext = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);

        transform = new GlideCircleTransform(context)
                .setColor(192, 192, 192, 1);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(mInflater.inflate(R.layout.item_pokemon_list_all, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindItem((BaseViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void bindItem(BaseViewHolder holder, final int position) {
        final GDPokemon data = mDatas.get(position);

        final String id = data.getId();
        final String name = data.getName();
        final String mega = TextUtils.isEmpty(data.getMega()) ? "00" : data.getMega();
        final String smallLogo = "http://res.pokemon.name/common/pokemon/icons/" + id + "." + mega + ".png";
        final List<GDProperty> pList = data.getProperty();

        Glide.with(mContext)
                .load(smallLogo)
                .transform(transform)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.ic_no_image_circle)
                .error(R.drawable.ic_no_image_circle)
                .into(holder.ivLogo);

        holder.tvId.setText("No." + id);
        holder.tvName.setText(name);

        setProperty(pList, 0, holder.tvProperty1);
        setProperty(pList, 1, holder.tvProperty2);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mClick);
    }

    private void setProperty(List<GDProperty> list, int position, TextView textView) {
        if (list != null && position < list.size()) {
            final GDProperty property = list.get(position);
            final String id = property.getId();
            final String name = property.getName();

            textView.setText(name);
            textView.setVisibility(View.VISIBLE);

            if (!StringCheck.isEmpty(id)) {
                textView.setBackgroundResource(property_bg_color[Integer.parseInt(id) - 1]);
            }
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private class BaseViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivLogo;
        private TextView tvId;
        private TextView tvName;
        private TextView tvProperty1;
        private TextView tvProperty2;

        private BaseViewHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_pm_logo);
            tvId = itemView.findViewById(R.id.tv_pm_id);
            tvName = itemView.findViewById(R.id.tv_pm_name);
            tvProperty1 = itemView.findViewById(R.id.tv_pm_property1);
            tvProperty2 = itemView.findViewById(R.id.tv_pm_property2);
        }
    }

    public void setListClick(OnListClickListener listClick) {
        this.mListClick = listClick;
    }

    private View.OnClickListener mClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListClick != null) {
                final int position = (int) v.getTag();
                mListClick.onItemClick(position);
            }
        }
    };

}
