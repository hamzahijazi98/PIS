package com.uni.pis.Adapter.EventsAdapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.Events.TheirCardInvitation
import com.uni.pis.Events.mStorageRef
import com.uni.pis.R
import com.uni.pis.Data.EventData.eventData
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*

class MyInvitedEventsAdapter(var InvitedEventsArrayList: ArrayList<eventData>):
    RecyclerView.Adapter<MyInvitedEventsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun binditems(eventData: eventData){
            itemView.tv_eventname.text= eventData.Event_type
            itemView.tv_desc.text= eventData.Description
            if (eventData.image!="") {
                mStorageRef =
                    FirebaseStorage.getInstance().getReferenceFromUrl(eventData.image)
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    itemView.iv_event.setImageBitmap(
                        Bitmap.createScaledBitmap(
                            bmp, itemView.iv_event.width,
                            itemView.iv_event.height, false
                        )
                    )
                }.addOnFailureListener {
                    // Handle any errors
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.cardview_event_viewer,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return InvitedEventsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(InvitedEventsArrayList[position])


        holder.itemView.setOnClickListener{
            val image = InvitedEventsArrayList[position]
            val i = Intent(holder.itemView.context, TheirCardInvitation::class.java)
            val bundle = Bundle()
            val parcel = InvitedEventsArrayList[position]
            bundle.putParcelable("eventdata", parcel)
            i.putExtra("Bundle", bundle)
            ContextCompat.startActivity(holder.itemView.context, i, Bundle())
        }



    }


}

