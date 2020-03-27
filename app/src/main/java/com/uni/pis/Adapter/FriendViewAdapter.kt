package com.uni.pis.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.R
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.cardview_friend.view.*
import kotlinx.android.synthetic.main.fragment_profile_page_personal.*
class FriendViewAdapter(val friendlist:ArrayList<FriendsItem>,val context: Context):RecyclerView.Adapter<FriendViewAdapter.ViewHolder>() {


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        lateinit var mStorageRef: StorageReference
        fun bindItems(Friendsitem: FriendsItem) {
            itemView.tv_friendname.text = Friendsitem.Name
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(Friendsitem.Image)
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
