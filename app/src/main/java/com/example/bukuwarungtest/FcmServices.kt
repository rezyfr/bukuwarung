package com.example.bukuwarungtest

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FcmServices : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.d("From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Timber.d("Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Timber.d(
                "Message Notification Body: ${remoteMessage.notification!!.body}"
            )
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    override fun onNewToken(p0: String?) {
        Timber.d("Refreshed token: $p0")
    }
}
