package com.showguan.chapter07_client;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter07_client.util.ToastUtil;

public class SentMmsActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "Kennem";

    private ActivityResultLauncher<Intent> mResultLauncher;
    private EditText et_phone_number;
    private EditText et_title;
    private EditText et_desc;
    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_mms);

        ImageView iv_add_pic = findViewById(R.id.iv_add_pic);
        findViewById(R.id.btn_send).setOnClickListener(this);
        iv_add_pic.setOnClickListener(this);

        et_phone_number = findViewById(R.id.et_phone_number);
        et_title = findViewById(R.id.et_title);
        et_desc = findViewById(R.id.et_desc);

        mResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK){
                Intent intent = result.getData();
                picUri = intent.getData();
                if(picUri != null){
                    iv_add_pic.setImageURI(picUri);
                    Log.d(TAG, picUri.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.iv_add_pic){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            mResultLauncher.launch(intent);
        } else if (v.getId() == R.id.btn_send) {
            sendMms(et_phone_number.getText().toString(),
                    et_title.getText().toString(),
                    et_desc.getText().toString());
        }
    }

    private void sendMms(String phone, String title, String desc) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("address", phone);
        intent.putExtra("subject", title);
        intent.putExtra("sms_body", desc);
        intent.putExtra(Intent.EXTRA_STREAM, picUri);
        intent.setType("image/*");
        startActivity(intent);
        ToastUtil.show(this, "从在弹窗中选择信息或短信应用");
    }
}