package com.example.news.view.fragment.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.loadImage
import kotlinx.android.synthetic.main.item_nested.view.*

class NestedAdapter(
    news: List<Article>,
    private val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<NestedAdapter.NestedViewHolder>() {

    val news = mutableListOf<Article>()

    init {
        this.news.addAll(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NestedViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_nested, parent, false)
    )

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NestedViewHolder, position: Int) {
        val article = news[position]

        with(holder) {
            itemView.context.loadImage(article.urlToImage, image)
            itemView.setOnClickListener { clickListener(article) }
        }
    }

    class NestedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.nested_image
    }
}