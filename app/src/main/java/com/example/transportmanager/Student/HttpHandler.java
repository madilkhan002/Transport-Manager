package com.example.transportmanager.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {

    public String makeServiceCall(String urlString) {
        String response = null;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int statusCode = connection.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the input stream
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                // Close the input stream and disconnect the connection
                reader.close();
                inputStream.close();
            }

            // Disconnect the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

}
