package com.android.trashub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.trashub.R
import com.android.trashub.news.NewsItem
import com.bumptech.glide.Glide

class NewsAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<NewsItem, NewsAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.bind(newsItem)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleNews: TextView = itemView.findViewById(R.id.title)
        private val imageNews: ImageView = itemView.findViewById(R.id.image)
        private val detailNews: TextView = itemView.findViewById(R.id.detail)

        fun bind(itemNews: NewsItem) {
            titleNews.text = itemNews.title
            detailNews.apply {
                text = "Detail"
                setTag(R.id.detail, itemNews.url)
                visibility = if (itemNews.url != null) View.VISIBLE else View.GONE
                setOnClickListener {
                    itemNews.url?.let { url -> onItemClick(url) }
                }
            }
            if (!itemNews.imageUrl.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(itemNews.imageUrl)
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(imageNews)
            } else {
                imageNews.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_place_holder))
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, itemNews: NewsItem): Boolean {
            return oldItem.title == itemNews.title
        }

        override fun areContentsTheSame(oldItem: NewsItem, itemNews: NewsItem): Boolean {
            return oldItem == itemNews
        }
    }
}