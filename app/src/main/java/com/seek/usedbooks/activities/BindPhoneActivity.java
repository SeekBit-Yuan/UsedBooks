package com.seek.usedbooks.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestPhoneVerifyCallback;
import com.maxleap.SaveCallback;
import com.maxleap.VerifyPhoneCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.R;
import com.seek.usedbooks.utils.NoUtilCheck;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class BindPhoneActivity extends BaseActivity_login{

    @BindView(R.id.bind_tel)
    TextInputLayout bindTel;
    @BindView(R.id.bind_verify_code)
    TextInputLayout bindVerifyCode;
    @BindView(R.id.bind_verify_code_get)
    TextView bindVerifyCodeGet;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.bind_confirm)
    Button bindConfirm;
    @BindView(R.id.progress_bar_area)
    RelativeLayout progressBarArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bind_verify_code_get, R.id.bind_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bind_verify_code_get:
                String phone = getPhone();
                if (phone != null) {
                    MLUser currentUser = MLUser.getCurrentUser();
                    currentUser.setMobilePhone(phone);
                    progressBarArea.setVisibility(View.VISIBLE);
                    MLUserManager.saveInBackground(currentUser, new SaveCallback() {
                        @Override
                        public void done(MLException e) {
                            progressBarArea.setVisibility(View.GONE);
                            if (e == null) {
                                sendSms();
                            } else {
                                showToast(e.getMessage());
                            }
                        }
                    });
                }
                break;
            case R.id.bind_confirm:
                String phones = getPhone();
                String code = getCode();
                if (phones != null && code != null) {
                    progressBarArea.setVisibility(View.VISIBLE);
                    MLUserManager.verifyPhoneInBackground(phones, code, new VerifyPhoneCallback() {
                        @Override
                        public void done(final MLException e) {
                            progressBarArea.setVisibility(View.GONE);
                            if (e != null) {
                                showToast(e.getMessage());
                            } else {
                                showToast("绑定成功");
                                finish();
                            }
                        }
                    });
                }
                break;
        }
    }

    private String getPhone() {

        String tel = bindTel.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            bindTel.setError(getString(R.string.activity_register_tel_hint));
            bindTel.requestFocus();
        } else if (!NoUtilCheck.isMobileNo(tel)) {
            bindTel.setError(getString(R.string.fragment_login_tel_invalid_error));
            bindTel.requestFocus();
        } else {
            bindTel.setErrorEnabled(false);
            bindTel.setError("");
            return tel;
        }
        return null;
    }


    private String getCode() {

        String code = bindVerifyCode.getEditText().getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            bindVerifyCode.setError(getString(R.string.activity_register_tel_hint));
            bindVerifyCode.requestFocus();
        } else {
            bindVerifyCode.setErrorEnabled(false);
            bindVerifyCode.setError("");
            return code;
        }
        return null;
    }


    private CountDownTimer countDownTimer;

    private void sendSms() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    bindVerifyCodeGet.setEnabled(false);
                    bindVerifyCodeGet.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    bindVerifyCodeGet.setEnabled(true);
                    bindVerifyCodeGet.setText(R.string.activity_register_get_verify_code);
                }
            };
        }
        countDownTimer.start();

        String phone = bindTel.getEditText().getText().toString();

        MLUserManager.requestPhoneVerifyInBackground(phone, new RequestPhoneVerifyCallback() {

            @Override
            public void done(final MLException e) {
                if (e != null) {
                    showToast(e.getMessage());
                    countDownTimer.cancel();
                    bindVerifyCodeGet.setEnabled(true);
                    bindVerifyCodeGet.setText(R.string.activity_register_get_verify_code);
                } else {
                    showToast("发送成功");
                }
            }
        });

    }
}
