package com.example.news.view.fragment.article

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.Constants.ARG_ARTICLE
import com.example.news.util.loadImage
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: ArticleViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_article_fragment_to_view_pager_fragment)
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)

        (arguments?.getParcelable(ARG_ARTICLE) as? Article).apply {
            context?.loadImage(this?.urlToImage, image)
            title.text = this?.title
            description.text = this?.description
        }
    }
}