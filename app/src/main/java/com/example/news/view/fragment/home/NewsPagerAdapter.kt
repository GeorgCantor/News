package com.example.news.view.fragment.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.R
import com.example.news.util.Constants.BUSINESS_PAGE
import com.example.news.util.Constants.FAV_PAGE
import com.example.news.util.Constants.SCIENCE_PAGE
import com.example.news.view.fragment.favorites.FavoritesFragment
import com.example.news.view.fragment.news.NewsFragment

class NewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        BUSINESS_PAGE to { NewsFragment.create(fragment.context?.getString(R.string.business) ?: "") },
        SCIENCE_PAGE to { NewsFragment.create(fragment.context?.getString(R.string.science) ?: "") },
        FAV_PAGE to { FavoritesFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}