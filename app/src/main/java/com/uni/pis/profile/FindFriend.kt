package com.uni.pis.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.R
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_find_friend.*
import java.util.*
import kotlin.collections.ArrayList

class FindFriend : AppCompatActivity() {

    val friendarraylist=ArrayList<FriendsItem>()
    val searcharraylist=ArrayList<FriendsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friend)
        friendarraylist.add(FriendsItem("123456","yousef",
            R.drawable.ic_image_black_24dp.toString()))
        friendarraylist.add(FriendsItem("123456","yousef",
            R.drawable.ic_image_black_24dp.toString()))
        friendarraylist.add(FriendsItem("123456","yousef",
            R.drawable.ic_image_black_24dp.toString()))
        friendarraylist.add(FriendsItem("123456","yousef",
            R.drawable.ic_image_black_24dp.toString()))

        searcharraylist.addAll(friendarraylist)

        val friendarraylistadapter=FriendViewAdapter(searcharraylist,this)
        rv_findfriend.layoutManager=LinearLayoutManager(this)
        rv_findfriend.adapter=friendarraylistadapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem=menu!!.findItem(R.id.search)

        if(menuItem!=null){
            val searchView=menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty())
                    {
                        searcharraylist.clear()
                        val search=newText.toLowerCase(Locale.getDefault())
                        friendarraylist.forEach{
                            if(it.Name.toLowerCase(Locale.getDefault()).contains(search)){
                                searcharraylist.add(it)
                            }
                        }

                        rv_findfriend.adapter!!.notifyDataSetChanged()
                    }
                    else
                    {
                        searcharraylist.clear()
                        searcharraylist.addAll(friendarraylist)
                        rv_findfriend.adapter!!.notifyDataSetChanged()
                    }


                    return true
                }


            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

}
