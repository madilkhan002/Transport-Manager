package com.example.transportmanager.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.MainActivity;
import com.example.transportmanager.R;

public class CrudMenuPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_menu_page);

    }

    public void showStudents(View view) {
        startActivity(new Intent(CrudMenuPage.this,ShowAllStudents.class));
    }

    public void addStudents(View view) {
        startActivity(new Intent(CrudMenuPage.this,AddStudentPage.class));
    }

    public void manageRoute(View view) {
        startActivity(new Intent(CrudMenuPage.this,ChangeRouteImageMainActivity.class));
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}