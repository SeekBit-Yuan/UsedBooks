/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.seek.usedbooks.R;

public class ReceiptInfoAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mData;
    private SelectListener mListener;
    private String mDefaultValue;

    public ReceiptInfoAdapter(Context context, String[] data, SelectListener listener, String defaultValue) {
        mContext = context;
        mData = data;
        mListener = listener;
        mDefaultValue = defaultValue;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int position) {
        return mData[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_radio, parent, false);
            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.radio_title);
            holder.radioView = (RadioButton) convertView.findViewById(R.id.radio);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titleView.setText(mData[position]);
        holder.radioView.setChecked(mData[position].equals(mDefaultValue));
        holder.radioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelect(position);
                    mDefaultValue = mData[position];
                    notifyDataSetChanged();
                }
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelect(position);
                    mDefaultValue = mData[position];
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView titleView;
        RadioButton radioView;
    }

    public void setData(String[] data, SelectListener listener, String defaultValue) {
        mData = data;
        mListener = listener;
        mDefaultValue = defaultValue;
        notifyDataSetChanged();
    }

    public interface SelectListener {
        void onSelect(int position);
    }
}
