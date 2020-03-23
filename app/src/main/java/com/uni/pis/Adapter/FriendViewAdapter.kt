package com.uni.pis.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.R
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.cardview_friend.view.*

class FriendViewAdapter(val friendlist:ArrayList<FriendsItem>,val context: Context):RecyclerView.Adapter<FriendViewAdapter.ViewHolder>() {


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        fun bindItems(Friendsitem: FriendsItem) {
            itemView.tv_friendname.text = Friendsitem.Name
            itemView.iv_friend.setImageResource(Friendsitem.Image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_friend, parent, false)
        return ViewHolder(view)


    }
    override fun getItemCount(): Int {
        return friendlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(friendlist[position])


    }


}
