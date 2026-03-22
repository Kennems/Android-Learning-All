package com.show.carhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.show.carhome.databinding.NetworkStateItemBinding

class FooterAdapter(
    val adapter: CarBrandAdapter, val context: Context
) : LoadStateAdapter<NetWorkStateItemViewHolder>() {
    // 水品居中
    override fun onBindViewHolder(holder: NetWorkStateItemViewHolder, loadState: LoadState) {
        val params = holder.itemView.layoutParams
        if (params is StaggeredGridLayoutManager.LayoutParams) {
            params.isFullSpan = true
        }

        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetWorkStateItemViewHolder {
        val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NetWorkStateItemViewHolder(binding) {
            adapter.retry()
        }
    }
}