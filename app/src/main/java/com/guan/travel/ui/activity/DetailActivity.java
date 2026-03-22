package com.guan.travel.ui.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.guan.travel.databinding.ActivityDetailBinding;
import com.guan.travel.domain.ItemModel;
import com.guan.travel.ui.adapter.PicListAdapter;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ItemModel object;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        object = (ItemModel) getIntent().getSerializableExtra("object");

        initList();
        setVariable();
    }

    private void setVariable() {
        binding.titleText.setText(object.getTitle());
        binding.priceText.setText("$" + object.getPrice());
        binding.backBtn.setOnClickListener(v -> finish());
        binding.bedText.setText(object.getBed() + " bed");
        binding.descriptionText.setText(object.getDescription());
        binding.guideText.setText(object.isGuide() ? "With guide" : "No guide");
        binding.ratingBar.setRating((float) object.getScore());
        binding.ratingText.setText(object.getScore() + " Ratting");
    }

    private void initList() {
        ArrayList<String> picList = new ArrayList<>(object.getPic());

        Glide.with(this)
                .load(picList.get(0))
                .into(binding.pic);

        binding.picList.setAdapter(new PicListAdapter(picList, binding.pic));
        binding.picList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}