package com.example.news.view.fragment.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.util.EndlessScrollListener
import com.example.news.util.observe
import com.example.news.util.shortToast
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    var isFirstRequest = true

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

            error.observe(viewLifecycleOwner) { context?.shortToast(it) }

            news.observe(viewLifecycleOwner) {
                when (isFirstRequest) {
                    true -> {
                        adapter = NewsAdapter(it) { context?.shortToast(it.title) }
                        news_recycler.adapter = adapter
                        isFirstRequest = false
                    }
                    false -> adapter.updateList(it)
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
}