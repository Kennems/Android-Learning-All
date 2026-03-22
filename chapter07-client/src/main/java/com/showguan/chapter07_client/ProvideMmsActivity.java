package com.showguan.chapter07_client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.showguan.chapter07_client.entity.ImageInfo;
import com.showguan.chapter07_client.util.FileUtil;
import com.showguan.chapter07_client.util.PermissionUtil;
import com.showguan.chapter07_client.util.ToastUtil;
import com.showguan.chapter07_client.util.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kotlin.UByte;

public class ProvideMmsActivity extends AppCompatActivity{
    private static String TAG = "Kennem";

    private List<ImageInfo> mImageList = new ArrayList<>();
    private GridLayout gl_appendix;

    private static final String[] PERMISSION_STORAGE = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private static final int PERMISSION_STORAGE_CODE = 1;
    private EditText et_phone_number;
    private EditText et_message;
    private EditText et_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_mms);
        gl_appendix = findViewById(R.id.gl_appendix);

        et_title = findViewById(R.id.et_title);
        et_message = findViewById(R.id.et_message);
        et_phone_number = findViewById(R.id.et_phone_number);


        MediaScannerConnection.scanFile(
                this,
                new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
                null,
                null
        );

        if (PermissionUtil.checkPermission(this, PERMISSION_STORAGE, PERMISSION_STORAGE_CODE)) {
            loadImageList();
            showImageGrid();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_STORAGE_CODE) {
            if (PermissionUtil.checkGranted(grantResults)) {
                loadImageList();
                showImageGrid();
            }
        }
    }

    private void showImageGrid() {
        gl_appendix.removeAllViews();
        for (ImageInfo image : mImageList) {
            ImageView imageView = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            int px = Utils.dip2px(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px, px);
            imageView.setLayoutParams(params);

            int padding = Utils.dip2px(this, 5);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setOnClickListener(v->{
                sendMms(et_phone_number.getText().toString(),
                        et_title.getText().toString(),
                        et_message.getText().toString(),
                        image.path);
            });
            gl_appendix.addView(imageView);
        }
    }

    private void sendMms(String phone, String title, String desc, String path) {
        Uri uri = Uri.parse(path);
        // 兼容Android 7.0 把访问文件的Uri方式改为FileProvider
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d(TAG, String.format("FileProvider Uri : %s", uri.toString()));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("address", phone);
        intent.putExtra("subject", title);
        intent.putExtra("sms_body", desc);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(intent);
        ToastUtil.show(this, "从在弹窗中选择信息或短信应用");
    }

    @SuppressLint("Range")
    private void loadImageList() {
        String[] columns = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 307200",
                null,
                "_size DESC");

        int count = 0;
        if (cursor != null) {
            while (cursor.moveToNext() && count < 6) {
                ImageInfo image = new ImageInfo();
                image.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                if(FileUtil.checkFileUri(this, image.path)){
                    count += 1;
                    mImageList.add(image);
                }
                Log.d(TAG,image.toString() );
            }
        }
    }


}