package com.example.news.view.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.view.fragment.favorites.FavoritesFragment
import com.example.news.view.fragment.news.NewsFragment

const val FAV_PAGE_INDEX = 0
const val NEWS_PAGE_INDEX = 1

class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        FAV_PAGE_INDEX to { FavoritesFragment() },
        NEWS_PAGE_INDEX to { NewsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}