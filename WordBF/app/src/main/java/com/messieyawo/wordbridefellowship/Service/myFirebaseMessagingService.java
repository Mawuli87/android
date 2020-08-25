package com.messieyawo.wordbridefellowship.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.messieyawo.wordbridefellowship.R;

import java.util.Map;

public class myFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                sendNotification(remoteMessage);
            else
                sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        Map<String,String> data = remoteMessage.getData();
        String title = data.get("Word Bride Fellowship");
        String content = data.get("Content");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "EDNTDev";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //only active for android o because on other it needs notifcation channel
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Word bride daily bible Notification",
                    NotificationManager.IMPORTANCE_HIGH);
            //configure the nitification channel
            notificationChannel.setDescription("Word Bride Fellowship Daily Bible Verses");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
           notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.splash_eagle))
                .setTicker(title)
                .setContentText(content)
                .setContentInfo("Word Bride Fellowship Daily Bible Verses");
        notificationManager.notify(1,notificationBuilder.build());


    }
}
