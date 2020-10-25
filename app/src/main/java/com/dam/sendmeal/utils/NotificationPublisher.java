package com.dam.sendmeal.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dam.sendmeal.R;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class NotificationPublisher extends BroadcastReceiver {
    private static NotificationPublisher instance = null;

    private static final String ORDER_CHANNEL_ID = "order_notification_channel";
    private static final int ORDER_NOTIFICATION_ID = 125;

    private NotificationManager mNotifyManager;

    public  NotificationPublisher() {
    }

    public static NotificationPublisher getInstance() {
        if(instance == null){
            instance = new NotificationPublisher();
        }
        return instance;
    }

    public void createNotificationChannel(Context context){
        mNotifyManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(ORDER_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        createNotificationChannel(context);
        showNotification(context);
    }

    private void showNotification(Context context) {



        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sendmeal_logo);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, ORDER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sendmeal_logo)
                .setLargeIcon(icon)
                .setContentTitle("Tu plato esta en preparacion")
                .setContentText("El local esta preparando tu plato!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(ORDER_NOTIFICATION_ID, builder.build());

        //context.sendBroadcast(intent);


    }
}

//TODO hacer metodo del canal como en las diapositivas
//        String CHANNEL_ID = "Notificacion oferta";

//        Intent intent = new Intent(context, Home.class);
//String name = null;
//intent.putExtra("data", name);
//System.out.println("SETEO EN BROADCAST   " + intent.getExtras());
//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

//        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);

//
//    private NotificationCompat.Builder getNotificationBuilder(Context context){
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, ORDER_CHANNEL_ID)
//                .setContentTitle("You've been notified!")
//                .setContentText("This is your notification text.")
//                .setSmallIcon(R.drawable.logo);
//        return notifyBuilder;
//    }