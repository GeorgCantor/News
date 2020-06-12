package com.example.news.view.fragment.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.loadImage
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    news: List<Article>,
    private val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    val news = mutableListOf<Article>()

    init {
        this.news.addAll(news)
    }

    fun updateList(news: List<Article>) {
        this.news.addAll(news)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
    )

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = news[position]

        with(holder) {
            itemView.context.loadImage(article.urlToImage ?: "", image)
            title.text = article.title
            description.text = article.description
            itemView.setOnClickListener { clickListener(article) }
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.news_image
        val title: TextView = view.title
        val description: TextView = view.description
    }
}