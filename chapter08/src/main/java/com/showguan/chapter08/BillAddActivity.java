package com.showguan.chapter08;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter08.database.BillDBHelper;
import com.showguan.chapter08.entity.BillInfo;
import com.showguan.chapter08.util.DateUtil;
import com.showguan.chapter08.util.ToastUtil;

import java.util.Calendar;
import java.util.Date;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "Kennem";

    private TextView tv_date;
    private Calendar calendar;
    private TextView tv_subtitle;
    private TextView tv_title;
    private RadioButton rb_in;
    private RadioButton rb_out;
    private EditText et_remark;
    private EditText et_amount;
    private Button btn_save;
    private RadioGroup rg_type;
    private BillDBHelper mDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_add);

        tv_title = findViewById(R.id.tv_title);
        tv_subtitle = findViewById(R.id.tv_subtitle);
        rg_type = findViewById(R.id.rg_type);
        rb_in = findViewById(R.id.rb_in);
        rb_out = findViewById(R.id.rb_out);
        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_amount);
        btn_save = findViewById(R.id.btn_save);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        tv_title.setText("请记录账单");
        tv_subtitle.setText("账单列表");
        tv_subtitle.setOnClickListener(this);

        tv_date = findViewById(R.id.tv_date);
        calendar = Calendar.getInstance();
        tv_date.setText(DateUtil.getDate(calendar));
        tv_date.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        mDBhelper = BillDBHelper.getInstance(this);
        mDBhelper.openReadLink();
        mDBhelper.openWriteLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_date) {
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        } else if (v.getId() == R.id.btn_save) {
            BillInfo bill = new BillInfo();
            bill.date = tv_date.getText().toString();
            bill.type = rg_type.getCheckedRadioButtonId() == R.id.rb_in ?
                    BillInfo.BILL_TYPE_INCOME : BillInfo.BILL_TYPE_COSE;
            bill.remark = et_remark.getText().toString();
            bill.amount = Double.parseDouble(et_amount.getText().toString());
            Log.d(TAG, bill.toString());
            if (mDBhelper.save(bill) > 0) {
                ToastUtil.show(this, "添加账单成功");
            }
        } else if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.tv_subtitle) {
            Intent intent = new Intent(this, BillPagerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_date.setText(DateUtil.getDate(calendar));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}