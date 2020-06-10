package com.example.news.view.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.databinding.FragmentViewPagerBinding.inflate
import com.google.android.material.tabs.TabLayoutMediator

class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflate(inflater, container, false)
        val viewPager = binding.viewPager

        viewPager.adapter = NewsPagerAdapter(this)

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int) = when (position) {
        FAV_PAGE_INDEX -> R.drawable.garden_tab_selector
        NEWS_PAGE_INDEX -> R.drawable.plant_list_tab_selector
        else -> throw IndexOutOfBoundsException()
    }

    private fun getTabTitle(position: Int) = when (position) {
        FAV_PAGE_INDEX -> "Favorites"
        NEWS_PAGE_INDEX -> "News"
        else -> null
    }
}