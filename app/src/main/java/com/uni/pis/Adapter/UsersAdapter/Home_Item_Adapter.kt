package com.uni.pis.Adapter.UsersAdapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.uni.pis.R
import com.uni.pis.Data.LoginData.Home_Item
import com.uni.pis.Data.UserData.userData
import kotlinx.android.synthetic.main.fragment_home_.*

class Home_Item_Adapter(var context: Context,var Home_Item_ArrayList: ArrayList<Home_Item>):BaseAdapter() {

    override fun getItem(position: Int): Any {
        return Home_Item_ArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Home_Item_ArrayList.size
    }
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View=View.inflate(context, R.layout.home_cardview,null)
        val icons:ImageView=view.findViewById(R.id.img_home)
        val names:TextView=view.findViewById(R.id.home_icon)
        val listItem: Home_Item =Home_Item_ArrayList.get(position)
        icons.setImageResource(listItem.icons!!)
        names.text=listItem.name

        return view

    }



}