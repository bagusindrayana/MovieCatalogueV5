package com.bagus.mymoviefavortie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bagus.mymoviefavorite.ui.favorite.movie.FavoriteMovieFragment
import com.bagus.mymoviefavorite.ui.favorite.tv.FavoriteTvFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager>(R.id.list_tab_container)
        setTitle("Favoritku");
        initViews()
    }



    private fun initViews() {
        val viewPager = findViewById<ViewPager>(R.id.list_tab_container)
        setupViewPager(viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val mainFragmentPagerAdapter = MainFragmentPagerAdapter(supportFragmentManager)
        mainFragmentPagerAdapter.addFragment(FavoriteMovieFragment(), this.getString(R.string.title_movie))
        mainFragmentPagerAdapter.addFragment(FavoriteTvFragment(), this.getString(R.string.title_tv))

        viewPager.adapter = mainFragmentPagerAdapter

    }
}
