package com.uni.pis.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.uni.pis.Adapter.FriendViewAdapter
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.EvenstList
import com.uni.pis.R
import com.uni.pis.data.friendData
import com.uni.pis.model.EventsListeItem
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_find_friend.*
import java.util.*
import kotlin.collections.ArrayList

class FindFriend : AppCompatActivity(),BackgroundWorker.MyCallback {

    val friendarraylist=ArrayList<friendData>()
    val searcharraylist=ArrayList<friendData>()
    val friendarraylistadapter=FriendViewAdapter(searcharraylist,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friend)



        searcharraylist.addAll(friendarraylist)


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

    override fun onResult(result: String?) {
        var data = result.split("*")

        for (i in data) {

            var friend = i.split("&")
            if (friend.size > 1) {

                var firstname=friend[BackgroundWorker.userDataOrder.firstName.index].substringAfter("=")
                var last_name=friend[BackgroundWorker.userDataOrder.lastName.index].substringAfter("=")
                var phoneNumber=friend[BackgroundWorker.userDataOrder.phoneNumber.index].substringAfter("=")
                var gender=friend[BackgroundWorker.userDataOrder.gender.index].substringAfter("=")
                var email=friend[BackgroundWorker.userDataOrder.email.index].substringAfter("=")
                var birthdate=friend[BackgroundWorker.userDataOrder.birthday.index].substringAfter("=")
                var city=friend[BackgroundWorker.userDataOrder.city.index].substringAfter("=")
                var image=friend[BackgroundWorker.userDataOrder.image.index].substringAfter("=")
                var friendID=friend[BackgroundWorker.userDataOrder.UserID.index].substringAfter("=")
                friendarraylist.add(friendData(firstname,last_name,email,phoneNumber,gender,birthdate,image,city,friendID))
            }

        }
    }

}
