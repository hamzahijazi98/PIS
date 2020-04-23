package com.uni.pis.Adapter.UsersAdapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.Data.UserData.friendData
import com.uni.pis.Data.UserData.userData
import com.uni.pis.profile.PublicPageProfile
import kotlinx.android.synthetic.main.cardview_find_friend_list.view.*
import kotlinx.android.synthetic.main.cardview_friend_list.view.iv_friend
import kotlinx.android.synthetic.main.cardview_friend_list.view.tv_friendname
import org.json.JSONException
import org.json.JSONObject
import javax.xml.transform.ErrorListener
import javax.xml.transform.TransformerException

class FindFriendAdapter(val FindFriendArrayList:ArrayList<friendData>, context:Context):
    RecyclerView.Adapter<FindFriendAdapter.ViewHolder>() {

    var firbaseNotify=FirebaseMessaging.getInstance().subscribeToTopic("FriendRequest");

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val URL = "https://fcm.googleapis.com/fcm/send"
        var userID= mFirebaseAuth.currentUser?.uid!!

        lateinit var mStorageRef: StorageReference
        fun bindItems(friendData: friendData){
            itemView.tv_friendname.text = friendData.first_name+" "+friendData.last_name
            try {
                mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(friendData.image)
                mStorageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    try{
                        itemView.iv_friend.setImageBitmap(
                            Bitmap.createScaledBitmap(
                                bmp, itemView.iv_friend.width,
                                itemView.iv_friend.height, false
                            )
                        )}
                    catch (e: IllegalStateException){
                        Log.d("FindFriendAdapter", "onError: " + e.message)
                    }
                }.addOnFailureListener {
                    Log.d("FindFriendAdapter", "onError: " + it.message)
                }
            }
            catch (e: Exception){
                Log.d("FindFriendAdapter", "onError: " + e.message)
            }

            itemView.addfriend.setOnClickListener {

                sendNotification(userID,friendData.UserID)
                itemView.addfriend.text="Add as Friend"
            }
        }



        private fun sendNotification(userID :String,friendID:String) {
            val mRequestQue = Volley.newRequestQueue(itemView.context);
            Volley.newRequestQueue(itemView.context);
            val title="Friend Request"
            val body="${userData.first_name} ${userData.last_name} Want to be your friend"
            val json = JSONObject()
            try {
                json.put("to", "/topics/FriendRequest$friendID")
                val notificationObj = JSONObject()

                notificationObj.put("title",title )
                notificationObj.put("body", body)
                val extraData = JSONObject()
                extraData.put("UserID", userID)
                extraData.put("FriendID",friendID )
                extraData.put("Image", userData.image )
                extraData.put("Name","${userData.first_name} ${userData.last_name}")
                json.put("notification", notificationObj)
                json.put("data", extraData)
                var request: JsonObjectRequest = object : JsonObjectRequest(
                    Method.POST, URL,
                    json,
                    Response.Listener<JSONObject> { Log.d("MUR", "onResponse: ") },
                    object : ErrorListener, Response.ErrorListener {
                        override fun onErrorResponse(error: VolleyError) {
                            Log.d("Notify", "onError: " + error.networkResponse)
                        }

                        override fun warning(exception: TransformerException?) {
                            Log.d("Notify", "onError: " + exception!!.message) }

                        override fun error(exception: TransformerException?) {
                            Log.d("Notify", "onError: " + exception!!.message) }

                        override fun fatalError(exception: TransformerException?) {
                            Log.d("Notify", "onError: " + exception!!.message)}
                    }
                ) {
                    override fun getHeaders(): Map<String, String> {
                        val header: HashMap<String, String> =
                            HashMap()
                        header["content-type"] = "application/json"
                        header["authorization"] = "key=AIzaSyD46-_quPy21OCoSi1npr1Y9SssSoHML7c"
                        return header
                    }
                }
                try {
                    var data = BackgroundWorker(itemView.context)
                    data.execute("notification","send",System.currentTimeMillis().toString(),title,body,
                        userData.image,userID,friendID )

                }
                catch (e:java.lang.Exception)
                {
                    Log.d("Notify", "onError: " + e.message)
                }
                mRequestQue.add(request)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_find_friend_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FindFriendArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(FindFriendArrayList[position])
        holder.itemView.setOnClickListener{

            val image = FindFriendArrayList[position]
            val i = Intent(holder.itemView.context, PublicPageProfile::class.java)
            val bundle = Bundle()
            val parcel = FindFriendArrayList[position]
            bundle.putParcelable("friendData", parcel)
            i.putExtra("Bundle", bundle)
            ContextCompat.startActivity(holder.itemView.context, i, Bundle())
        }


    }
}