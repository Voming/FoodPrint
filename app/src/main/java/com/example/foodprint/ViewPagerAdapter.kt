package com.example.foodprint

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val items = ArrayList<Fragment>()
    override fun getCount(): Int {
        return items.size

    }

    override fun getItem(position: Int): Fragment {
        return items[position]

    }

    fun updateFragments(items : List<Fragment>){
        this.items.addAll(items)
    }

}