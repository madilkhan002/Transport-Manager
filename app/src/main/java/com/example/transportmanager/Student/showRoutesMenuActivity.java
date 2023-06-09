package com.example.transportmanager.Student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.transportmanager.R;
import com.example.transportmanager.Student.Route_Fragments.ShowRouteFragment;
import com.example.transportmanager.Student.Service.ImageDownloadService;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class showRoutesMenuActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_routes_info);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://transport-manager-cc4cc.appspot.com");

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Create the fragment
        ShowRouteFragment fragment = new ShowRouteFragment();
        // Add the fragment to the activity
        fragmentManager.beginTransaction().
                add(R.id.fragment_route_1, fragment).
                setReorderingAllowed(true).
                addToBackStack("name").commit();
    }

    public void route_1(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Get the reference to the ImageView in the fragment
        ShowRouteFragment fragmentInstance = (ShowRouteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_route_1);
        ImageView imageView = fragmentInstance.imageView;

        // Call the downloadAndSetImage function with the image path
        ImageDownloadService.downloadAndSetImage("images/route1.jpg", imageView,mStorageRef);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_route_1, fragmentInstance) // Replace with the same fragment instance
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    public void route_2(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Get the reference to the ImageView in the fragment
        ShowRouteFragment fragmentInstance = (ShowRouteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_route_1);
        ImageView imageView = fragmentInstance.imageView;

        // Call the downloadAndSetImage function with the image path
        ImageDownloadService.downloadAndSetImage("images/route2.jpg", imageView,mStorageRef);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_route_1, fragmentInstance) // Replace with the same fragment instance
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    public void route_3(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Get the reference to the ImageView in the fragment
        ShowRouteFragment fragmentInstance = (ShowRouteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_route_1);
        ImageView imageView = fragmentInstance.imageView;

        // Call the downloadAndSetImage function with the image path
        ImageDownloadService.downloadAndSetImage("images/route3.jpg", imageView,mStorageRef);

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_route_1, fragmentInstance) // Replace with the same fragment instance
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }


    public void home(View view) {
        startActivity(new Intent(this,StudentFunctionPage.class));
    }
}