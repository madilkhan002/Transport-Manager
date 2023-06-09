package com.example.transportmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.transportmanager.Admin.AdminLoginPage;
import com.example.transportmanager.Student.StudentLoginPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void adminBtnClick(View view) {
        startActivity(new Intent(this, AdminLoginPage.class));
    }

    public void userBtnClick(View view) {
        startActivity(new Intent(this, StudentLoginPage.class));
    }
}