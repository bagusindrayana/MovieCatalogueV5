package com.bagus.moviecataloguev5.ui.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class MainFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val mFragments = ArrayList<Fragment>()
    private val mTitleFragments = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mTitleFragments.add(title)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleFragments[position]
    }
}