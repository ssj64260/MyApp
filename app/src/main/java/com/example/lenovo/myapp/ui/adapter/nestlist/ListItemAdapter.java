package com.example.lenovo.myapp.ui.adapter.nestlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

import java.util.List;

/**
 * ListView 或 GridView 适配器
 */

public class ListItemAdapter extends BaseAdapter {

    private List<PokemonBean> list;
    private LayoutInflater inflater;
    private RequestManager requestManager;
    private GlideCircleTransform transform;

    public ListItemAdapter(Context context, List<PokemonBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        requestManager = Glide.with(context);
        transform = new GlideCircleTransform(context)
                .setColor(192, 192, 192, 1);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_pm_list_grid, parent, false);
            holder.ivLogo = (ImageView) convertView.findViewById(R.id.iv_pm_logo);
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_pm_id);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_pm_name);
            holder.tvProperty1 = (TextView) convertView.findViewById(R.id.tv_pm_property1);
            holder.tvProperty2 = (TextView) convertView.findViewById(R.id.tv_pm_property2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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

        requestManager.load(smallLogo)
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

        return convertView;
    }

    private class ViewHolder {
        private ImageView ivLogo;
        private TextView tvId;
        private TextView tvName;
        private TextView tvProperty1;
        private TextView tvProperty2;
    }
}
