package com.uni.pis.Adapter

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
import com.uni.pis.R
import com.uni.pis.Events.mStorageRef
import com.uni.pis.Events.MyCardInvitation
import com.uni.pis.data.eventData
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*


class MyEventListAdapter(var arrayList_Myevents: ArrayList<eventData>):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(eventData:eventData){
            itemView.tv_eventname.text=eventData.Event_type
            itemView.tv_desc.text=eventData.Description
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
        val v=LayoutInflater.from(parent.context).inflate(R.layout.cardview_event_viewer,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList_Myevents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(arrayList_Myevents[position])

        holder.itemView.setOnClickListener{
                  val image = arrayList_Myevents[position]
                  val i = Intent(holder.itemView.context, MyCardInvitation::class.java)
                  val bundle = Bundle()
                  val parcel = arrayList_Myevents[position]
                  bundle.putParcelable("eventdata", parcel)
                  i.putExtra("Bundle", bundle)
                  ContextCompat.startActivity(holder.itemView.context, i, Bundle())
        }


    }


}