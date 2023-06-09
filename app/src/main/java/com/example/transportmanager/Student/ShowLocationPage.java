package com.example.transportmanager.Student;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.transportmanager.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class ShowLocationPage extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String DIRECTION_API_KEY = "YOUR_DIRECTION_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location_page);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        LatLng currentLocation = new LatLng(latitude, longitude);
                        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));

                        LatLng destinationLocation = new LatLng(31.60123427421647, 73.03576649924102);
                        googleMap.addMarker(new MarkerOptions().position(destinationLocation).title("Destination"));

                        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                        boundsBuilder.include(currentLocation);
                        boundsBuilder.include(destinationLocation);
                        LatLngBounds bounds = boundsBuilder.build();

                        int padding = 50;

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

                        fetchPathAndDisplay(currentLocation, destinationLocation);
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void fetchPathAndDisplay(LatLng origin, LatLng destination) {
        String url = getDirectionsUrl(origin, destination);
        FetchPathTask fetchPathTask = new FetchPathTask();
        fetchPathTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin, LatLng destination) {
        String originStr = "origin=" + origin.latitude + "," + origin.longitude;
        String destinationStr = "destination=" + destination.latitude + "," + destination.longitude;
        String key = "key=" + DIRECTION_API_KEY;
        String parameters = originStr + "&" + destinationStr + "&" + key;
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;
        return url;
    }

    private class FetchPathTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                HttpHandler httpHandler = new HttpHandler();
                response = httpHandler.makeServiceCall(urls[0]);
            } catch (Exception e) {
                Log.e("FetchPathTask", "Error: " + e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.isEmpty()) {
                List<LatLng> points = decodePolyline(response);
                drawPathOnMap(points);
            }
        }

        private List<LatLng> decodePolyline(String encoded) {
            List<LatLng> poly = new ArrayList<>();
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;
            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    if (index >= len) {
                        break; // Check if index exceeds the length of the string
                    }
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                if (index >= len) {
                    break; // Check if index exceeds the length of the string
                }
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;
                shift = 0;
                result = 0;
                do {
                    if (index >= len) {
                        break; // Check if index exceeds the length of the string
                    }
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                if (index >= len) {
                    break; // Check if index exceeds the length of the string
                }
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                double latDouble = lat * 1e-5;
                double lngDouble = lng * 1e-5;
                LatLng point = new LatLng(latDouble, lngDouble);
                poly.add(point);
            }
            return poly;
        }


        private void drawPathOnMap(List<LatLng> points) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(points)
                    .width(5)
                    .color(ContextCompat.getColor(ShowLocationPage.this, R.color.purple_200));
            Polyline polyline = googleMap.addPolyline(polylineOptions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
                onMapReady(googleMap);
            } else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
