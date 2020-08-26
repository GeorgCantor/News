package com.example.news.view.fragment.news

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.model.response.Article
import com.example.news.util.Constants.ARG_ARTICLE
import com.example.news.util.Constants.ARG_QUERY
import com.example.news.util.EndlessScrollListener
import com.example.news.util.MyPreferences
import com.example.news.util.observe
import com.example.news.util.shortToast
import com.example.news.view.fragment.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class NewsFragment : Fragment(R.layout.fragment_news) {

    companion object {
        fun create(query: String): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply { putString(ARG_QUERY, query) }
            }
        }
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    private var isFirstRequest = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { parametersOf() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(ARG_QUERY) ?: ""

        with(viewModel) {
            getNews(query, 1)

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
                viewModel.getNews(query, page)
            }
        }

        news_recycler.addOnScrollListener(scrollListener)
    }

    private fun openDetail(article: Article) {
        findNavController().navigate(
            R.id.action_view_pager_fragment_to_article_fragment,
            Bundle().apply { putParcelable(ARG_ARTICLE, article) }
        )
    }

    private fun chooseThemeDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.choose_theme_text))
            val styles = arrayOf("Light", "Dark", "System default")
            val checkedItem = MyPreferences(requireContext()).darkMode

            setSingleChoiceItems(styles, checkedItem) { dialog, which ->
                when (which) {
                    0 -> {
                        setDefaultNightMode(MODE_NIGHT_NO)
                        MyPreferences(requireContext()).darkMode = 0
                        (requireActivity() as AppCompatActivity).delegate.applyDayNight()
                        dialog.dismiss()
                    }
                    1 -> {
                        setDefaultNightMode(MODE_NIGHT_YES)
                        MyPreferences(requireContext()).darkMode = 1
                        (requireActivity() as AppCompatActivity).delegate.applyDayNight()

                        dialog.dismiss()
                    }
                    2 -> {
                        setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                        MyPreferences(requireContext()).darkMode = 2
                        (requireActivity() as AppCompatActivity).delegate.applyDayNight()
                        dialog.dismiss()
                    }
                }
            }
            create()
            show()
        }
    }
}