package com.uni.pis.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.R
import com.uni.pis.data.friendData
import kotlinx.android.synthetic.main.cardview_friend_list.view.*

class FindFriendAdapter(val FindFriend:ArrayList<friendData>,context:Context):
    RecyclerView.Adapter<FindFriendAdapter.ViewHolder>() {


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        lateinit var mStorageRef: StorageReference
        fun bindItems(friendData: friendData){
            itemView.tv_friendname.text = friendData.first_name+" "+friendData.last_name
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(friendData.image)
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    try{
                        itemView.iv_friend.setImageBitmap(
                            Bitmap.createScaledBitmap(
                                bmp, itemView.iv_friend.width,
                                itemView.iv_friend.height, false
                            )
                        )}
                    catch (e: IllegalStateException){
                        //Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    // Handle any errors
                }
            }
            catch (e: Exception){
                // Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindFriendAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_find_friend_list, parent, false)
        return FindFriendAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FindFriend.size
    }

    override fun onBindViewHolder(holder: FindFriendAdapter.ViewHolder, position: Int) {
        holder.bindItems(FindFriend[position])
    }


}