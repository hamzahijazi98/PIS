package com.uni.pis.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.R
import com.uni.pis.Events.mStorageRef
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*


class MyEventListAdapter(var arrayList_Myevents: ArrayList<EventsListeItem>):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(EventsListItem:EventsListeItem){
            itemView.tv_eventname.text=EventsListItem.name
            itemView.tv_desc.text=EventsListItem.description
            if (EventsListItem.Image!="") {
                mStorageRef =
                    FirebaseStorage.getInstance().getReferenceFromUrl(EventsListItem.Image)
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
        val v=LayoutInflater.from(parent.context).inflate(R.layout.cardview_event_viewer,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList_Myevents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(arrayList_Myevents[position])

    }


}