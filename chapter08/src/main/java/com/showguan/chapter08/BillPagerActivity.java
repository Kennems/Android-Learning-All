package com.showguan.chapter08;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.showguan.chapter08.adapter.BillPagerAdapter;
import com.showguan.chapter08.database.BillDBHelper;
import com.showguan.chapter08.util.DateUtil;

import org.w3c.dom.Text;

import java.util.Calendar;

public class BillPagerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Calendar calendar;
    private TextView tv_month;
    private ViewPager vp_bill;
    private BillDBHelper mDBhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_subtitle = findViewById(R.id.tv_subtitle);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_month = findViewById(R.id.tv_month);

        tv_title.setText("帐单列表");
        tv_subtitle.setText("添加账单");
        tv_subtitle.setOnClickListener(this);
        calendar = Calendar.getInstance();
        tv_month.setText(DateUtil.getMonth(calendar));
        tv_month.setOnClickListener(this);

        mDBhelper = BillDBHelper.getInstance(this);
        mDBhelper.openReadLink();
        mDBhelper.openWriteLink();
        initViewPager();
    }

    // 初始化ViewPager
    private void initViewPager() {
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        vp_bill = findViewById(R.id.vp_bill);
        BillPagerAdapter adapter = new BillPagerAdapter(getSupportFragmentManager(), calendar.get(Calendar.YEAR));
        vp_bill.setAdapter(adapter);
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tv_month){
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            dialog.show();
        } else if (v.getId() == R.id.tv_subtitle) {
            Intent intent = new Intent(this, BillAddActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        tv_month.setText(DateUtil.getMonth(calendar));
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBhelper.closeLink();
    }
}