package com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skymapglobal.cctest.workspace.main.presentation.MainActivity
import com.skymapglobal.cctest.workspace.newsfeed.presentation.NewsFragment


class NewsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = MainActivity.categories.size
    override fun createFragment(position: Int): Fragment =
        NewsFragment.newInstance(category = MainActivity.categories[position])
}