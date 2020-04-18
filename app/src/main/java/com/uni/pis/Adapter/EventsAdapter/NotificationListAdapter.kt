package com.uni.pis.Adapter.EventsAdapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Data.UserData.acceptFriendData
import kotlinx.android.synthetic.main.cardview_notification.view.*


class NotificationListAdapter(val NotificationArray:ArrayList<acceptFriendData>, context: Context):
    RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        lateinit var mStorageRef: StorageReference

        fun bindItems(acceptFriendData: acceptFriendData) {
            itemView.tv_acceptnamefriend.text = acceptFriendData.first_name
            try {
                mStorageRef =
                    FirebaseStorage.getInstance().getReferenceFromUrl(acceptFriendData.image)
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    try {
                        itemView.iv_acceptImage.setImageBitmap(
                            Bitmap.createScaledBitmap(
                                bmp, itemView.iv_acceptImage.width,
                                itemView.iv_acceptImage.height, false
                            )
                        )
                    } catch (e: IllegalStateException) {
                        Log.d(this.toString(), "onError: " + e.message)
                    } catch (e: IllegalArgumentException) {
                        Log.d(this.toString(), "onError: " + e.message)
                    }
                }.addOnFailureListener {
                    Log.d(this.toString(), "onError: " + it.message)
                }
            } catch (e: Exception) {
                // Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
            }
            itemView.accept_friend.setOnClickListener {
                var data = BackgroundWorker(itemView.context)
                data.execute("addfriend", acceptFriendData.FriendID, acceptFriendData.UserID)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return NotificationArray.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(NotificationArray[position])
        when (position) {
            position-> {
                holder.itemView.btn_reject.setOnClickListener {
                    NotificationArray.removeAt(position)
                    notifyItemRemoved(holder.adapterPosition)
                }
            }
        }
    }
}