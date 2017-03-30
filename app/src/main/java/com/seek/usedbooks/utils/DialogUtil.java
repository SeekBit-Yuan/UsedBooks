/**
 * Copyright (c) 2015-present, MaxLeap.
 * All rights reserved.
 * ----
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.seek.usedbooks.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.seek.usedbooks.R;
import com.seek.usedbooks.widget.BaseDialog;

public class DialogUtil {

    public interface Listener {
        void onOk(String content);

        void onCancel();
    }

    public interface UploadPhotoListener {
        void onTakePic();

        void onPickPic();
    }

    public static void showUploadPhotoDialog(Context context, final UploadPhotoListener listener) {
        final Dialog dialog = new BaseDialog(context, R.style.CustomizeDialog);
        dialog.setContentView(R.layout.dialog_upload_photo);
        TextView takeView = (TextView) dialog.findViewById(R.id.dialog_take_pic);
        TextView pickView = (TextView) dialog.findViewById(R.id.dialog_pick_pic);

        takeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTakePic();
                }
                dialog.dismiss();
            }
        });
        pickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPickPic();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showInputInfoDialog(Context context, String title,
                                           String hint, String info,
                                           final Listener listener) {
        final Dialog dialog = new BaseDialog(context, R.style.CustomizeDialog);
        dialog.setContentView(R.layout.dialog_input_info);
        final EditText inputInfo = (EditText) dialog.findViewById(R.id.dialog_input);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView cancelView = (TextView) dialog.findViewById(R.id.dialog_cancel);
        TextView okView = (TextView) dialog.findViewById(R.id.dialog_ok);
        if (title != null) {
            dialogTitle.setText(title);
        }
        if (hint != null) {
            inputInfo.setHint(hint);
        }
        if (info != null) {
            inputInfo.setText(info);
            inputInfo.setSelection(info.length());
        }
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onCancel();
                }
                dialog.dismiss();
            }
        });
        okView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onOk(inputInfo.getText().toString());
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static Dialog createProgressDialog(Context context) {
        final Dialog dialog = new BaseDialog(context, R.style.CustomizeDialog);
        dialog.setContentView(R.layout.dialog_progress);
        return dialog;
    }

}
