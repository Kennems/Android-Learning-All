package com.showguan.chapter08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.showguan.chapter08.adapter.PlaneListWithButtonAdapter;
import com.showguan.chapter08.entity.Planet;
import com.showguan.chapter08.util.ToastUtil;

import java.util.List;

public class ListFocusActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_focus);
        ListView lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        PlaneListWithButtonAdapter adapter = new PlaneListWithButtonAdapter(this, planetList);
        lv_planet.setAdapter(adapter);
        lv_planet.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "条目被点击了 ： " + planetList.get(position).name);
    }
}