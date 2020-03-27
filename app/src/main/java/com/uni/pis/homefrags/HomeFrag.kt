package com.uni.pis

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.uni.pis.Adapter.Home_Item_Adapter
import com.uni.pis.homefrags.MainActivity
import com.uni.pis.model.Home_Item
import com.uni.pis.profile.ProfilePagePersonalFrag
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
        arraylist.add(Home_Item(R.drawable.notf,"Notification"))
        arraylist.add(Home_Item(R.drawable.event,"Events List"))
        arraylist.add(Home_Item(R.drawable.prof,"Profile"))
        arraylist.add(Home_Item(R.drawable.help,"Help"))
        return arraylist

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items: Home_Item = arrayList!![position]
        when (position) {
            0 -> {
//                vp?.getItem(0)
                Toast.makeText(context,"yousef ",Toast.LENGTH_LONG).show()
            }

            1-> {
                val intent = Intent (context, EvenstList::class.java)
                startActivity(intent)
            }

            3->{
                val fm=fragmentManager
                var trans= fm?.beginTransaction()
                if (trans != null) {
                    trans.replace(R.id.FrameHome,ProfilePagePersonalFrag())
                    trans.addToBackStack(null)
                    trans.commit()

                }
            }



        }
    }

}
