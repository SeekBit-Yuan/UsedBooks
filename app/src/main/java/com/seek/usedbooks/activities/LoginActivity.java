package com.seek.usedbooks.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxleap.LogInCallback;
import com.maxleap.MLAnonymousUtils;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.LoginUser;
import com.seek.usedbooks.R;
import com.seek.usedbooks.fragments.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by 一丝不狗 on 2017/3/24.
 */

public class LoginActivity extends BaseActivity_login implements View.OnClickListener{

    @BindView(R.id.login_tel)
    TextInputLayout loginTel;
    @BindView(R.id.login_password)
    TextInputLayout loginPassword;
    @BindView(R.id.login_password_visible)
    ImageView loginPasswordVisible;
    @BindView(R.id.login_confirm)
    Button loginConfirm;
    @BindView(R.id.login_to_register)
    TextView loginToRegister;
    @BindView(R.id.login_forget_password)
    TextView loginForgetPassword;
    @BindView(R.id.login_qq)
    ImageButton loginQq;
    @BindView(R.id.login_weibo)
    ImageButton loginWeibo;
    @BindView(R.id.login_wechat)
    ImageButton loginWechat;
    @BindView(R.id.progress_bar_area)
    RelativeLayout progressBarArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MLUser currentUser = MLUser.getCurrentUser();
        //非匿名用户登录
        if (currentUser != null && !MLAnonymousUtils.isLinked(currentUser)) {
            goMain();
        }
    }


    @OnClick({R.id.login_password_visible, R.id.login_confirm, R.id.login_to_register, R.id.login_forget_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_password_visible:

                if (loginPassword.getEditText().getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    loginPasswordVisible.setImageResource(R.mipmap.btn_login_show_press);
                    loginPasswordVisible.setColorFilter(mContext.getResources().getColor(R.color.color_primary));
                    loginPassword.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    loginPasswordVisible.setImageResource(R.mipmap.btn_login_show_normal);
                    loginPasswordVisible.clearColorFilter();
                    loginPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                loginPassword.getEditText().setTypeface(Typeface.DEFAULT);
                loginPassword.getEditText().setSelection(loginPassword.getEditText().getText().length());

                break;
            case R.id.login_confirm:

                final String tel = loginTel.getEditText().getText().toString();
                final String pwd = loginPassword.getEditText().getText().toString();

                if (TextUtils.isEmpty(tel) || TextUtils.isEmpty(pwd)) {
                    return;
                }
                View fview = getCurrentFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(fview.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                progressBarArea.setVisibility(View.VISIBLE);

                MLUserManager.logInInBackground(tel, pwd, LoginUser.class, new LogInCallback() {
                    @Override
                    public void done(MLUser mlUser, MLException e) {
                        progressBarArea.setVisibility(View.GONE);
                        if (e == null) {
                            showToast("登录成功:" + mlUser.getUserName());
                            goMain();
                        } else {
                            showToast("登录失败:" + e.getMessage());
                        }

                    }
                });

                break;
            case R.id.login_to_register:
//                goNext(getString(R.string.activity_register_title), SignUpActivity.class);
                Intent intent_signup = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent_signup);
                break;
            case R.id.login_forget_password:
//                goNext(getString(R.string.activity_forget_password_title), ForgetPasswordActivity.class);
                Intent intent_forgetpwd = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent_forgetpwd);
                break;
            case R.id.login_qq:
                break;
            case R.id.login_weibo:
                break;
            case R.id.login_wechat:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (MLUser.getCurrentUser() != null && !MLAnonymousUtils.isLinked(MLUser.getCurrentUser())) {
            goMain();
        }
    }

    private void goMain() {
        goNext(MLUser.getCurrentUser().getUserName(), MainActivity.class);
        finish();
    }

}
