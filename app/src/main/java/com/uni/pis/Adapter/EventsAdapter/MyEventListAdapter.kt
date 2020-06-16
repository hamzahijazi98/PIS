package com.uni.pis.Adapter.EventsAdapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.uni.pis.BackgroundWorker
import com.uni.pis.R
import com.uni.pis.Events.mStorageRef
import com.uni.pis.Events.MyCardInvitation
import com.uni.pis.Data.EventData.eventData
import com.uni.pis.Events.eventdate
import com.uni.pis.Events.mFirebaseAuth
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*


class MyEventListAdapter(var MyEvents_ArrayList: ArrayList<eventData>):
    RecyclerView.Adapter<MyEventListAdapter.ViewHolder>(),BackgroundWorker.MyCallback {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun binditems(eventData: eventData){
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
        return MyEvents_ArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(MyEvents_ArrayList[position])
        holder.itemView.setOnClickListener{
                  val image = MyEvents_ArrayList[position]
                  val i = Intent(holder.itemView.context, MyCardInvitation::class.java)
                  val bundle = Bundle()
                  val parcel = MyEvents_ArrayList[position]
                  bundle.putParcelable("eventdata", parcel)
                  i.putExtra("Bundle", bundle)
                  ContextCompat.startActivity(holder.itemView.context, i, Bundle())
        }

        holder.itemView.setOnLongClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Do you want to delete this Event?")
            builder.setPositiveButton("Confirm"){ _, _ ->
                removeitem(position,holder)

            }
            builder.setNegativeButton("Cancel"){ _, _ ->
                Toast.makeText(holder.itemView.context,"Cancelled.",Toast.LENGTH_SHORT).show()
            }
            builder.create().show()
            true
        }

    }

    private fun removeitem(position: Int, holder: ViewHolder):Boolean{
        try {
            var EventID= MyEvents_ArrayList[position].Event_ID
            var data = BackgroundWorker(holder.itemView.context)
            data.execute("deleteevent",EventID)
        }
        catch (e:Exception){
            Toast.makeText(holder.itemView.context,e.message, Toast.LENGTH_LONG).show()
        }
        MyEvents_ArrayList.removeAt(position)
        notifyDataSetChanged()
        return true

    }

    override fun onResult(result: String?) {
    }


}