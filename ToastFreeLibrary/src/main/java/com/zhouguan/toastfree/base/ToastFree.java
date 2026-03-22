package com.zhouguan.toastfree.base;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.zhouguan.toastfree.R;


public class ToastFree {

    private static ToastFree toastFree;

    private Toast toast_short,toast_long;

    public static final int DURATION_SHORT = Toast.LENGTH_SHORT;
    public static final int DURATION_LONG = Toast.LENGTH_LONG;

    private ToastFree(){}

    public static ToastFree instance(){
        if (toastFree == null){
            synchronized (ToastFree.class){
                if (toastFree == null){
                    toastFree = new ToastFree();
                }
            }
        }
        return toastFree;
    }


    // short to display the message
    public void showTextShort(@NonNull Context context, @NonNull String message){
        if (toast_short == null){
            toast_short = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast_short.setText(message);
        }
        toast_short.show();
    }

    // long to display the message
    public void showTextLong(@NonNull Context context, @NonNull String message){
        if (toast_long == null){
            toast_long = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }else {
            toast_long.setText(message);
        }
        toast_long.show();
    }

    /**
     *
     * @param context
     * @param drawableResId
     * @param message
     * @param duration #{DURATION_SHORT}、#{DURATION_LONG}
     */
    public void showImg(Context context, @DrawableRes int drawableResId, String message, int duration){
        Toast toastImg = new Toast(context);
        // inflate view
        View view = LayoutInflater.from(context).inflate(R.layout.view_toast_img_short, null);
        toastImg.setView(view);
        toastImg.setGravity(Gravity.CENTER, 0, 0);
        toastImg.setDuration(duration);
        TextView tv_message = view.findViewById(R.id.tv_message);
        if (tv_message != null){
            if (TextUtils.isEmpty(message)){
                tv_message.setVisibility(View.GONE);
            }else {
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(message);
            }
        }

        toastImg.show();
    }



}
