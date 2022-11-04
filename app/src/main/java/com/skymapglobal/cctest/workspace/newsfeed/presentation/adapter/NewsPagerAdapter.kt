package com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skymapglobal.cctest.workspace.main.presentation.MainActivity
import com.skymapglobal.cctest.workspace.newsfeed.presentation.NewsFragment


class NewsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private var memoFragments: MutableMap<Int, NewsFragment> = mutableMapOf()

    override fun getItemCount(): Int = MainActivity.categories.size

    /*
    * Memo activated tabs
    * */
    override fun createFragment(position: Int): Fragment {
        if (!memoFragments.containsKey(position)) {
            memoFragments[position] =
                NewsFragment.newInstance(
                    category = MainActivity.categories[position]
                )
        }
        return memoFragments[position]!!
    }
}