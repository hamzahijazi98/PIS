package com.uni.pis.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.R
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*

class MyInvitedEventsAdapter(var arrayList_Invevents: ArrayList<EventsListeItem>, val context: Context):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(EventsListItem:EventsListeItem){
            itemView.tv_eventname.text=EventsListItem.name
            itemView.tv_desc.text=EventsListItem.description
            itemView.iv_event.setImageResource(EventsListItem.Image.toInt())
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
//        holder.binditems(arrayList_Invevents[position])
//    }

    }
}