package com.showguan.chapter06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter06.util.FileUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_pic;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_write);
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        iv_pic = findViewById(R.id.iv_pic);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_save){
            String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
            String fileName = System.currentTimeMillis() + ".jpg";
            path = directory + File.separatorChar + fileName;
            Log.d("Kennem", path);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat2);
            FileUtil.saveImage(path, bitmap);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_read) {
            // 使用FileUtil工具类从指定路径读取图片文件并设置到ImageView中显示
            // 注意：这里假设FileUtil类中有一个名为readImage的方法，可以根据路径返回Bitmap对象。
            // 由于具体的FileUtil类实现不明确，这段代码是假设性的示例。

            // 方法一：使用FileUtil工具类读取图片文件
            // Bitmap bitmap = FileUtil.readImage(path); // 假设FileUtil类有这个方法
            // iv_pic.setImageBitmap(bitmap); // 将读取的Bitmap对象设置到ImageView中显示

            // 方法二：直接使用BitmapFactory解码指定路径的图片文件为Bitmap对象
            // Bitmap bitmap = BitmapFactory.decodeFile(path); // 使用BitmapFactory解码文件
            // iv_pic.setImageBitmap(bitmap); // 将解码得到的Bitmap对象设置到ImageView中显示

            // 方法三：使用Uri对象加载指定路径的图片文件到ImageView中显示
            iv_pic.setImageURI(Uri.parse(path)); // 将指定路径的图片文件通过Uri加载并显示到ImageView

        }
    }
}