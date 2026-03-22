package com.zhouguan.learnglide.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhouguan.learnglide.data.GlideFeature
import com.zhouguan.learnglide.R

class GlideFeatureAdapter(
    private val features: List<GlideFeature> = emptyList()
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate( R.layout.item_feature, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feature = features[position]
        holder.title.text = feature.title
        holder.description.text = feature.description
        holder.actionButton.setOnClickListener { feature.action(holder.imageView) }
    }

    override fun getItemCount() = features.size
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.imageView)
    val title: TextView = view.findViewById(R.id.title)
    val description: TextView = view.findViewById(R.id.description)
    val actionButton: Button = view.findViewById(R.id.actionButton)
}