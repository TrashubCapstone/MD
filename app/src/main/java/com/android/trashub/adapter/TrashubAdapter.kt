package com.android.trashub.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.data.Trashub
import com.android.trashub.ui.dashboard.ResultFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrashubAdapter(private val trashubList: ArrayList<Trashub>, private val onItemClicked: (Trashub) -> Unit) : RecyclerView.Adapter<TrashubAdapter.TrashubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashubViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_jenis, parent, false)
        return TrashubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrashubViewHolder, position: Int) {
        val currentItem = trashubList[position]

        holder.namaSampah.text = currentItem.nama_sampah
        holder.itemView.setOnClickListener { onItemClicked(currentItem) }

        Glide.with(holder.itemView.getContext())
            .load(currentItem.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.imageView)
    }

    override fun getItemCount() = trashubList.size

    class TrashubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val namaSampah: TextView = itemView.findViewById(R.id.name)
    }
}