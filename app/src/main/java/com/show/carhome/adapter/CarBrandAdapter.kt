package com.show.carhome.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.show.carhome.databinding.CarbrandItemBinding
import com.show.carhome.model.CarBrandItemModel

class CarBrandAdapter(
    private val context: Context
) : PagingDataAdapter<CarBrandItemModel, BindingViewHolder>(object :
    DiffUtil.ItemCallback<CarBrandItemModel>() {
    override fun areItemsTheSame(oldItem: CarBrandItemModel, newItem: CarBrandItemModel): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: CarBrandItemModel,
        newItem: CarBrandItemModel
    ): Boolean {
        return oldItem == newItem
    }

}) {
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).let { item ->
            val binding = holder.binding as CarbrandItemBinding
            binding.carBrand = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = CarbrandItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }
}