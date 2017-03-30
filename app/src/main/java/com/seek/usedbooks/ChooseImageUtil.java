package com.seek.usedbooks;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.io.File;

/**
 * Created by 一丝不狗 on 2017/3/25.
 */

public class ChooseImageUtil {

    private Activity act;

    public ChooseImageUtil(Activity act) {
        this.act = act;
    }

    public void showChooseDialog(View animateView) {

        final String stringItems[] = new String[]{"拍照", "手机相册"};
        final ActionSheetDialog dialog = new ActionSheetDialog(act, stringItems, animateView);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        checkPermissionCamera();
                        break;
                    case 1:
                        checkPermissionReadExternal();
                        break;

                    default:
                        break;
                }
                dialog.dismiss();
            }
        });

    }

    public void getImageFromPick() {

        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        act.startActivityForResult(intent, 0);

    }

    private void checkPermissionCamera() {

        if (ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.CAMERA)) {
                Toast.makeText(act, "相机权限未开启", 0).show();
            } else {
                //第一次进来：让用户授权，或者用户选择了不再提醒
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.CAMERA}, 10);
            }
        } else {
            getImageFromCamer();
        }
    }


    private void checkPermissionReadExternal() {

        if (ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(act, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                //第二次用户再进来：之前拒绝了该权限请求，所以提示用户无权限

                Toast.makeText(act, "SD卡权限未开启", 0).show();


            } else {
                //第一次进来：让用户授权，或者用户选择了不再提醒
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 11);

            }
        } else {
            getImageFromPick();

        }
    }


    public void getImageFromCamer() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径

        File userImageCacheDir = act.getExternalCacheDir();

        File fs = new File(userImageCacheDir, "cache.jpg");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fs));

        act.startActivityForResult(intent, 1);
    }


    public String getImageFromPicker(Activity act, Intent data) {
        String res = null;
        Uri uri = data.getData();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = act.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        if(res == null){
            String path = uri.getPath();
            if(null != path ){
                res = path;
            }
        }

        return res;
    }
}
