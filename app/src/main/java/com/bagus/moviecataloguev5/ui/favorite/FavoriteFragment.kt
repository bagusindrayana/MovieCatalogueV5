package com.bagus.moviecataloguev5.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bagus.moviecataloguev5.R
import com.bagus.moviecataloguev5.ui.favorite.movie.FavoriteMovieFragment
import com.bagus.moviecataloguev5.ui.favorite.tv.FavoriteTvFragment
import com.google.android.material.tabs.TabLayout


class FavoriteFragment : Fragment() {

    private var rv_favorites: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.list_tab_container)
        getActivity()?.setTitle(R.string.title_favorite);
        initViews(view)
        return view
    }

    private fun initViews(view : View) {
        val viewPager = view.findViewById<ViewPager>(R.id.list_tab_container)
        setupViewPager(viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val mainFragmentPagerAdapter = MainFragmentPagerAdapter(getChildFragmentManager())
        mainFragmentPagerAdapter.addFragment(FavoriteMovieFragment(), this.getString(R.string.title_movie))
        mainFragmentPagerAdapter.addFragment(FavoriteTvFragment(), this.getString(R.string.title_tv))

        viewPager.adapter = mainFragmentPagerAdapter

    }



}