package com.example.transportmanager.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.Admin.Notification.NotificationUtils;
import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.example.transportmanager.Student.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStudentPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;

    TextView Username,Password,RouteNo;
    CheckBox IsEligible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_page);

        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Student");

        // Bind TextViews
        Username = findViewById(R.id.addstudent_username);
        Password = findViewById(R.id.addstudent_password);
        RouteNo = findViewById(R.id.addstudent_routeno);
        // Bind CheckBox
        IsEligible = findViewById(R.id.addstudent_eligible);
    }

    public void addStudent(View view) {
        String username,password,routeno;
        username = Username.getText().toString();
        password = Password.getText().toString();
        routeno = RouteNo.getText().toString();
        boolean isEligible = IsEligible.isChecked();

        if(username.length() == 0 || password.length() == 0 || routeno.length() == 0)
        {
            Toast.makeText(this, "Invalid Input.....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Check if the student username exists
            reference.child("students").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Username exists in the database
                        Toast.makeText(AddStudentPage.this, "Student id already exists....", Toast.LENGTH_SHORT).show();
                    } else {
                        // Username does not exist in the database
                        // insert the student
                        Student student = new Student(username,password,routeno,isEligible);
                        reference.child("students").child(username).setValue(student);
                        NotificationUtils.showNotification(getApplicationContext(), "Notification", "Student Added Successfully");
                        Toast.makeText(AddStudentPage.this, "Student Added Successfully...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddStudentPage.this,CrudMenuPage.class));
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