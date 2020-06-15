package com.uni.pis.Adapter.EventsAdapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.Data.EventData.eventData
import com.uni.pis.Events.UserID
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.Events.mStorageRef
import com.uni.pis.R
import kotlinx.android.synthetic.main.cardview_event_viewer.view.*

class MyEventsListVideoAdapter(var MyEvents_ArrayList: ArrayList<eventData>,var VideoUri:Uri):
    RecyclerView.Adapter<MyEventsListVideoAdapter.ViewHolder>() {
    var mFirebaseAuth = FirebaseAuth.getInstance()
    lateinit var mStorageRef: StorageReference


    class ViewHolder(itemView: View, videoUri: Uri): RecyclerView.ViewHolder(itemView){
        lateinit var ViedoStoragelink: String
        lateinit var eventdata:eventData
         var viedoUri=videoUri
        fun binditems(eventData: eventData){
            eventdata=eventData
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
        fun getFileExtension(uri: Uri): String? {
            val cR =itemView.context.contentResolver
            val mime = MimeTypeMap.getSingleton()
            return mime.getExtensionFromMimeType(cR.getType(uri)) }
        fun uploadFile() {
            var userID= mFirebaseAuth.currentUser?.uid!!
            if (viedoUri != null)
            {
                var fileReference = mStorageRef.child(
                    userID +System.currentTimeMillis().toString() + "." + getFileExtension(viedoUri))
                var uploadTask= fileReference.putFile(viedoUri)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                            ViedoStoragelink =it.toString()
                            var data = BackgroundWorker(itemView.context)
                            data.execute("updateventvideo",
                                eventdata.Event_ID,
                                ViedoStoragelink)
                            Toast.makeText(itemView.context,"updated.",Toast.LENGTH_SHORT).show()

                        }


                    }
                    .addOnFailureListener { e ->
                        Log.d("MyEventsListVideoAdapter", "onError: " + e.message)
                    }
            }
            else
            {
                Toast.makeText(itemView.context, "No file selected", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.cardview_event_viewer,parent,false)
        return ViewHolder(v,VideoUri)
    }

    override fun getItemCount(): Int {
        return MyEvents_ArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binditems(MyEvents_ArrayList[position])

        holder.itemView.setOnClickListener{
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Do you want to link video to this Event?")
            builder.setPositiveButton("Confirm"){ _, _ ->
                holder.uploadFile()

            }
            builder.setNegativeButton("Cancel"){ _, _ ->
                Toast.makeText(holder.itemView.context,"Cancelled.",Toast.LENGTH_SHORT).show()
            }
            builder.create().show()}

    }


}