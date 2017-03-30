package com.seek.usedbooks.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maxleap.GetCallback;
import com.maxleap.MLFile;
import com.maxleap.MLObject;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.SaveCallback;
import com.maxleap.exception.MLException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.seek.usedbooks.ChooseImageUtil;
import com.seek.usedbooks.LoginUser;
import com.seek.usedbooks.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class AccountActivity extends BaseActivity_login{

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_logout)
    AppCompatTextView tvLogout;

    LoginUser currentUser;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.rl_age)
    RelativeLayout rlAge;
    @BindView(R.id.tv_bind_phone)
    AppCompatTextView tvBindPhone;
    @BindView(R.id.tv_bind_email)
    AppCompatTextView tvBindEmail;
    @BindView(R.id.tv_reset_pwd)
    AppCompatTextView tvResetPwd;
    @BindView(R.id.tv_change_pwd)
    AppCompatTextView tvChangePwd;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    private DisplayImageOptions mBaseimageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_account);
        ButterKnife.bind(this);

        currentUser = (LoginUser) MLUser.getCurrentUser(LoginUser.class);

        mBaseimageOptions = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_qq)
                .showImageForEmptyUri(R.mipmap.ic_qq).showImageOnFail(R.mipmap.ic_qq)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY).displayer(new CircleBitmapDisplayer()).build();

        srl.setColorSchemeResources(R.color.color_primary, R.color.text_color_blue, R.color.voucher_color_red);
        srl.setNestedScrollingEnabled(true);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MLUserManager.fetchInBackground(currentUser, new GetCallback() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        srl.setRefreshing(false);
                        initViewData();
                    }
                });

            }
        });
        initViewData();

    }


    private void initViewData() {

        String keyNickName = currentUser.getKeyNickName();

        String keyComment = currentUser.getKeyComment();
        int keyAge = currentUser.getKeyAge();

        String keySex = currentUser.getKeySex();

        tvNickname.setText(TextUtils.isEmpty(keyNickName) ? "点击设置昵称" : keyNickName);

        tvComment.setText(TextUtils.isEmpty(keyComment) ? "点击设置签名" : keyComment);

        tvAge.setText(keyAge == 0 ? "" : keyAge + "");

        tvSex.setText(TextUtils.isEmpty(keySex) ? "" : keySex);

        MLFile mlFile = currentUser.getKeyHeadImage();
        if (mlFile != null && !TextUtils.isEmpty(mlFile.getUrl())) {
            String url = "http://" + mlFile.getUrl();
            ImageLoader.getInstance().displayImage(url, ivHead, mBaseimageOptions, null);
        }

        String mobilePhone = currentUser.getMobilePhone();
        Boolean phoneVerified = currentUser.isPhoneVerified();

        if (mobilePhone != null) {
            tvBindPhone.setText("手机号:" + mobilePhone + (phoneVerified ? "已绑定" : "待绑定"));
        } else {
            tvBindPhone.setText("绑定手机号");
        }

        String email = currentUser.getEmail();
        Boolean emailVerified = currentUser.isEmailVerified();

        if (TextUtils.isEmpty(email)) {
            tvBindEmail.setText("绑定邮箱");
        } else {
            tvBindEmail.setText("邮箱:" + email + (emailVerified ? "已绑定" : "待绑定"));
        }

    }

    private void updateUserInfo(MLUser currentUser) {

        MLUserManager.saveInBackground(currentUser, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    showToast("更新成功");
                    initViewData();
                } else {
                    showToast("更新失败");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 0:// 相册

                if (resultCode == RESULT_OK && data != null && mChooseImageUtil != null) {
                    String imageFromPicker = mChooseImageUtil.getImageFromPicker(this, data);
                    if (!TextUtils.isEmpty(imageFromPicker)) {
                        uploadImage(imageFromPicker);
                    }
                }

                break;

            case 1:// 相机
                if (resultCode == RESULT_OK) {
                    File fs = new File(getExternalCacheDir(), "cache.jpg");
                    if (fs != null && fs.exists()) {
                        uploadImage(fs.getPath());
                    }
                }

                break;

            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mChooseImageUtil.getImageFromCamer();
                } else {
                    Snackbar.make(rlAge, "相机权限,未开启!", 3000).show();
                }
                return;
            }

            case 11: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mChooseImageUtil.getImageFromPick();
                } else {
                    Snackbar.make(rlAge, "SD卡权限,未开启!", 3000).show();
                }
                return;
            }
        }
    }


    private void uploadImage(String path) {
        MLFile f = new MLFile(new File(path));
        currentUser.setKeyHeadImage(f);
        updateUserInfo(currentUser);
    }

    private ChooseImageUtil mChooseImageUtil;

    @OnClick({R.id.iv_head, R.id.tv_nickname, R.id.tv_comment, R.id.rl_sex, R.id.rl_age, R.id.tv_logout, R.id.tv_bind_phone, R.id.tv_bind_email, R.id.tv_reset_pwd, R.id.tv_change_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:

                if (mChooseImageUtil == null) {
                    mChooseImageUtil = new ChooseImageUtil(this);
                }
                mChooseImageUtil.showChooseDialog(null);

                break;
            case R.id.tv_nickname:
                showDialog("设置昵称", tvNickname.getText().toString(), new OnValueChangeListener() {
                    @Override
                    public void onChangedValue(String value) {
                        currentUser.setKeyNickname(value);
                        updateUserInfo(currentUser);
                    }
                });

                break;
            case R.id.tv_comment:
                showDialog("设置签名", tvComment.getText().toString(), new OnValueChangeListener() {
                    @Override
                    public void onChangedValue(String value) {
                        currentUser.setKeyComment(value);
                        updateUserInfo(currentUser);
                    }
                });
                break;
            case R.id.rl_sex:

                showDialog("设置性别", tvSex.getText().toString(), new OnValueChangeListener() {
                    @Override
                    public void onChangedValue(String value) {
                        currentUser.setKeySex(value);
                        updateUserInfo(currentUser);
                    }
                });

                break;
            case R.id.rl_age:

                showDialog("设置年龄", tvAge.getText().toString(), new OnValueChangeListener() {
                    @Override
                    public void onChangedValue(String value) {
                        if (TextUtils.isEmpty(value)) {
                            currentUser.setKeyAge(0);
                            updateUserInfo(currentUser);
                        } else {
                            try {
                                int i = Integer.parseInt(value);
                                currentUser.setKeyAge(i);
                                updateUserInfo(currentUser);
                            } catch (Exception e) {
                                showToast("年龄请输入数字");
                            }
                        }
                    }
                });
                break;
            case R.id.tv_logout:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("确认退出?").setTitle("注销").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        MLUser.logOut();
                        goNext(getString(R.string.action_sign_in), LoginActivity.class);
                        finish();
                    }
                }).show();
                break;
            case R.id.tv_bind_phone:

                goNext(getString(R.string.activity_bind_phone), BindPhoneActivity.class);

                break;
            case R.id.tv_bind_email:

                goNext(getString(R.string.activity_bind_email), BindEmailActivity.class);

                break;

            case R.id.tv_change_pwd:

                if(currentUser != null){
                    goNext(getString(R.string.activity_change_password),ChangePasswordActivity.class);
                }else {
                    showToast("请登录!");
                }

                break;
            case R.id.tv_reset_pwd:

                String mobilePhone = currentUser.getString("mobilePhone");
                Boolean phoneVerified = currentUser.isPhoneVerified();

                if (mobilePhone != null && phoneVerified) {
                    goNext(getString(R.string.activity_reset_password), ForgetPasswordActivity.class);
                } else {
                    showToast("请先绑定手机号!");
                }

                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initViewData();
    }

    private void showDialog(String title, final String value, final OnValueChangeListener listener) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        final EditText et = new EditText(this);
        et.setText(TextUtils.isEmpty(value) ? "" : value);
        et.setSelection(TextUtils.isEmpty(value) ? 0 : value.length());
        dialog.setTitle(title);
        dialog.setView(et);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = et.getText().toString();
                if (!TextUtils.equals(s, value) && listener != null) {
                    listener.onChangedValue(s);
                }
                dialogInterface.dismiss();
            }
        });

        dialog.show();

    }

    interface OnValueChangeListener {
        void onChangedValue(String value);
    }

}
