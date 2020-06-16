package com.uni.pis.Adapter.UsersAdapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sendbird.android.*
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.Data.EventData.eventDataInvite
import com.uni.pis.Data.UserData.FriendsItem
import kotlinx.android.synthetic.main.cardview_friend_list.view.*
import kotlinx.android.synthetic.main.invitationdialog.view.*

class FriendViewAdapter(val FriendArrayList:ArrayList<FriendsItem>, val context: Context):RecyclerView.Adapter<FriendViewAdapter.ViewHolder>() {
    private val SENDBIRDAPPID = "C70ACBE6-0911-45D5-B02B-C56D3ADDF158"
    private val userid = mFirebaseAuth.currentUser?.uid!!

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        lateinit var mStorageRef: StorageReference
        fun bindItems(Friendsitem: FriendsItem) {
            itemView.tv_friendname.text = Friendsitem.Name
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(Friendsitem.Image)
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
                        Log.d("FriendViewAdapter", "onError: " + e.message)
                    }
                }.addOnFailureListener {
                    Log.d("FriendViewAdapter", "onError: " + it.message)
                }
            } catch (e: Exception) {
                Log.d("FriendViewAdapter", "onError: " + e.message)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_friend_list, parent, false)
        return ViewHolder(view)


    }

    override fun getItemCount(): Int {
        return FriendArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(FriendArrayList[position])

        if (eventDataInvite.EventID != "") {
            holder.itemView.btn_invite.setOnClickListener {
                val mDialogView = LayoutInflater.from(holder.itemView.context)
                    .inflate(R.layout.invitationdialog, null)
                val mBulider = AlertDialog.Builder(holder.itemView.context)
                    .setView(mDialogView)
                    .setTitle("Invitee Number")

                val mAlertDialog = mBulider.show()
                mDialogView.btn_confirm.setOnClickListener {
                    mAlertDialog.dismiss()
                    var inviteeNumber = mDialogView.et_inviteenumber.text.toString()
                    var data = BackgroundWorker(holder.itemView.context)
                    data.execute("invitetomyevent", "0", "0", eventDataInvite.EventID,
                        FriendArrayList[position].UserID, inviteeNumber
                    )



                    SendBird.init(SENDBIRDAPPID, holder.itemView.context)
                    SendBird.connect(userid, object : SendBird.ConnectHandler {
                        override fun onConnected(user: User?, e: SendBirdException?) {
                            if (e != null) {
                                return
                            }
                        }
                    })
                    GroupChannel.getChannel(
                        eventDataInvite.channelUrl,
                        object : GroupChannel.GroupChannelGetHandler {
                            override fun onResult(
                                groupChannel: GroupChannel,
                                e: SendBirdException?
                            ) {
                                if (e != null) { // Error.
                                    return
                                }
                                val userIds: MutableList<String> = ArrayList()
                                userIds.add(FriendArrayList[position].UserID)
                                groupChannel.inviteWithUserIds(
                                    userIds,
                                    object : GroupChannel.GroupChannelInviteHandler {
                                        override fun onResult(e: SendBirdException?) {
                                            if (e != null) { // Error.
                                                return
                                            }
                                        }
                                    })

                            }
                        })



                    Toast.makeText(
                        holder.itemView.context,
                        "Invitation Sent Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                mDialogView.btn_cancel.setOnClickListener {
                    mAlertDialog.dismiss()
                    Toast.makeText(holder.itemView.context, "Cancelled", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
