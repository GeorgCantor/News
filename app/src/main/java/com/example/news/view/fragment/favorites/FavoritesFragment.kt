package com.example.news.view.fragment.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.news.R

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}