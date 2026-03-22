package com.showguan.chapter07_client.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.showguan.chapter07_client.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtil {


    // 保存文本到指定路径
    public static void saveText(String path, String txt) {
        // 注意，这里需要确保os被正确关闭之后才能保存文件
        try (BufferedWriter os = new BufferedWriter(new FileWriter(path))) {
            os.write(txt);
            Log.d("Kennem", "保存成功");
            Log.d("Kennem", "保存的内容" + txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从指定路径的文本文件中读取为字符串
    public static String openText(String path){
        BufferedReader is = null;
        StringBuilder sb = new StringBuilder();
        try{
            is = new BufferedReader(new FileReader(path));
            String line = null;
            while((line = is.readLine()) != null){
                sb.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is != null){
                try{
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static void saveImage(String path, Bitmap bitmap) {
        try(FileOutputStream fos = new FileOutputStream(path)){
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap readImage(String path) {
        Bitmap bitmap = null;
        try(FileInputStream fis = new FileInputStream(path))
        {
            bitmap = BitmapFactory.decodeStream(fis);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }


    public static boolean checkFileUri(Context context, String path){
        File file = new File(path);
        Log.d("Kennem", "old Path" + path);
        if(!file.exists() || !file.isFile()){
            return false;
        }
        try{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Uri uri = FileProvider.getUriForFile(context, context.getString(R.string.file_provider), file);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
