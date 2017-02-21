package com.example.lenovo.myapp.ui.adapter.systemres;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cxb.tools.glide.GlideCircleTransform;
import com.cxb.tools.utils.ImageUtil;
import com.cxb.tools.utils.StringCheck;
import com.example.lenovo.myapp.R;
import com.example.lenovo.myapp.model.testbean.Contact;
import com.example.lenovo.myapp.ui.adapter.OnListClickListener;

import java.util.List;

/**
 * 联系人列表
 */

public class ContactListAdapter extends RecyclerView.Adapter {

    private OnListClickListener onListClickListener;

    private Context context;
    private List<Contact> list;
    private LayoutInflater inflater;

    public ContactListAdapter(Context context, List<Contact> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(inflater.inflate(R.layout.item_contact_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindNoHeadItem((ListViewHolder) holder, position);
    }

    private void bindNoHeadItem(ListViewHolder holder, final int position) {
        Contact contact = list.get(position);
        String sortKey = contact.getPhonebookLabelAlt();
        String name = contact.getDisplayName();
        String avatarUri = contact.getPhotoThumbUri();

        if (position == 0) {
            holder.tvSortKey.setVisibility(View.VISIBLE);
        } else {
            int prePosition = position - 1;
            Contact pContact = list.get(prePosition);
            if (pContact.getPhonebookLabelAlt().equals(contact.getPhonebookLabelAlt())) {
                holder.tvSortKey.setVisibility(View.GONE);
            } else {
                holder.tvSortKey.setVisibility(View.VISIBLE);
            }
        }
        holder.tvSortKey.setText(sortKey);

        if (StringCheck.isEmpty(avatarUri)) {
            holder.tvAvatar.setText(name.substring(0, 1));
            holder.ivAvatar.setVisibility(View.GONE);
        } else {
            holder.ivAvatar.setVisibility(View.VISIBLE);
            GlideCircleTransform transform = new GlideCircleTransform(context)
                    .setColor(192, 192, 192, 1);
            Glide.with(context).load(ImageUtil.getContentContactAvatar(avatarUri, context))
                    .transform(transform)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.ic_no_image_circle)
                    .error(R.drawable.ic_no_image_circle)
                    .into(holder.ivAvatar);
        }

        holder.tvName.setText(name);

        holder.rlCantact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onListClickListener != null) {
                    onListClickListener.onItemClick(position);
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

        private RelativeLayout rlCantact;
        private TextView tvSortKey;
        private ImageView ivAvatar;
        private TextView tvAvatar;
        private TextView tvName;

        private ListViewHolder(View view) {
            super(view);
            rlCantact = (RelativeLayout) view.findViewById(R.id.rl_contact);
            tvSortKey = (TextView) view.findViewById(R.id.tv_sort_key);
            ivAvatar = (ImageView) view.findViewById(R.id.iv_avatar);
            tvAvatar = (TextView) view.findViewById(R.id.tv_avatar);
            tvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }

    public void setOnListClickListener(OnListClickListener onListClickListener) {
        this.onListClickListener = onListClickListener;
    }
}
