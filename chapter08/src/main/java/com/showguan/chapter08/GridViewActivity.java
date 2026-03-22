package com.showguan.chapter08;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.showguan.chapter08.adapter.PlanetGridAdapter;
import com.showguan.chapter08.entity.Planet;
import com.showguan.chapter08.util.ToastUtil;

import java.util.List;

public class GridViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<Planet> planetList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gv_planet = findViewById(R.id.gv_planet);
        planetList = Planet.getDefaultList();
        PlanetGridAdapter adapter = new PlanetGridAdapter(this, planetList);
        gv_planet.setAdapter(adapter);
        gv_planet.setOnItemClickListener(this);
        gv_planet.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "你选择了" + planetList.get(position).name);
    }
}