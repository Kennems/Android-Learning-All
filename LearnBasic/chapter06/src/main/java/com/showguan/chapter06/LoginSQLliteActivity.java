package com.showguan.chapter06;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter06.database.LoginDBHelper;
import com.showguan.chapter06.enity.LoginInfo;
import com.showguan.chapter06.util.ViewUtil;

import java.util.Random;

public class LoginSQLliteActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, View.OnFocusChangeListener {

    private TextView login_passFun;
    private EditText login_input;
    private TextView login_input_option;
    private View remember_password_layout;
    private RadioButton rb_phonecode;
    private RadioButton rb_password;
    private CheckBox ck_save_password;
    private EditText et_phone;
    private Button login;
    private String mPassword = "123456";
    private String mVerifyCode;

    private ActivityResultLauncher<Intent> register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            Intent intent = o.getData();
            if (intent != null && o.getResultCode() == Activity.RESULT_OK) {
                mPassword = intent.getStringExtra("new_password");
            }
        }
    });
    private SharedPreferences preferences;
    private LoginDBHelper mHelper;


    @Override
    protected void onStart() {
        super.onStart();
        mHelper = LoginDBHelper.getInstance(this);
        mHelper.openReadLink();
        mHelper.openWriteLink();
        reload();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        RadioGroup rg_login = findViewById(R.id.rg_login);
        login_passFun = findViewById(R.id.login_passFun);
        login_input = findViewById(R.id.login_input);
        login_input_option = findViewById(R.id.login_input_option);
        remember_password_layout = findViewById(R.id.remember_password_layout);
        rb_password = findViewById(R.id.rb_password);
        rb_phonecode = findViewById(R.id.rb_phonecode);
        et_phone = findViewById(R.id.et_phone);
        ck_save_password = findViewById(R.id.ck_save_password);
        rg_login.setOnCheckedChangeListener(this);
        EditText et_phone = findViewById(R.id.et_phone);
        EditText login_input = findViewById(R.id.login_input);
        login = findViewById(R.id.login);


        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        login_input.addTextChangedListener(new HideTextWatcher(login_input, 6));
        login_input.setOnFocusChangeListener(this);

        login_input_option.setOnClickListener(this);
        login.setOnClickListener(this);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);

    }

    private void reload() {
        LoginInfo info = mHelper.queryTop();
        if (info != null && info.isRemember()) {
            et_phone.setText(info.getPhone());
            login_input.setText(info.getPassword());
            ck_save_password.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_password) {
            login_passFun.setText(R.string.login_password);
            login_input.setHint(R.string.password_hint);
            login_input_option.setText(R.string.forget_password);

            remember_password_layout.setVisibility(View.VISIBLE);


        } else if (checkedId == R.id.rb_phonecode) {
            login_passFun.setText(R.string.login_phonecode);
            login_input.setHint(R.string.code_hint);
            login_input_option.setText(R.string.get_code);

            remember_password_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        String phoneNum = et_phone.getText().toString();
        if (phoneNum.length() < 11) {
            Toast.makeText(this, "您输入的手机号不足11位，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v.getId() == R.id.login_input_option) {
            if (rb_password.isChecked()) {
                Intent intent = new Intent(this, FindPasswordActivity.class);
                intent.putExtra("phone", phoneNum);
                register.launch(intent);
            } else if (rb_phonecode.isChecked()) {
                mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("手机验证码");
                builder.setMessage("手机号" + phoneNum + ", 本次验证码是" + mVerifyCode);
                builder.setPositiveButton("好的", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else if (v.getId() == R.id.login) {
            if (rb_password.isChecked()) {
                if (!mPassword.equals(login_input.getText().toString())) {
                    Toast.makeText(this, "用户名或密码错误，请检查输入：", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();

            } else if (rb_phonecode.isChecked()) {
                if (mVerifyCode == null || !mVerifyCode.equals(login_input.getText().toString())) {
                    Toast.makeText(this, "验证码错误，请检查输入：", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccess();
            }
        }
    }

    private void loginSuccess() {
        String desc = String.format("你的手机号是%s, 恭喜你登录成功，点击确认返回上一个页面", et_phone.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确认返回", (dialog, which) -> {
            finish();
        });
        builder.setNegativeButton("我再看看吧", null);
        AlertDialog dialog = builder.create();
        dialog.show();

        if (rb_password.isChecked() && ck_save_password.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("account", et_phone.getText().toString());
            editor.putString("password", login_input.getText().toString());
            editor.putBoolean("is_save_password", true);
            editor.commit();
        }
        if(rb_password.isChecked()){
            LoginInfo info = new LoginInfo(et_phone.getText().toString(), login_input.getText().toString(), ck_save_password.isChecked());
            mHelper.insert(info);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.login_input && hasFocus) {
            LoginInfo info = mHelper.queryByPhone(et_phone.getText().toString());
//            if(info!=null){
//                Log.d("Kennem", info.toString());
//            }
            if (info != null) {
                login_input.setText(info.getPassword());
                ck_save_password.setChecked(true);
            } else {
                login_input.setText("");
                ck_save_password.setChecked(false);
            }
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
            if (s.toString().length() == maxLength) {
                ViewUtil.hideOneInputMethod(LoginSQLliteActivity.this, mView);
            }
        }
    }
}