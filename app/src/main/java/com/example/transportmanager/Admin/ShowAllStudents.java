package com.example.transportmanager.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.transportmanager.FireabaseInfo.FirebaseCon;
import com.example.transportmanager.R;
import com.example.transportmanager.Student.Student;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowAllStudents extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<Student> allStudents;
    RecyclerView recyclerView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_students);

        recyclerView = findViewById(R.id.showstudent_listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        allStudents= new ArrayList<>();

        database = FirebaseDatabase.getInstance(new FirebaseCon().getUrl());
        reference = database.getReference("Student");

        // Retrieve all students from the database
        reference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through all student snapshots
                    for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                        Student student = studentSnapshot.getValue(Student.class);
                        // Perform your desired actions with each student
                        allStudents.add(student);
                    }
                } else {
                    // No students exist in the database

                }

                // Now We have the data of all Students
                // Now Pass data to adapter
                ShowStudentAdapter adapter = new ShowStudentAdapter(allStudents, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                adapter.setOnItemClickListener(new ShowStudentAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Student student) {
                        // Handle the click event here
                        // You can access the clicked student object and perform the desired actions

                        Intent intent = new Intent(ShowAllStudents.this,StudentDeleteUpdatePage.class);
                        intent.putExtra("Username",student.getUsername());
                        intent.putExtra("Password",student.getPassword());
                        intent.putExtra("Route",student.getRoute());
                        intent.putExtra("Eligibility",student.isEligilble());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred, handle it appropriately
            }
        });
    }
}