package com.guan.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guan.travel.databinding.ViewholderPopularBinding;
import com.guan.travel.domain.ItemModel;
import com.guan.travel.ui.activity.DetailActivity;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {
    ArrayList<ItemModel> items;
    Context context;
    ViewholderPopularBinding binding;

    public PopularAdapter(ArrayList<ItemModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PopularViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        binding.titleText.setText(items.get(position).getTitle());
        binding.priceText.setText("$" + items.get(position).getPrice() + "/Night");
        binding.addressText.setText(items.get(position).getAddress());
        binding.scoreText.setText("" + items.get(position).getScore());

        Glide.with(context)
                .load(items.get(position).getPic().get(0))
                .into(binding.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("object", items.get(position));
                        context.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        public PopularViewHolder(ViewholderPopularBinding binding) {
            super(binding.getRoot());
        }
    }

}
