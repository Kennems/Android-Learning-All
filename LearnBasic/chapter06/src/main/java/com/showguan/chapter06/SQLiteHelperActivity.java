package com.showguan.chapter06;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter06.database.UserDBHelper;
import com.showguan.chapter06.enity.User;
import com.showguan.chapter06.util.ToastUtil;

import java.util.List;

public class SQLiteHelperActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_height;
    private EditText et_age;
    private EditText et_name;
    private EditText et_weight;
    private UserDBHelper mHelper;
    private CheckBox ck_isMarried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_helper);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_modify).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_isMarried = findViewById(R.id.ck_isMarried);

    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString().trim();
        String ageStr = et_age.getText().toString().trim();
        String heightStr = et_height.getText().toString().trim();
        String weightStr = et_weight.getText().toString().trim();
        boolean married = ck_isMarried.isChecked();

        // 校验name是否为空
        if (name.isEmpty()) {
            ToastUtil.show(this, "姓名不能为空");
            return;
        }

        Integer age = null;
        Float height = null;
        Float weight = null;

        // 校验age是否为空或为有效整数
        if (!ageStr.isEmpty()) {
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                ToastUtil.show(this, "年龄输入不正确");
                return;
            }
        }

        // 校验height是否为空或为有效浮点数
        if (!heightStr.isEmpty()) {
            try {
                height = Float.parseFloat(heightStr);
            } catch (NumberFormatException e) {
                ToastUtil.show(this, "身高输入不正确");
                return;
            }
        }

        // 校验weight是否为空或为有效浮点数
        if (!weightStr.isEmpty()) {
            try {
                weight = Float.parseFloat(weightStr);
            } catch (NumberFormatException e) {
                ToastUtil.show(this, "体重输入不正确");
                return;
            }
        }

        User user = null;
        if (v.getId() == R.id.btn_add) {
            user = new User(name, age, height, weight, married);
            if (mHelper.insert(user) > 0) {
                ToastUtil.show(this, "添加成功");
            }
        } else if (v.getId() == R.id.btn_delete) {
            if (mHelper.deleteByName(name) > 0) {
                ToastUtil.show(this, "删除成功");
            }
        } else if (v.getId() == R.id.btn_modify) {
            user = new User(name, age, height, weight, married);
            if (mHelper.update(user) > 0) {
                ToastUtil.show(this, "修改成功");
            }
        } else if (v.getId() == R.id.btn_select) {
            List<User> list = mHelper.queryByName(name);
            if(list.size() != 0){
                ToastUtil.show(this, "查询成功");
            }
            for (User u : list) {
                Log.d("Kennem", u.toString());
            }
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        mHelper = UserDBHelper.getInstance(this);
        mHelper.openWriteLink();
        mHelper.openReadLink();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mHelper.closeLink();
    }


}