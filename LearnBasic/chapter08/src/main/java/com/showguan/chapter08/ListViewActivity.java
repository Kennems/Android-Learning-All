package com.showguan.chapter08;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.adapter.PlanetBaseAdapter;
import com.showguan.chapter08.entity.Planet;
import com.showguan.chapter08.util.ToastUtil;
import com.showguan.chapter08.util.Utils;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Planet> planetList;
    private CheckBox ck_divider;
    private CheckBox ck_bgc;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        lv_planet = findViewById(R.id.lv_planet);
        ck_divider = findViewById(R.id.ck_divider);
        ck_divider.setOnCheckedChangeListener(this);
        ck_bgc = findViewById(R.id.ck_bgc);
        ck_bgc.setOnCheckedChangeListener(this);

        planetList = Planet.getDefaultList();
        PlanetBaseAdapter adapter = new PlanetBaseAdapter(this, planetList);

        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "您选择的是" + planetList.get(position).name);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.ck_divider) {
            if (ck_divider.isChecked()) {
                Drawable black = getResources().getDrawable(R.color.black, getTheme());
                lv_planet.setDivider(black);
                lv_planet.setDividerHeight(Utils.dip2px(this, 1));
            } else {
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(Utils.dip2px(this, 0));
            }
        } else if (buttonView.getId() == R.id.ck_bgc) {
            if (ck_bgc.isChecked()) {
                lv_planet.setSelector(R.drawable.list_selector);
            } else {
                Drawable transparent = getResources().getDrawable(R.color.transparent, getTheme());
                lv_planet.setSelector(transparent);
            }
        }
    }
}