package com.uni.pis.Adapter.EventsAdapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.uni.pis.Events.Create_Invitation
import com.uni.pis.R
import com.uni.pis.Events.VideoInvitation
import com.uni.pis.Data.EventData.Events_Item
import kotlinx.android.synthetic.main.cardview_create_event.view.*
import android.view.View as View1


lateinit var type:String
class EventsAdapter(val EventsTypeArrayList: ArrayList<Events_Item>, val context: Context): RecyclerView.Adapter<EventsAdapter.ViewHolder>()
{

    class ViewHolder(itemView: View1): RecyclerView.ViewHolder(itemView){
        fun bindItems(EventItem: Events_Item){
            itemView.tv_eventName.text=EventItem.title
            itemView.IV_events.setImageResource(EventItem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.cardview_create_event,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return EventsTypeArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(EventsTypeArrayList[position])
        val view= holder.itemView.findViewById<LinearLayout>(R.id.initialinvisiable)
        holder.itemView.setOnClickListener{
when(position) {

    0 -> {
        view.visibility=android.view.View.VISIBLE
        holder.itemView.btn_template.setOnClickListener {
        val i = Intent(context, Create_Invitation::class.java)
        ContextCompat.startActivity(context, i, Bundle())
        }

        holder.itemView.btn_video.setOnClickListener {
            val i=Intent(context, VideoInvitation::class.java)
            ContextCompat.startActivity(context,i,Bundle())
        }
    }



    1->{ // var i = Intent(context, VideoInvitation::class.java)
       // ContextCompat.startActivity(context, i, Bundle())
        Toast.makeText(context, "yousef", Toast.LENGTH_LONG).show()
    }


    2-> {Toast.makeText(context, "yousefdggsfrty", Toast.LENGTH_LONG).show()}


}

        }
    }
}






