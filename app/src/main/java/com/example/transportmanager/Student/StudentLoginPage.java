package com.example.transportmanager.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginPage extends AppCompatActivity {
    TextView Username,Password;

    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login_page);

        // Bind TextViews
        Username = findViewById(R.id.txtuser_username);
        Password = findViewById(R.id.txtuser_password);

        // DB
        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Student");
    }

    public void userLoginBtn(View view) {
        String username = Username.getText().toString();
        String password = Password.getText().toString();

        if(username.length() == 0 || password.length() == 0)
        {
            Toast.makeText(this, "Invalid input....", Toast.LENGTH_SHORT).show();
            return;
        }
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(password);

        // Check if the student with the given username and password exists
        reference.child("students").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Student exists in the database
                    Student student = dataSnapshot.getValue(Student.class);
                    if (student.getPassword().equals(password)) {
                        // Password matches
                        Toast.makeText(StudentLoginPage.this, "Login Success...", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(StudentLoginPage.this,StudentFunctionPage.class);

                        // Get SharedPreferences instance
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Username", student.getUsername()); // Replace "key" with your desired key and "value" with the data you want to store
                        editor.putString("Password", student.getPassword()); // Replace "key" with your desired key and "value" with the data you want to store
                        editor.putString("Route", student.getRoute()); // Replace "key" with your desired key and "value" with the data you want to store
                        editor.putBoolean("Eligibility", student.isEligilble()); // Replace "key" with your desired key and "value" with the data you want to store
                        editor.apply();

                        startActivity(intent);
                    } else {
                        // Password does not match
                        Toast.makeText(StudentLoginPage.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Student does not exist in the database
                    Toast.makeText(StudentLoginPage.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred, handle it appropriately
            }
        });


    }
}