package com.show.carhome.adapter

import android.view.View
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.show.carhome.databinding.NetworkStateItemBinding

class NetWorkStateItemViewHolder(
    private val binding: NetworkStateItemBinding,
    val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: LoadState) {
        binding.apply {
            progress.isVisible = data is LoadState.Loading
            retryButton.isVisible = data is LoadState.Error
            retryButton.setOnClickListener { retryCallback }
            errorMsg.isVisible = !(data as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorMsg.text = (data as? LoadState.Error)?.error?.message
        }
    }
}

inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }