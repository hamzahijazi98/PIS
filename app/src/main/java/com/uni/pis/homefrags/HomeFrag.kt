package com.uni.pis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.uni.pis.Adapter.Home_Item_Adapter
import com.uni.pis.model.Home_Item
import kotlinx.android.synthetic.main.fragment_main_.*
import java.util.ArrayList

class HomeFrag : Fragment(), AdapterView.OnItemClickListener {
    private var arrayList: ArrayList<Home_Item>?=null
    private var gridView: GridView?=null
    private var itemAdapters: Home_Item_Adapter?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        gridView=Gridview_home
        arrayList=ArrayList()
        arrayList=SetDataList()
        itemAdapters=Home_Item_Adapter(view.context,arrayList!!)
        gridView?.adapter=itemAdapters
        gridView?.onItemClickListener=this




    }

    private fun SetDataList():ArrayList<Home_Item>{
        var arraylist:ArrayList<Home_Item> = ArrayList()
        arraylist.add(Home_Item(R.drawable.ic_image_black_24dp,"Home"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Create Event"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Profile"))
        arraylist.add(Home_Item(R.drawable.ic_launcher_foreground,"Help"))
        return arraylist

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items:Home_Item=arrayList!!.get(position)
        if(position==0)
            Toast.makeText(context,items.name, Toast.LENGTH_LONG).show()

        if(position==1)
            Toast.makeText(context,items.name, Toast.LENGTH_LONG).show()

    }


}
