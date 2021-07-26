package com.example.commercialapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AutomationActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    Button menu;
    Switch app1;
    Switch app2;
    Switch app3;
    Switch app4;
    Switch choice;
    Switch fan;
    Switch lights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation);

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        app1 = findViewById(R.id.s1);
        app2 = findViewById(R.id.s2);
        app3 = findViewById(R.id.s3);
        app4 = findViewById(R.id.s4);
        choice = findViewById(R.id.s5);
        fan = findViewById(R.id.s6);
        lights = findViewById(R.id.s7);
        menu = findViewById(R.id.bMenu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AutomationActivity.this, MainActivity.class));
            }
        });

        mRef.child("Toggle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value1 = dataSnapshot.getValue(int.class);
                if(value1 == 1){
                    choice.setChecked(true);
                    fan.setVisibility(View.INVISIBLE);
                    lights.setVisibility(View.INVISIBLE);
                }else{
                    choice.setChecked(false);
                    fan.setVisibility(View.VISIBLE);
                    lights.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/App1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value2 = dataSnapshot.getValue(int.class);
                if(value2 == 1){
                    app1.setChecked(true);
                }else{
                    app1.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/App2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value3 = dataSnapshot.getValue(int.class);
                if(value3 == 1){
                    app2.setChecked(true);
                }else{
                    app2.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/App3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value4 = dataSnapshot.getValue(int.class);
                if(value4 == 1){
                    app3.setChecked(true);
                }else{
                    app3.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/App4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value5 = dataSnapshot.getValue(int.class);
                if(value5 == 1){
                    app4.setChecked(true);
                }else{
                    app4.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value6 = dataSnapshot.getValue(int.class);
                if(value6 == 1){
                    fan.setChecked(true);
                }else{
                    fan.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mRef.child("Control/Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value7 = dataSnapshot.getValue(int.class);
                if(value7 == 1){
                    lights.setChecked(true);
                }else{
                    lights.setChecked(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice.isChecked()){
                    mRef = mDatabase.getReference("Toggle");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Toggle");
                    mRef.setValue(0);
                }
            }
        });

        app1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app1.isChecked()){
                    mRef = mDatabase.getReference("Control/App1");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/App1");
                    mRef.setValue(0);
                }
            }
        });

        app2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app2.isChecked()){
                    mRef = mDatabase.getReference("Control/App2");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/App2");
                    mRef.setValue(0);
                }
            }
        });

        app3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app3.isChecked()){
                    mRef = mDatabase.getReference("Control/App3");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/App3");
                    mRef.setValue(0);
                }
            }
        });

        app4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(app4.isChecked()){
                    mRef = mDatabase.getReference("Control/App4");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/App4");
                    mRef.setValue(0);
                }
            }
        });

        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fan.isChecked()){
                    mRef = mDatabase.getReference("Control/Fan");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/Fan");
                    mRef.setValue(0);
                }
            }
        });

        lights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lights.isChecked()){
                    mRef = mDatabase.getReference("Control/Light");
                    mRef.setValue(1);
                }else{
                    mRef = mDatabase.getReference("Control/Light");
                    mRef.setValue(0);
                }
            }
        });
    }
}