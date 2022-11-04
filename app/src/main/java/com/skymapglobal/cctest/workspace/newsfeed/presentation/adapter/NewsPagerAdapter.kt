package com.skymapglobal.cctest.workspace.newsfeed.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skymapglobal.cctest.workspace.main.presentation.MainActivity
import com.skymapglobal.cctest.workspace.newsfeed.domain.usecase.GetTopHeadingsUseCase
import com.skymapglobal.cctest.workspace.newsfeed.presentation.NewsFragment
import com.skymapglobal.cctest.workspace.newsfeed.presentation.NewsViewModel
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber


class NewsPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val getTopHeadingsUseCase: GetTopHeadingsUseCase by inject(GetTopHeadingsUseCase::class.java)
    private var memoFragments: MutableMap<Int, NewsFragment> = mutableMapOf()

    override fun getItemCount(): Int = MainActivity.categories.size

    /*
    * Memo tab if it is created
    * */
    override fun createFragment(position: Int): Fragment {
        Timber.e("createFragment: $position")
        if (memoFragments.containsKey(position)) {
            return memoFragments[position]!!;
        }
        memoFragments[position] =
            NewsFragment.newInstance(
                category = MainActivity.categories[position],
                NewsViewModel(getTopHeadingsUseCase)
            )
        return memoFragments[position]!!
    }
}