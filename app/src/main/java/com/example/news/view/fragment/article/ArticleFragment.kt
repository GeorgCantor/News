package com.example.news.view.fragment.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.Constants.ARTICLE_ARG
import com.example.news.util.loadImage
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_article, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (arguments?.getParcelable(ARTICLE_ARG) as? Article).apply {
            context?.loadImage(this?.urlToImage, image)
            title.text = this?.title
            description.text = this?.description
        }
    }
}