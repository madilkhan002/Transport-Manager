package com.example.transportmanager.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginPage extends AppCompatActivity {
    TextView Username,Password;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_form);

        Username = findViewById(R.id.txtadmin_username);
        Password = findViewById(R.id.txtadmin_password);

        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Admin");
    }

    public void adminLoginBtn(View view) {
        String username = Username.getText().toString();
        String password = Password.getText().toString();
        if(username.length() == 0 || password.length() == 0)
        {
            Toast.makeText(this, "input field is empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Insert new Admin
            // Admin admin = new Admin(username,password);
            // reference.child("admins").child(username).setValue(admin);
            // Toast.makeText(this, "Admin inserted Successfully....", Toast.LENGTH_SHORT).show();

            // Check if the user exists in the database
            reference.child("admins").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User exists in the database
                        Admin admin = dataSnapshot.getValue(Admin.class);
                        if (admin.getPassword().equals(password)) {
                            // Password matches
                            // Perform your desired actions here
                            Toast.makeText(AdminLoginPage.this, "Login Success...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminLoginPage.this,CrudMenuPage.class));
                        } else {
                            // Password does not match
                            // Perform your desired actions here
                            Toast.makeText(AdminLoginPage.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User does not exist in the database
                        // Perform your desired actions here
                        Toast.makeText(AdminLoginPage.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Error occurred, handle it appropriately
                }
            });


        }
    }
}