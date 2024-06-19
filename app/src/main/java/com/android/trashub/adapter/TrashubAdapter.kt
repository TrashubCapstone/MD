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

class TrashubAdapter(private val trashubList: ArrayList<Trashub>) : RecyclerView.Adapter<TrashubAdapter.TrashubViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashubViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_jenis, parent, false)
        return TrashubViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrashubViewHolder, position: Int) {
        val currentItem = trashubList[position]

        holder.namaSampah.text = currentItem.nama_sampah
//        holder.jenisSampah.text = currentItem.jenis_sampah
//        holder.kategoriSampah.text = currentItem.kategori_sampah
//        holder.keteranganSampah.text = currentItem.keterangan_sampah

        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount() = trashubList.size

    class TrashubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image)
        val namaSampah: TextView = itemView.findViewById(R.id.name)
//        val jenisSampah: TextView = itemView.findViewById(R.id.jenis_sampah)
//        val kategoriSampah: TextView = itemView.findViewById(R.id.kategori_sampah)
//        val keteranganSampah: TextView = itemView.findViewById(R.id.keterangan_sampah)
    }
}
