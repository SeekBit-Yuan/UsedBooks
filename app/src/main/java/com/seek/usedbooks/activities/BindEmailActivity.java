package com.seek.usedbooks.activities;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestEmailVerifyCallback;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.LoginUser;
import com.seek.usedbooks.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绑定邮箱,会发送邮件至您所填写的邮箱,请确保邮箱的正确性。
 *
 * 邮件发出后,请登录邮箱进行验证。验证成功后,刷新可获最新验证状态。
 *
 */
public class BindEmailActivity extends BaseActivity_login {

    @BindView(R.id.bind_email)
    TextInputLayout bindEmail;
    @BindView(R.id.bind_confirm)
    Button bindConfirm;
    @BindView(R.id.progress_bar_area)
    RelativeLayout progressBarArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bind_email);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.bind_confirm)
    public void onClick() {

        final String trim = bindEmail.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(trim) || !trim.contains("@") || trim.startsWith("@") || trim.endsWith("@")) {
            showToast("请输入正确的邮箱");
            return;
        }
        LoginUser currentUser = (LoginUser) MLUser.getCurrentUser(LoginUser.class);
        currentUser.setEmail(trim);
        progressBarArea.setVisibility(View.VISIBLE);
        MLUserManager.saveInBackground(currentUser, new SaveCallback() {
            @Override
            public void done(MLException e) {
                progressBarArea.setVisibility(View.GONE);
                if (e != null) {
                    showToast(e.getMessage());
                } else {
                    MLUserManager.requestEmailVerifyInBackground(trim, new RequestEmailVerifyCallback() {
                        @Override
                        public void done(MLException e) {
                            if (e != null) {
                                showToast(e.getMessage());
                            } else {
                                showToast("发送成功,请至"+trim+"查看");
                                finish();
                            }
                        }
                    });
                }
            }
        });


    }
}
