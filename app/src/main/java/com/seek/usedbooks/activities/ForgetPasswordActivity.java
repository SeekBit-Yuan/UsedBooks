package com.seek.usedbooks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.maxleap.MLUserManager;
import com.maxleap.RequestPasswordResetCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.R;
import com.seek.usedbooks.utils.NoUtilCheck;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class ForgetPasswordActivity extends BaseActivity_login{

    private TextInputLayout forgetPwdTel;
    private View progressbar;
    private Button confirmBtn;
    private String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maccount_activity_forget_password);
        init();
    }

    private void init() {
        initView();
    }

    private void initView() {

        progressbar = findViewById(R.id.progress_bar_area);
        forgetPwdTel = (TextInputLayout) findViewById(R.id.forget_password_tel);
        confirmBtn = (Button) findViewById(R.id.forget_password_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt = forgetPwdTel.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(txt)) {
                    forgetPwdTel.setError(getString(R.string.fragment_login_tel_empty_error));
                    forgetPwdTel.requestFocus();
                } else if (!NoUtilCheck.isMobileNo(txt)) {
                    forgetPwdTel.setError(getString(R.string.fragment_login_tel_invalid_error));
                    forgetPwdTel.requestFocus();
                } else {
                    forgetPwdTel.setErrorEnabled(false);
                    forgetPwdTel.setError("");
                    View view = getCurrentFocus();
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    progressbar.setVisibility(View.VISIBLE);

                    MLUserManager.requestPasswordResetByPhoneNumberInBackground(txt, new RequestPasswordResetCallback() {
                        @Override
                        public void done(final MLException e) {
                            progressbar.setVisibility(View.GONE);
                            if (e != null) {
                                //  发生错误
                                showToast(e.getMessage());
                            } else {
                                //  成功请求
                                showToast("发送成功");

                                Intent i = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);

                                i.putExtra(ResetPasswordActivity.INTENT_KEY_RESET_PHONE, txt);
                                startActivityForResult(i, 1);
                                finish();
                            }
                        }
                    });

                }
            }
        });

        forgetPwdTel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6) {
                    confirmBtn.setEnabled(false);
                } else {
                    confirmBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (forgetPwdTel.getEditText().getText().toString().length() < 6) {
            confirmBtn.setEnabled(false);
        } else {
            confirmBtn.setEnabled(true);
        }

    }

}
