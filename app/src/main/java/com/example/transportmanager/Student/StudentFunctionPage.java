package com.example.transportmanager.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.MainActivity;
import com.example.transportmanager.R;

public class StudentFunctionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_function_page2);
    }

    public void QrCode(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isEligible = sharedPreferences.getBoolean("Eligibility", false); // Replace "key" with the key used while storing data, and provide a default value if the key is not found

        if(isEligible==false)
        {
            Toast.makeText(this, "Sorry! You are not eligible...", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(StudentFunctionPage.this,ShowQrCodePage.class));
    }

    public void routesInfo(View view) {
        startActivity(new Intent(this,StudentRouteManagerPage.class));
    }

    public void chatForum(View view) {
        Intent intent = new Intent(StudentFunctionPage.this,ChatForumPage.class);
//        intent.putExtra("Username",getIntent().getStringExtra("Username"));
//        intent.putExtra("Username","ali");
        startActivity(intent);
    }

    public void logout(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}