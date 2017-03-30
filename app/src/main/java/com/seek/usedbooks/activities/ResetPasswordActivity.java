package com.seek.usedbooks.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.maxleap.MLUserManager;
import com.maxleap.ResetPasswordCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.R;
import com.seek.usedbooks.utils.NoUtilCheck;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class ResetPasswordActivity extends BaseActivity_login{

    public final static String INTENT_KEY_RESET_PHONE = "INTENT_KEY_RESET_PHONE";

    private TextInputLayout resetPwdCode;
    private TextInputLayout resetPwd;
    private TextInputLayout resetRepeatPwd;
    private Button confirmBtn;
    private View progressbar;
    private String tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maccount_activity_reset_password);
        init();
    }

    private void init() {
        initView();
    }


    private void initView() {
        tel = getIntent().getStringExtra(INTENT_KEY_RESET_PHONE);
        resetPwdCode = (TextInputLayout) findViewById(R.id.reset_password_code);
        resetPwd = (TextInputLayout) findViewById(R.id.reset_password_pwd);
        resetRepeatPwd = (TextInputLayout) findViewById(R.id.reset_password_repeat_pwd);
        confirmBtn = (Button) findViewById(R.id.reset_password_confirm);
        progressbar = findViewById(R.id.progress_bar_area);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = resetPwdCode.getEditText().getText().toString().trim();
                String pwd = resetPwd.getEditText().getText().toString().trim();
                String pwdRepeat = resetRepeatPwd.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    resetPwdCode.setError(getString(R.string.fragment_login_password_code_empty_error));
                    resetPwdCode.requestFocus();
                } else if (TextUtils.isEmpty(pwd)) {
                    resetPwd.setError(getString(R.string.fragment_login_password_empty_error));
                    resetPwd.requestFocus();
                } else if (pwd.length() < 6 || pwd.length() > 20) {
                    resetPwd.setError(getString(R.string.fragment_login_password_invalid_error));
                    resetPwd.requestFocus();
                } else if (!NoUtilCheck.isReasonable(pwd) || NoUtilCheck.isNumeric(pwd) || NoUtilCheck.isCharacter(pwd)) {
                    resetPwd.setError(getString(R.string.fragment_login_password_safe_error));
                    resetPwd.requestFocus();
                } else if (!pwd.equals(pwdRepeat)) {
                    resetRepeatPwd.setError(getString(R.string.fragment_login_password_reset_error));
                    resetRepeatPwd.requestFocus();
                } else {
                    resetPwdCode.setErrorEnabled(false);
                    resetPwdCode.setError("");
                    resetPwd.setErrorEnabled(false);
                    resetPwd.setError("");
                    resetRepeatPwd.setErrorEnabled(false);
                    resetRepeatPwd.setError("");
                    View view = getCurrentFocus();
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    progressbar.setVisibility(View.VISIBLE);

                    MLUserManager.requestResetPasswordInBackground(tel, code, pwd,
                            new ResetPasswordCallback() {
                                @Override
                                public void done(final MLException e) {
                                    progressbar.setVisibility(View.GONE);
                                    if (e != null) {
                                        //  发生错误
                                        showToast("重置失败:"+e.getMessage());
                                    } else {
                                        //  成功请求
                                        showToast("密码重置成功!");

                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }

}
