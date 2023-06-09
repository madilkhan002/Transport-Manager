package com.example.transportmanager.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.Admin.Notification.NotificationUtils;
import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentDeleteUpdatePage extends AppCompatActivity {
    EditText Password,RouteNo;
    TextView Username;
    String _username;
    CheckBox IsEligible;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_delete_update_page);

        // Bind TextViews
        Username = findViewById(R.id.txt_studelup_username);
        Password = findViewById(R.id.edttxt_studelup_password);
        RouteNo = findViewById(R.id.edttxt_studelup_route);
        // Bind CheckBox
        IsEligible = findViewById(R.id.edttxt_studelup_eligibility);

        // Set Initial Values
        Intent intent = getIntent();
        Username.setText("Id : " + intent.getStringExtra("Username"));
        Password.setText(intent.getStringExtra("Password"));
        RouteNo.setText(intent.getStringExtra("Route"));
        IsEligible.setChecked(intent.getBooleanExtra("Eligibility",false));
        _username = intent.getStringExtra("Username").toString();


        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Student");
    }

    public void deleteStudent(View view) {
        reference.child("students").child(_username).removeValue();
        Toast.makeText(this, "Student Deleted Successfully...", Toast.LENGTH_SHORT).show();
        NotificationUtils.showNotification(getApplicationContext(), "Notification", "Student Deleted Successfully");
        startActivity(new Intent(StudentDeleteUpdatePage.this,ShowAllStudents.class));
    }

    public void UpdateStudent(View view) {
        String password = Password.getText().toString();
        String route = RouteNo.getText().toString();
        Boolean isEligible = IsEligible.isChecked();
        if(password.length() == 0 || route.length() == 0)
        {
            Toast.makeText(this, "Invalid input....", Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String, Object> updatedData = new HashMap<>();
        updatedData.put("username", _username);
        updatedData.put("password", password);
        updatedData.put("route", route);
        updatedData.put("eligilble", isEligible);

        reference.child("students").child(_username).updateChildren(updatedData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Error occurred during the update operation
                    Toast.makeText(StudentDeleteUpdatePage.this, "Update Failed....", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentDeleteUpdatePage.this,ShowAllStudents.class));
                } else {
                    // Update operation successful
                    NotificationUtils.showNotification(getApplicationContext(), "Notification", "Student Updated successful");
                    Toast.makeText(StudentDeleteUpdatePage.this, "Update successful....", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StudentDeleteUpdatePage.this,ShowAllStudents.class));
                }
            }
        });

    }
}