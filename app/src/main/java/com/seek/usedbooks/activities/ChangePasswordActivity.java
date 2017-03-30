package com.seek.usedbooks.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.maxleap.ChangePasswordCallback;
import com.maxleap.MLUserManager;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class ChangePasswordActivity extends BaseActivity_login {

    @BindView(R.id.change_password_old)
    TextInputLayout changePasswordOld;
    @BindView(R.id.change_password_new)
    TextInputLayout changePasswordNew;
    @BindView(R.id.change_password)
    Button changePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.change_password)
    public void onClick() {

        String oldPwd = changePasswordOld.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(oldPwd)) {
            changePasswordOld.setError(getString(R.string.activity_change_old_password));
            changePasswordOld.setErrorEnabled(true);
            return;
        }
        changePasswordOld.setErrorEnabled(false);
        String newPwd = changePasswordNew.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(newPwd)) {
            changePasswordNew.setError(getString(R.string.activity_change_new_password));
            changePasswordNew.setErrorEnabled(true);
            return;
        }

        changePasswordNew.setErrorEnabled(false);
        View view = getCurrentFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        MLUserManager.changePasswordInBackground(oldPwd, newPwd, new ChangePasswordCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    showToast("修改成功");
                    finish();
                } else {
                    showToast(e.getMessage());
                }
            }
        });

    }
}
