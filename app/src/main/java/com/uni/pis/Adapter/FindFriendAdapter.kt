package com.uni.pis.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.uni.pis.BackgroundWorker
import com.uni.pis.Events.UserID
import com.uni.pis.Events.mFirebaseAuth
import com.uni.pis.R
import com.uni.pis.data.friendData
import com.uni.pis.data.userData
import kotlinx.android.synthetic.main.cardview_find_friend_list.view.*
import kotlinx.android.synthetic.main.cardview_friend_list.view.*
import kotlinx.android.synthetic.main.cardview_friend_list.view.iv_friend
import kotlinx.android.synthetic.main.cardview_friend_list.view.tv_friendname
import org.json.JSONException
import org.json.JSONObject
import javax.xml.transform.ErrorListener
import javax.xml.transform.TransformerException

class FindFriendAdapter(val FindFriend:ArrayList<friendData>,context:Context):
    RecyclerView.Adapter<FindFriendAdapter.ViewHolder>() {

    var firbaseNotify=FirebaseMessaging.getInstance().subscribeToTopic("FriendRequest");

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private var mRequestQue: RequestQueue? = null
        private val URL = "https://fcm.googleapis.com/fcm/send"
        var userID= mFirebaseAuth.currentUser?.uid!!
        var firbaseNotify=FirebaseMessaging.getInstance().subscribeToTopic("FriendRequest");
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
                        //Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    // Handle any errors
                }
            }
            catch (e: Exception){
                // Toast.makeText(context,e.message, Toast.LENGTH_LONG).show()
            }

            itemView.addfriend.setOnClickListener {

                sendNotification(userID,friendData.UserID)
                itemView.addfriend.text="Add as Friend"
            }
        }



        private fun sendNotification(userID :String,friendID:String) {
            mRequestQue = Volley.newRequestQueue(itemView.context);

            val json = JSONObject()
            try {
                json.put("to", "/topics/" + "FriendRequest ")
                val notificationObj = JSONObject()
                notificationObj.put("title", "Friend Request")
                notificationObj.put("body", "${userData.first_name} ${userData.last_name} Want to be your friend")
                val extraData = JSONObject()
                extraData.put("UserID", userID)
                extraData.put("FriendID",friendID )
                extraData.put("Image",userData.image )
                extraData.put("Name","${userData.first_name} ${userData.last_name}")
                json.put("notification", notificationObj)
                json.put("data", extraData)
                val request: JsonObjectRequest = object : JsonObjectRequest(
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
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val header: MutableMap<String, String> =
                            HashMap()
                        header["content-type"] = "application/json"
                        header["authorization"] = "key=AIzaSyD46-_quPy21OCoSi1npr1Y9SssSoHML7c"
                        return header
                    }
                }
                mRequestQue!!.add(request)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }






    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindFriendAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_find_friend_list, parent, false)
        return FindFriendAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FindFriend.size
    }

    override fun onBindViewHolder(holder: FindFriendAdapter.ViewHolder, position: Int) {
        holder.bindItems(FindFriend[position])
    }




}