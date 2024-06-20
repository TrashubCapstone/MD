package com.android.trashub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SliderAdapter(private val contentList: List<SliderContent>) : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    data class SliderContent(val imageResId: Int, val title: String, val text: String, val quotes: String)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        val content = contentList[position]
        holder.imageView.setImageResource(content.imageResId)
        holder.titleView.text = content.title
        holder.textView.text = content.text
        holder.quotesView.text = content.quotes
    }

    override fun getItemCount(): Int = contentList.size

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.slideImage)
        val titleView: TextView = itemView.findViewById(R.id.slideTitle)
        val textView: TextView = itemView.findViewById(R.id.slideText)
        val quotesView: TextView = itemView.findViewById(R.id.slideQuotes)
    }
}
