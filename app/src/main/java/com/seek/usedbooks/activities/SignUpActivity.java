package com.seek.usedbooks.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxleap.LogInCallback;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.RequestSmsCodeCallback;
import com.maxleap.SignUpCallback;
import com.maxleap.exception.MLException;
import com.seek.usedbooks.LoginUser;
import com.seek.usedbooks.R;
import com.seek.usedbooks.utils.NoUtilCheck;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Walking
 */
public class SignUpActivity extends BaseActivity_login {
    @BindView(R.id.register_tel)
    TextInputLayout registerTel;
    @BindView(R.id.register_verify_code)
    TextInputLayout registerVerifyCode;
    @BindView(R.id.register_verify_code_get)
    TextView registerVerifyCodeGet;
    @BindView(R.id.register_password)
    TextInputLayout registerPassword;
    @BindView(R.id.register_password_visible)
    ImageView registerPasswordVisible;
    @BindView(R.id.register_confirm)
    Button registerConfirm;
    @BindView(R.id.progress_bar_area)
    RelativeLayout progressBarArea;
    @BindView(R.id.rb_type1)
    AppCompatRadioButton rbType1;
    @BindView(R.id.rb_type2)
    AppCompatRadioButton rbType2;
    @BindView(R.id.rb_type3)
    AppCompatRadioButton rbType3;
    @BindView(R.id.rl_code)
    RelativeLayout rlCode;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maccount_activity_register);
        ButterKnife.bind(this);

        rbType1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    registerTel.setHint("请输入用户名");
                    registerTel.getEditText().setText("");
                    registerTel.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                    rlCode.setVisibility(View.GONE);
                    rlPwd.setVisibility(View.VISIBLE);
                }

            }
        });
        rbType2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    registerTel.setHint("请输入手机号");
                    registerTel.getEditText().setText("");
                    registerTel.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
                    rlCode.setVisibility(View.VISIBLE);
                    rlPwd.setVisibility(View.GONE);
                }

            }
        });

        rbType3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    registerTel.setHint("请输入用户名");
                    registerTel.getEditText().setText("");
                    registerTel.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
                    rlCode.setVisibility(View.GONE);
                    rlPwd.setVisibility(View.VISIBLE);
                }

            }
        });

        rbType1.setChecked(true);
    }


    @OnClick({R.id.register_verify_code_get, R.id.register_password_visible, R.id.register_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_verify_code_get:
                if (TextUtils.isEmpty(registerTel.getEditText().getText().toString().trim())) {
                    registerTel.setError(getString(R.string.fragment_login_tel_empty_error));
                    registerTel.requestFocus();
                } else if (!NoUtilCheck.isMobileNo(registerTel.getEditText().getText().toString())) {
                    registerTel.setError(getString(R.string.fragment_login_tel_invalid_error));
                    registerTel.requestFocus();
                } else {
                    registerTel.setErrorEnabled(false);
                    registerTel.setError("");
                    sendSms();
                }
                break;
            case R.id.register_password_visible:

                if (registerPassword.getEditText().getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    registerPasswordVisible.setImageResource(R.mipmap.btn_login_show_press);
                    registerPasswordVisible.setColorFilter(getResources().getColor(R.color.color_primary));
                    registerPassword.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    registerPasswordVisible.setImageResource(R.mipmap.btn_login_show_normal);
                    registerPasswordVisible.clearColorFilter();
                    registerPassword.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                registerPassword.getEditText().setTypeface(Typeface.DEFAULT);
                registerPassword.getEditText().setSelection(registerPassword.getEditText().getText().length());

                break;
            case R.id.register_confirm:
                regist();
                break;
        }
    }

    private String getRegistPwd() {

        final String pwd = registerPassword.getEditText().getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            registerPassword.setError(getString(R.string.fragment_login_password_empty_error));
            registerPassword.requestFocus();
        } else if (pwd.length() < 6 || pwd.length() > 20) {
            registerPassword.setError(getString(R.string.fragment_login_password_invalid_error));
            registerPassword.requestFocus();
        } else if (!NoUtilCheck.isReasonable(pwd) || NoUtilCheck.isNumeric(pwd) || NoUtilCheck.isCharacter(pwd)) {
            registerPassword.setError(getString(R.string.fragment_login_password_safe_error));
            registerPassword.requestFocus();
        } else {

            View view = getCurrentFocus();
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            progressBarArea.setVisibility(View.VISIBLE);

            registerPassword.setErrorEnabled(false);
            registerPassword.setError("");
            registerTel.setErrorEnabled(false);
            registerTel.setError("");
            return pwd;
        }
        return null;
    }


    private void regist() {

        final String tel = registerTel.getEditText().getText().toString();

        if (TextUtils.isEmpty(tel)) {
            registerTel.setError(getString(R.string.fragment_login_tel_empty_error));
            registerTel.requestFocus();
            return;
        }

        if (rbType1.isChecked()) {
            String registPwd = getRegistPwd();
            if (registPwd != null) {
                LoginUser users = new LoginUser();
                users.setUserName(tel);
                users.setPassword(registPwd);
                progressBarArea.setVisibility(View.VISIBLE);
                MLUserManager.signUpInBackground(users, new SignUpCallback() {
                    public void done(MLException e) {
                        progressBarArea.setVisibility(View.GONE);
                        if (e == null) {
                            // 注册成功
                            showToast("注册成功!");
                            finish();

                        } else {
                            // 注册失败
                            showToast(e.getMessage());
                        }
                    }
                });
            }

        } else if (rbType2.isChecked()) {
            String code = registerVerifyCode.getEditText().getText().toString();

            if (!NoUtilCheck.isMobileNo(tel)) {
                registerTel.setError(getString(R.string.fragment_login_tel_invalid_error));
                registerTel.requestFocus();
            } else if (TextUtils.isEmpty(code)) {
                registerVerifyCode.setError(getString(R.string.fragment_login_password_code_empty_error));
                registerVerifyCode.requestFocus();
            } else {
                registerVerifyCode.setErrorEnabled(false);
                registerVerifyCode.setError("");
                registerTel.setErrorEnabled(false);
                registerTel.setError("");

                View view = getCurrentFocus();
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                progressBarArea.setVisibility(View.VISIBLE);

                MLUserManager.loginWithSmsCodeInBackground(tel, code, new LogInCallback() {
                    @Override
                    public void done(MLUser user, MLException e) {
                        progressBarArea.setVisibility(View.GONE);
                        if (e != null) {
                            showToast(e.getMessage());
                        } else {
                            showToast("注册成功!");
                            finish();
                        }
                    }
                });

            }
        } else {
            String registPwd = getRegistPwd();
            if (registPwd != null) {
                LoginUser users = new LoginUser();
                users.setUserName(tel);
                users.setPassword(registPwd);
                progressBarArea.setVisibility(View.VISIBLE);
                MLUserManager.signUpInBackground(users, new SignUpCallback() {
                    public void done(MLException e) {
                        progressBarArea.setVisibility(View.GONE);
                        if (e == null) {
                            // 注册成功
                            goNext(getString(R.string.activity_bind_phone),BindPhoneActivity.class);
                            finish();
                        } else {
                            // 注册失败
                            showToast(e.getMessage());
                        }
                    }
                });
            }

        }
    }


    private void sendSms() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    registerVerifyCodeGet.setEnabled(false);
                    registerVerifyCodeGet.setText(millisUntilFinished / 1000 + "s");
                }

                @Override
                public void onFinish() {
                    registerVerifyCodeGet.setEnabled(true);
                    registerVerifyCodeGet.setText(R.string.activity_register_get_verify_code);
                }
            };
        }
        countDownTimer.start();
        String tel = registerTel.getEditText().getText().toString();

        MLUserManager.requestLoginSmsCodeInBackground(tel, new RequestSmsCodeCallback() {
            @Override
            public void done(final MLException e) {
                if (e != null) {
                    showToast(getString(R.string.fragment_login_get_verify_code_failed));
                    countDownTimer.cancel();
                    registerVerifyCodeGet.setEnabled(true);
                    registerVerifyCodeGet.setText(R.string.activity_register_get_verify_code);
                } else {
                    showToast("发送成功");
                }
            }
        });

    }

}
