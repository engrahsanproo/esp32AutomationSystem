package com.example.commercialapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    Button monitor;
    Button control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        monitor = findViewById(R.id.mMonitor);
        control = findViewById(R.id.mControl);

        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MonitorActivity.class));
            }
        });

        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AutomationActivity.class));
            }
        });

        mRef.child("/Alarm").addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot ) {
                int value1 = dataSnapshot.getValue(int.class);
                if (value1 == 1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
                        NotificationChannel channel =
                                new NotificationChannel("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);

                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                    }

                    NotificationCompat.Builder builder = new NotificationCompat.Builder( MainActivity.this , "MyNotification")
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .setAutoCancel(true)
                            .setContentTitle("Security Notification")
                            .setContentText("A movement has been detected in the premises.");

                    NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
                    manager.notify(0, builder.build());
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

    }
}