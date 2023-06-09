package com.example.transportmanager.Student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatForumPage extends AppCompatActivity {
    String username;
    TextView _Message;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_forum_page);

        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Chat");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("Username", "");
        _Message = findViewById(R.id.edttxt_chat_msg1);
        recyclerView = findViewById(R.id.recyclerView_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MessageModel> allMessages =  new ArrayList<>();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String messageId = messageSnapshot.getKey();
                    String username = messageSnapshot.child("username").getValue(String.class);
                    String message = messageSnapshot.child("message").getValue(String.class);
                    MessageModel messageModel = new MessageModel(username,message,messageId);
                    allMessages.add(messageModel);
                }
                ShowChatAdapter adapter = new ShowChatAdapter(allMessages, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.setupMessageListener();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred, handle it appropriately
            }
        });
    }

    public void sendMessage(View view) {
        String message = _Message.getText().toString();
        if(message.length() == 0)
        {
            Toast.makeText(this, "Can't Send Empty Messages...", Toast.LENGTH_SHORT).show();
            return;
        }
        // Generate a unique key for the new message
        String messageId = reference.child("Chat").push().getKey();
        // Create a new child node with the unique key
        MessageModel messageModel = new MessageModel(username,message,messageId);
        assert messageId != null;
        reference.child(messageId).setValue(messageModel);
        Toast.makeText(this, "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
    }

}