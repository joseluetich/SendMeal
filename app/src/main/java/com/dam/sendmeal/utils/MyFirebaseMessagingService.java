package com.dam.sendmeal.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dam.sendmeal.R;
import com.dam.sendmeal.ui.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String ACCEPTED_ORDER_CHANNEL = "accepted_order_notification_channel";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("TAG", "From: " + remoteMessage.getFrom());

        // Validar si el mensaje trae datos
        if (remoteMessage.getData().size() > 0) {
            Log.d("TAG", "Payload del mensaje: " + remoteMessage.getData());
        }
        // Pueden validar si el mensaje trae una notificación
        if (remoteMessage.getNotification() != null) {
            Log.d("TAG", "Cuerpo de la notificación del mensaje: " + remoteMessage.getNotification().getBody());
        }

        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }


    private void sendNotification(String title, String body) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ACCEPTED_ORDER_CHANNEL,
                    "Order Notification", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        Bitmap icon = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_sendmeal_logo);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, ACCEPTED_ORDER_CHANNEL)
                        .setSmallIcon(R.drawable.ic_sendmeal_logo)
                        .setLargeIcon(icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationBuilder.setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());

    }

}