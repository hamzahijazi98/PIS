package com.uni.pis.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.Events.receivercardinvitaion
import com.uni.pis.R
import com.uni.pis.data.eventData
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*

class MyInvitedEventsAdapter(var arrayList_Invevents: ArrayList<eventData>, val context: Context):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(eventData:eventData){
            itemView.tv_eventname.text= eventData.Event_type
            itemView.tv_desc.text= eventData.Description
            itemView.iv_event.setImageResource(eventData.image.toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEventListAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.cardview_event_viewer,parent,false)
        return MyEventListAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return arrayList_Invevents.size
    }

    override fun onBindViewHolder(holder: MyEventListAdapter.ViewHolder, position: Int) {
        holder.binditems(arrayList_Invevents[position])


        holder.itemView.setOnClickListener{
            val image = arrayList_Invevents[position]
            val i = Intent(holder.itemView.context, receivercardinvitaion::class.java)
            val bundle = Bundle()
            val parcel = arrayList_Invevents[position]
            bundle.putParcelable("eventdata", parcel)
            i.putExtra("Bundle", bundle)
            ContextCompat.startActivity(holder.itemView.context, i, Bundle())
        }



    }


}

