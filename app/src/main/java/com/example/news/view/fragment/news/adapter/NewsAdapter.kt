package com.example.news.view.fragment.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.loadImage
import kotlinx.android.synthetic.main.item_nested_recycler.view.*
import kotlinx.android.synthetic.main.item_news.view.*

class NewsAdapter(
    news: List<Article>,
    nestedNews: List<Article>,
    private val clickListener: (Article) -> Unit,
    private val nestedClickListener: (Article) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_LIST = 1
    }

    val news = mutableListOf<Article>()
    private val nestedNews = mutableListOf<Article>()

    init {
        this.news.addAll(news)
        this.nestedNews.addAll(nestedNews)
    }

    fun updateList(news: List<Article>, nestedNews: List<Article>) {
        this.news.addAll(news)
        this.nestedNews.clear()
        this.nestedNews.addAll(nestedNews)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                NewsViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
                )
            }
            TYPE_LIST -> {
                NestedRecyclerViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_nested_recycler, parent, false)
                )
            }
            else -> NewsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position.toString().toCharArray().last() == '0') {
            true -> TYPE_LIST
            else -> TYPE_ITEM
        }
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val article = news[position]

        with(holder) {
            when (this) {
                is NewsViewHolder -> {
                    itemView.context.loadImage(article.urlToImage, image)
                    title.text = article.title
                    description.text = article.description
                    itemView.setOnClickListener { clickListener(article) }
                }
                is NestedRecyclerViewHolder -> {
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = NestedAdapter(nestedNews) {
                        nestedClickListener(it)
                    }
                }
            }
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.news_image
        val title: TextView = view.title
        val description: TextView = view.description
    }

    class NestedRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.nested_recycler
    }
}