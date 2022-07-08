package com.example.proyecto

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelID = "notification_channel"
const val channelName = "com.example.proyecto"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    // generate the notification
    // attach the notification created with the custom layout
    // shot the notification

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, message: String): RemoteViews {
        val RemoteView = RemoteViews("com.example.proyecto", R.layout.notification)

        RemoteView.setTextViewText(R.id.title, title)
        RemoteView.setTextViewText(R.id.message, message)
        RemoteView.setImageViewResource(R.id.app_logo, R.drawable.logo)



        return RemoteView
    }

    fun generateNotification(title: String, message: String){

        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
        val secondIntent = Intent(this, SecondActivity::class.java)
        val secondPendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, secondIntent, 0)
        val thirdIntent = Intent(this, ThirdActivity::class.java)
        val thirdPendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, thirdIntent, 0)
//
        //chanel id, chanel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.ec)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ec, getString(R.string.second_name),
                secondPendingIntent)
            .addAction(R.drawable.ec, getString(R.string.third_name),
                thirdPendingIntent)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())



        builder = builder.setContent(getRemoteView(title,message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChanel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChanel)
        }

        notificationManager.notify(0,builder.build())

    }

}