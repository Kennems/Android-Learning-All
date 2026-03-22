package com.showguan.chapter06;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.showguan.chapter06.util.ViewUtil;

import java.util.Random;

public class FindPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private String mPhone;
    private String mVerifyCode;
    private EditText et_password;
    private EditText re_et_password;
    private EditText et_find_password_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        et_password = findViewById(R.id.et_password);
        re_et_password = findViewById(R.id.re_et_password);
        et_find_password_code = findViewById(R.id.et_find_password_code);
        et_password.addTextChangedListener(new HideTextWatcher(et_password, 6));
        re_et_password.addTextChangedListener(new HideTextWatcher(re_et_password, 6));
        et_find_password_code.addTextChangedListener(new HideTextWatcher(et_find_password_code, 6));

        mPhone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_verifycode){
            mVerifyCode = String.format("%06d", new Random().nextInt(999999));
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("手机验证码");
            builder.setMessage("手机号" + mPhone + ", 本次验证码是" + mVerifyCode);
            builder.setPositiveButton("好的",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(v.getId() == R.id.btn_confirm){
            String passwordFirst = et_password.getText().toString();
            String passwordSecond = re_et_password.getText().toString();
            if(!passwordFirst.equals(passwordSecond)){
                String desc = "两次密码不一致，请重新填写";
                Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
                return;
            }

            if(mVerifyCode==null || !mVerifyCode.equals(et_find_password_code.getText().toString())){
                String desc = "验证码有误，请重新填写";
                Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
                return;
            }

            String desc = "密码修改成功请重新登录";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("登录成功");
            builder.setMessage(desc);
            builder.setPositiveButton("返回登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.putExtra("new_password", passwordFirst);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
    class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int maxLength;
        public HideTextWatcher(EditText editText, int i) {
            this.mView = editText;
            this.maxLength = i;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length() == maxLength){
                ViewUtil.hideOneInputMethod(FindPasswordActivity.this, mView);
            }
        }
    }
}