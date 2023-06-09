package com.example.transportmanager.Student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transportmanager.Admin.ShowStudentAdapter;
import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowChatAdapter extends RecyclerView.Adapter<ShowChatAdapter.ViewHolder> {

    private final Context context;
    ArrayList<MessageModel> allmessages;

    FirebaseDatabase database;
    DatabaseReference reference;

    public ShowChatAdapter(ArrayList<MessageModel> allmessages, Context context) {
        this.allmessages = allmessages;
        this.context = context;
        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Chat");
    }


    public void setupMessageListener() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                String messageId = dataSnapshot.getKey();
                String username = dataSnapshot.child("username").getValue(String.class);
                String message = dataSnapshot.child("message").getValue(String.class);

                // Check if the message already exists in the list
                boolean isMessageExists = false;
                for (MessageModel existingMessage : allmessages) {
                    if (existingMessage.getMessageId().equals(messageId)) {
                        isMessageExists = true;
                        break;
                    }
                }

                // If the message doesn't exist, add it to the list
                if (!isMessageExists) {
                    MessageModel newMessage = new MessageModel(username,message,messageId);
                    newMessage.setMessageId(messageId);
                    allmessages.add(newMessage);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



    @NonNull
    @Override
    public ShowChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_chat_custom_layout,parent,false);
        return new ShowChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowChatAdapter.ViewHolder holder, int position) {
        holder.Username.setText(allmessages.get(position).getUsername());
        holder.Message.setText(allmessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return allmessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Username,Message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Username = itemView.findViewById(R.id.chat_txt_user);
            Message = itemView.findViewById(R.id.chat_txt_message);
        }
    }
}
