package com.android.trashub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.data.Trashub
import com.bumptech.glide.Glide

class TrashubAdapter(private val trashubList: ArrayList<Trashub>) :
    RecyclerView.Adapter<TrashubAdapter.TrashubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashubViewHolder {
        val listItem = LayoutInflater.from(parent.context).inflate(R.layout.item_jenis, parent, false)
        return TrashubViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: TrashubViewHolder, position: Int) {
        val currentTrashub = trashubList[position]
        holder.name.text = currentTrashub.jenis_sampah

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(currentTrashub.imageUrl)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return trashubList.size
    }

    class TrashubViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
        val name: TextView = listView.findViewById(R.id.name)
        val image: ImageView = listView.findViewById(R.id.image)
    }
}
