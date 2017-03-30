package com.seek.usedbooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.seek.usedbooks.R;
import com.seek.usedbooks.models.ProductType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductTypeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ProductType> mList;

    public ProductTypeAdapter(Context context, ArrayList<ProductType> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_type, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getTitle());
        Picasso.with(mContext).load(mList.get(position).getIcon()).placeholder(R.mipmap.def_category)
                .into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
