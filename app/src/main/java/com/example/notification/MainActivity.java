package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "Channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        Button button = findViewById(R.id.notify);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               // Create an explicit intent for an Activity in your app
               Intent intent = new Intent(MainActivity.this,AlertDetails.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                       intent, PendingIntent.FLAG_IMMUTABLE);

               NotificationCompat.Builder builder = new NotificationCompat.Builder
                       (MainActivity.this, CHANNEL_ID)
                       .setSmallIcon(R.drawable.ic_stat_name)
                       .setContentTitle("My notification")
                       .setContentText("Hello World!")
                       .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                       // Set the intent that will fire when the user taps the notification
                       .setContentIntent(pendingIntent)
                       .setAutoCancel(true);

               NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
               notificationManagerCompat.notify(200,builder.build());

           }

       });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel( CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}