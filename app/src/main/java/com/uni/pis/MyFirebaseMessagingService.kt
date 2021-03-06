package com.uni.pis

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        val extraData =
        remoteMessage.data
        val UserID = extraData["FriendID"]
        val FriendID = extraData["UserID"]
        val friendImage = extraData["Image"]
        val friendName = String(extraData["Name"]!!.toByteArray(), charset("UTF-8"))


            val notificationBuilder = Notification.Builder( this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_menu_add)
            val intent = Intent(this, com.uni.pis.Notification::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK

            intent.putExtra("UserID", UserID)
            intent.putExtra("FriendID", FriendID)
            intent.putExtra("Image", friendImage)
            intent.putExtra("name", friendName)
            startActivity(intent)


            val pendingIntent =
                PendingIntent.getActivity(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.setContentIntent(pendingIntent)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val id = System.currentTimeMillis().toInt()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel("TAC", "demo", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            notificationManager.notify(id, notificationBuilder.build())
        }
    }

