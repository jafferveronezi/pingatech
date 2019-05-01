package br.com.veronezitecnologia.pingatech.services

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.PendingIntent
import android.content.Intent
import br.com.veronezitecnologia.pingatech.R

class PingaMessagingService:FirebaseMessagingService(){

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        notify(remoteMessage)
    }

    fun notify(remoteMessage: RemoteMessage?) {
        val b = NotificationCompat.Builder(applicationContext)
        b.setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(remoteMessage!!.notification?.title.toString())
            .setContentText(remoteMessage!!.notification?.body.toString())
            .setContentInfo("INFO")
            .setContentIntent(
                createPendingIntent(
                    remoteMessage.notification?.clickAction,
                    remoteMessage.data
                )
            );
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(1, b.build())
    }


    private fun createPendingIntent(clickAction: String?, data: Map<String, String>): PendingIntent? {
        var pendingIntent: PendingIntent? = null
        try {
            val resultIntent = Intent(clickAction)
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

            for ((key, value) in data)
                resultIntent.putExtra(key, value)

            pendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT
            )
        } catch (ex: Exception) {

        }

        return pendingIntent
    }
}