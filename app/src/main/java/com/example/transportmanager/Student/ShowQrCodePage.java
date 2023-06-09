package com.example.transportmanager.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.transportmanager.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class ShowQrCodePage extends AppCompatActivity {

    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qr_code_page);

        qrCodeImageView = findViewById(R.id.qr_code_image_view);

        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String data = sharedPreferences.getString("Username", "");

        new GenerateQRCodeTask().execute(data);
    }

    private class GenerateQRCodeTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String data = strings[0];

            BitMatrix bitMatrix;
            try {
                bitMatrix = new MultiFormatWriter().encode(
                        data, BarcodeFormat.QR_CODE, 500, 500);
            } catch (WriterException e) {
                e.printStackTrace();
                return null;
            }

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }

            Bitmap qrCodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            qrCodeBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return qrCodeBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap qrCodeBitmap) {
            if (qrCodeBitmap != null) {
                qrCodeImageView.setImageBitmap(qrCodeBitmap);
            } else {
                // Handle the case when QR code generation fails
            }
        }
    }
}



