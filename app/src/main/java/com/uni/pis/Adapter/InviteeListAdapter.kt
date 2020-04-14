package com.uni.pis.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.data.InviteeListData
import kotlinx.android.synthetic.main.cardview_eventinvitee.view.*

class InviteeListAdapter (val InviteeList:ArrayList<InviteeListData>, val context: Context):
    RecyclerView.Adapter<InviteeListAdapter.ViewHolder>() {

    private val userid = mFirebaseAuth.currentUser?.uid!!

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        lateinit var mStorageRef: StorageReference
        @SuppressLint("SetTextI18n")
        fun bindItems(InviteeListData: InviteeListData) {
            itemView.tv_friendname.text = InviteeListData.Name
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(InviteeListData.image)
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    try {
                        itemView.iv_friend.setImageBitmap(
                            Bitmap.createScaledBitmap(
                                bmp, itemView.iv_friend.width,
                                itemView.iv_friend.height, false
                            )
                        )
                    } catch (e: IllegalStateException) {
                        //Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    // Handle any errors
                }
            } catch (e: Exception) {
                // Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
            }

            when(InviteeListData.attendace){
                "0"-> {itemView.ib_reject.visibility = View.VISIBLE
                itemView.tv_status.text="Reject"}
                "1"-> {itemView.ib_maybe.visibility = View.VISIBLE
                    itemView.tv_status.text="Maybe"}
                "2"-> {itemView.ib_accpet.visibility = View.VISIBLE
                    itemView.tv_status.text="Accept"}
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_eventinvitee, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return InviteeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(InviteeList[position])
    }
}