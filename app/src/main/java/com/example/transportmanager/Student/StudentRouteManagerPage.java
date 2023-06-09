package com.example.transportmanager.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.R;

public class StudentRouteManagerPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_route_manager_page);
    }

    public void routes(View view) {
        startActivity(new Intent(this, showRoutesMenuActivity.class));
    }

    public void currentLocation(View view) {
        startActivity(new Intent(this,ShowLocationPage.class));
    }
}