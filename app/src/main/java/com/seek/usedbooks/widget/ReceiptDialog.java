/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.seek.usedbooks.R;
import com.seek.usedbooks.adapters.ReceiptInfoAdapter;

public class ReceiptDialog implements View.OnClickListener {

    private Context mContext;
    private Dialog dialog;
    private ListView listView;
    private TextView dialogTitle;
    private TextView cancelBtn;
    private TextView confirm;
    private EditText headingInput;
    private ChooseListener chooseListener;
    private ReceiptInfoAdapter receiptInfoAdapter;
    private String[] receiptTypes;
    private String[] receiptContents;
    private String selectedType;
    private String selectedContent;
    private String headingText;

    public ReceiptDialog(Context context, ChooseListener chooseListener) {
        this.mContext = context;
        this.chooseListener = chooseListener;
        initUI();
    }

    private void initUI() {
        dialog = new BaseDialog(mContext, R.style.CustomizeDialog);
        dialog.setContentView(R.layout.dialog_receipt);
        listView = (ListView) dialog.findViewById(R.id.receipt_list);
        dialogTitle = (TextView) dialog.findViewById(R.id.receipt_title);
        cancelBtn = (TextView) dialog.findViewById(R.id.receipt_cancel);
        cancelBtn.setOnClickListener(this);
        confirm = (TextView) dialog.findViewById(R.id.receipt_confirm);
        confirm.setOnClickListener(this);
        headingInput = (EditText) dialog.findViewById(R.id.receipt_heading);
        receiptTypes = new String[]{mContext.getString(R.string.receipt_dialog_type1),
                mContext.getString(R.string.receipt_dialog_type2)};
        receiptContents = new String[]{mContext.getString(R.string.receipt_dialog_content1),
                mContext.getString(R.string.receipt_dialog_content2),
                mContext.getString(R.string.receipt_dialog_content3),
                mContext.getString(R.string.receipt_dialog_content4)};
    }

    public interface ChooseListener {
        void onChooseType(String type);

        void onChooseContent(String content);

        void onInputHeading(String heading);
    }

    public void showReceipt() {
        if (TextUtils.isEmpty(headingText)) {
            selectedType = null;
            selectedContent = null;
        }
        if (receiptInfoAdapter == null) {
            receiptInfoAdapter = new ReceiptInfoAdapter(mContext, receiptTypes, typeSelectListener, selectedType);
            listView.setAdapter(receiptInfoAdapter);
        } else {
            receiptInfoAdapter.setData(receiptTypes, typeSelectListener, selectedType);
        }
        dialogTitle.setText(R.string.receipt_dialog_type_title);
        cancelBtn.setText(R.string.receipt_dialog_cancel);
        confirm.setText(R.string.receipt_dialog_next);
        listView.setVisibility(View.VISIBLE);
        headingInput.setVisibility(View.GONE);
        dialog.show();
    }

    ReceiptInfoAdapter.SelectListener typeSelectListener = new ReceiptInfoAdapter.SelectListener() {
        @Override
        public void onSelect(int position) {
            selectedType = receiptTypes[position];
        }
    };

    ReceiptInfoAdapter.SelectListener contentSelectListener = new ReceiptInfoAdapter.SelectListener() {
        @Override
        public void onSelect(int position) {
            selectedContent = receiptContents[position];
        }
    };

    @Override
    public void onClick(View v) {
        String title = dialogTitle.getText().toString();
        switch (v.getId()) {
            case R.id.receipt_cancel:
                if (title.equals(mContext.getString(R.string.receipt_dialog_type_title))) {
                    dialog.dismiss();
                } else if (title.equals(mContext.getString(R.string.receipt_dialog_content_title))) {
                    receiptInfoAdapter.setData(receiptTypes, typeSelectListener, selectedType);
                    dialogTitle.setText(R.string.receipt_dialog_type_title);
                    cancelBtn.setText(R.string.receipt_dialog_cancel);
                } else if (title.equals(mContext.getString(R.string.receipt_dialog_heading_title))) {
                    receiptInfoAdapter.setData(receiptContents, contentSelectListener, selectedContent);
                    dialogTitle.setText(R.string.receipt_dialog_content_title);
                    confirm.setText(R.string.receipt_dialog_next);
                    listView.setVisibility(View.VISIBLE);
                    headingInput.setVisibility(View.GONE);
                }
                break;
            case R.id.receipt_confirm:
                if (title.equals(mContext.getString(R.string.receipt_dialog_type_title))) {
                    if (chooseListener != null && !TextUtils.isEmpty(selectedType)) {
                        chooseListener.onChooseType(selectedType);
                    } else {
                        return;
                    }
                    receiptInfoAdapter.setData(receiptContents, contentSelectListener, selectedContent);
                    dialogTitle.setText(R.string.receipt_dialog_content_title);
                    cancelBtn.setText(R.string.receipt_dialog_forward);
                } else if (title.equals(mContext.getString(R.string.receipt_dialog_content_title))) {
                    if (chooseListener != null && selectedContent != null) {
                        chooseListener.onChooseContent(selectedContent);
                    } else {
                        return;
                    }
                    listView.setVisibility(View.GONE);
                    headingInput.setVisibility(View.VISIBLE);
                    headingInput.setFocusable(true);
                    headingInput.setFocusableInTouchMode(true);
                    headingInput.requestFocus();
                    if (!TextUtils.isEmpty(headingText)) {
                        headingInput.setText(headingText);
                    }
                    dialogTitle.setText(R.string.receipt_dialog_heading_title);
                    confirm.setText(R.string.receipt_dialog_confirm);
                } else if (title.equals(mContext.getString(R.string.receipt_dialog_heading_title))) {
                    if (chooseListener != null && !TextUtils.isEmpty(headingInput.getText())) {
                        chooseListener.onInputHeading(headingInput.getText().toString());
                        headingText = headingInput.getText().toString();
                    } else {
                        return;
                    }
                    dialog.dismiss();
                }
                break;
            default:
                break;
        }
    }
}
