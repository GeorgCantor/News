package com.example.news.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.news.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_pager.*

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_view_pager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_pager.adapter = NewsPagerAdapter(this)

        TabLayoutMediator(tabs, view_pager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun getTabIcon(position: Int) = when (position) {
        FAV_PAGE_INDEX -> R.drawable.fav_tab_selector
        NEWS_PAGE_INDEX -> R.drawable.news_tab_selector
        else -> throw IndexOutOfBoundsException()
    }

    private fun getTabTitle(position: Int) = when (position) {
        FAV_PAGE_INDEX -> "Favorites"
        NEWS_PAGE_INDEX -> "News"
        else -> null
    }
}