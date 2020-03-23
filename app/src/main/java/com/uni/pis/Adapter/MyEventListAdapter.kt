package com.uni.pis.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.R
import com.uni.pis.model.EventsListeItem
import kotlinx.android.synthetic.main.cardview_eventslist.view.*


class MyEventListAdapter(var arrayList_Myevents: ArrayList<EventsListeItem>, val context: Context):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(EventsListItem:EventsListeItem){
            itemView.tv_eventname.text=EventsListItem.name
            itemView.tv_desc.text=EventsListItem.description
            itemView.iv_event.setImageResource(EventsListItem.Image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.cardview_eventslist,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return arrayList_Myevents.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(arrayList_Myevents[position])





    }


}