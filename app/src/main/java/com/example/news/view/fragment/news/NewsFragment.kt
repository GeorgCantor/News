package com.example.news.view.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.Constants.ARTICLE_ARG
import com.example.news.util.EndlessScrollListener
import com.example.news.util.observe
import com.example.news.util.shortToast
import com.example.news.view.fragment.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private var isFirstRequest = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { parametersOf() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_news, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            getNews("Business", 1)

            isProgressVisible.observe(viewLifecycleOwner) { visible ->
                progress_bar.visibility = if (visible) VISIBLE else GONE
            }

            error.observe(viewLifecycleOwner) { context?.shortToast(it) }

            news.observe(viewLifecycleOwner) { news ->
                when (isFirstRequest) {
                    true -> {
                        adapter = NewsAdapter(news, news, { openDetail(it) }, { openDetail(it) })
                        news_recycler.adapter = adapter
                        isFirstRequest = false
                    }
                    false -> {
                        nestedNews.observe(viewLifecycleOwner) {
                            adapter.updateList(news, it)
                        }
                    }
                }
            }
        }

        val scrollListener = object : EndlessScrollListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel.getNews("Business", page)
            }
        }

        news_recycler.addOnScrollListener(scrollListener)
    }

    private fun openDetail(article: Article) {
        findNavController().navigate(
            R.id.action_view_pager_fragment_to_article_fragment,
            Bundle().apply { putParcelable(ARTICLE_ARG, article) }
        )
    }
}