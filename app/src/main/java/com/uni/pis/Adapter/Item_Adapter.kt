package com.uni.pis.Adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.uni.pis.R
import com.uni.pis.model.Home_Item

class Item_Adapters(var context: Context,var arrayList: ArrayList<Home_Item>):BaseAdapter() {

    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view:View=View.inflate(context, R.layout.cardview_itemgrid,null)
        var icons:ImageView=view.findViewById(R.id.img_home)
        var names:TextView=view.findViewById(R.id.home_icon)
        var listItem:Home_Item=arrayList.get(position)
        icons.setImageResource(listItem.icons!!)
        names.text=listItem.name
        return view

    }



}