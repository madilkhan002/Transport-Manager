package com.example.transportmanager.Student.Route_Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.transportmanager.R;

public class ShowRouteFragment extends Fragment {
    public ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.show_route_custom_layout, container, false);
        imageView = view.findViewById(R.id.img_view_route);
        imageView.setImageResource(R.drawable.route2);
        return view;
    }

}
