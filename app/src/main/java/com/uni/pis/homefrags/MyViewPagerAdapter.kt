package com.uni.pis.homefrags

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyViewPagerAdapter(manger: FragmentManager): FragmentPagerAdapter(manger){
    private val fragmentlist:MutableList<Fragment> =ArrayList()
    private val titlelist:MutableList<String> =ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentlist[position]
    }


    override fun getCount(): Int {
        return fragmentlist.size
    }
    fun addfragment(fragment: Fragment, title:String){
        fragmentlist.add(fragment)
        titlelist.add(title)

    }
    override fun getPageTitle(position: Int): CharSequence? {
        return titlelist[position]
    }
}