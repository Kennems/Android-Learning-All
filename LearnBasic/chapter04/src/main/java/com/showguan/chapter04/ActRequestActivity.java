package com.showguan.chapter04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter04.utils.DateUtil;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {
    private final String mRequest = "今天是2024/06/18， 坚持就是胜利，效率就是王者";
    private ActivityResultLauncher<Intent> register;
    private TextView tv_response;
    private TextView tv_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_request);
        tv_request = findViewById(R.id.tv_request);
        tv_response = findViewById(R.id.tv_response);

        tv_request.setText("待发送的文本" + mRequest);
        findViewById(R.id.btn_request).setOnClickListener(this);

        register = registerForActivityResult(new StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null) {
                    Intent intent = result.getData();
                    if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                        Bundle bundle = intent.getExtras();
                        String request_time = bundle.getString("response_time");
                        String request_content = bundle.getString("response_content");
                        String desc = String.format("收到应答消息，应答时间为%s, 应答内容为%s", request_time, request_content);
                        tv_response.setText(desc);
                    }
                }
            }
        });
//        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if(result != null){
//                Intent intent = result.getData();
//                if(intent != null && result.getResultCode() == Activity.RESULT_OK){
//                    Bundle bundle = intent.getExtras();
//                    String request_time = bundle.getString("response_time");
//                    String request_content = bundle.getString("response_content");
//                    String desc = String.format("收到应答消息，应答时间为%s, 应答内容为%s", request_time, request_content);
//                    tv_response.setText(desc);
//                }
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActResponseActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("request_time", DateUtil.getNowTime());
        bundle.putString("request_content", mRequest);
        intent.putExtras(bundle);

        register.launch(intent);
    }
}