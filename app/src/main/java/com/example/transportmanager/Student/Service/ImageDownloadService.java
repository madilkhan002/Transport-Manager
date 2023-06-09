package com.example.transportmanager.Student.Service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageDownloadService extends IntentService {

    private static final String ACTION_DOWNLOAD_IMAGE = "com.example.transportmanager.action.DOWNLOAD_IMAGE";
    private static final String EXTRA_IMAGE_PATH = "com.example.transportmanager.extra.IMAGE_PATH";
    private static final String EXTRA_IMAGE_VIEW_ID = "com.example.transportmanager.extra.IMAGE_VIEW_ID";
    private static final String EXTRA_STORAGE_REFERENCE = "com.example.transportmanager.extra.STORAGE_REFERENCE";

    public ImageDownloadService() {
        super("ImageDownloadService");
    }

    public static void startImageDownload(Context context, String imagePath, int imageViewId, StorageReference storageRef) {
        Intent intent = new Intent(context, ImageDownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_IMAGE);
        intent.putExtra(EXTRA_IMAGE_PATH, imagePath);
        intent.putExtra(EXTRA_IMAGE_VIEW_ID, imageViewId);
        intent.putExtra(EXTRA_STORAGE_REFERENCE, storageRef.toString()); // Pass the storage reference as a string
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD_IMAGE.equals(action)) {
                String imagePath = intent.getStringExtra(EXTRA_IMAGE_PATH);
                int imageViewId = intent.getIntExtra(EXTRA_IMAGE_VIEW_ID, 0);
                String storageRefString = intent.getStringExtra(EXTRA_STORAGE_REFERENCE);
                StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(storageRefString);

                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setId(imageViewId);
                downloadAndSetImage(imagePath, imageView, storageRef);
            }
        }
    }

    public static void downloadAndSetImage(String imagePath, ImageView imageView, StorageReference storageRef) {
        // Download the image from Firebase Storage
        StorageReference imageRef = storageRef.child(imagePath);
        final long MAX_IMAGE_SIZE = 1024 * 1024; // Maximum image size in bytes (change as needed)

        imageRef.getBytes(MAX_IMAGE_SIZE).addOnSuccessListener(bytes -> {
            // Image downloaded successfully
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            // Set the bitmap in the ImageView
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
            }
        }).addOnFailureListener(e -> {
            // Image download failed
        });
    }
}