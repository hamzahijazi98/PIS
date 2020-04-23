package com.uni.pis.Adapter.UsersAdapter

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
import com.uni.pis.Data.UserData.InviteeListData
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import kotlinx.android.synthetic.main.cardview_eventinvitee.view.*


class   InviteeListAdapter (val InviteeListArrayList:ArrayList<InviteeListData>, val context: Context):
    RecyclerView.Adapter<InviteeListAdapter.ViewHolder>() {
    companion object{
    var total:Int=0
    var totalAccepted:Int=0
    var totalrejeted:Int=0
    var totalmaby:Int=0
    }
    lateinit var callbacks : FragCallbacks
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
                itemView.tv_status.text="Reject"
                    total +=InviteeListData.inviteenumber.trim().toInt()
                    totalrejeted +=InviteeListData.inviteenumber.trim().toInt()

                }
                "1"-> {itemView.ib_maybe.visibility = View.VISIBLE
                    itemView.tv_status.text="Maybe"
                    total +=InviteeListData.inviteenumber.trim().toInt()
                    totalmaby +=InviteeListData.inviteenumber.trim().toInt()}
                "2"-> {itemView.ib_accpet.visibility = View.VISIBLE
                    itemView.tv_status.text="Accept"
                    total +=InviteeListData.inviteenumber.trim().toInt()
                    totalAccepted +=InviteeListData.inviteenumber.trim().toInt()}
            }
            itemView.tv_personInviteeNumer.text=InviteeListData.inviteenumber

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        total=0
        totalAccepted=0
        totalrejeted=0
        totalmaby=0
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_eventinvitee, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return InviteeListArrayList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(InviteeListArrayList[position])
        callbacks.sendResult(
            total,
            totalAccepted,
            totalrejeted,
            totalmaby
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        callbacks= context as FragCallbacks
    }

    interface FragCallbacks
    {
        fun sendResult (total: Int,totalaccepted: Int,totalrejected: Int,totalmaby: Int) // this method will be implemented in the hosting activity
    }


}