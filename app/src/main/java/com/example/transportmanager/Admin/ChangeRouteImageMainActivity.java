package com.example.transportmanager.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ChangeRouteImageMainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 100;
    private StorageReference mStorageRef;

    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_route_image_main);

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://transport-manager-cc4cc.appspot.com");
    }

    public void route1(View view) {
        name = "route1.jpg";
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    public void route2(View view) {
        name = "route2.jpg";
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    public void route3(View view) {
        name = "route3.jpg";
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            // Get the image URI
            Uri uri = data.getData();

            try {

                // Get the image from the URI
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Upload the image to Firebase Storage
                StorageReference imageRef = mStorageRef.child("images/" + name);
                imageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Image uploaded successfully
                                Toast.makeText(ChangeRouteImageMainActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                // Image upload failed
                                Toast.makeText(ChangeRouteImageMainActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}