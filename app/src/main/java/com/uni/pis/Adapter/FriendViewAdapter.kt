package com.uni.pis.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.data.eventData
import com.uni.pis.data.friendData
import com.uni.pis.data.model.eventDataInvite
import com.uni.pis.model.FriendsItem
import kotlinx.android.synthetic.main.activity_public_page_profile.*
import kotlinx.android.synthetic.main.cardview_friend_list.view.*
import kotlinx.android.synthetic.main.invitationdialog.view.*

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_friend_list, parent, false)
        return ViewHolder(view)


    }
    override fun getItemCount(): Int {
        return friendlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(friendlist[position])





       if(eventDataInvite.EventID!=""){
           holder.itemView.btn_invite.setOnClickListener {
                val mDialogView=LayoutInflater.from(holder.itemView.context).inflate(R.layout.invitationdialog,null)

                val mBulider= AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView)
                    .setTitle("Invitee Number")

                val mAlertDialog=mBulider.show()
                mDialogView.btn_confirm.setOnClickListener {
                    mAlertDialog.dismiss()
                   var inviteeNumber =mDialogView.et_inviteenumber.text.toString()
                    var data = BackgroundWorker(holder.itemView.context)
                    data.execute("invitetomyevent","0","0",eventDataInvite.EventID,
                        friendlist[position].UserID ,inviteeNumber)
                    Toast.makeText(holder.itemView.context,"You Invitee Number Now Is ${eventDataInvite.InviteeNumer}",Toast.LENGTH_LONG).show()
                }
                mDialogView.btn_cancel.setOnClickListener{
                    mAlertDialog.dismiss()
                    Toast.makeText(holder.itemView.context,"Cancelled",Toast.LENGTH_LONG).show()
                }
            }
        }





    }


}
