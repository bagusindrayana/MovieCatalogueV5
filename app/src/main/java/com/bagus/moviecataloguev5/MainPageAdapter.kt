package com.bagus.moviecataloguev5

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bagus.moviecataloguev5.ui.favorite.FavoriteFragment
import com.bagus.moviecataloguev5.ui.movies.MovieFragment
import com.bagus.moviecataloguev5.ui.tv.TvFragment

class MainPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    private val pages = listOf(
        MovieFragment(),
        TvFragment(),
        FavoriteFragment()

    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Movie"
            1 -> "Tv"
            else -> "Favorite"
        }
    }
}