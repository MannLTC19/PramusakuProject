package com.example.pramusaku;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToolsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        // Initialize UI components
        View box1 = view.findViewById(R.id.box_1);
        View box2 = view.findViewById(R.id.box_2);
        View box3 = view.findViewById(R.id.box_3);
        View box4 = view.findViewById(R.id.box_4);

        // Set click listeners for each box
        setBoxClickListener(box1, "National Scout Code of Order");
        setBoxClickListener(box2, "National Data");
        setBoxClickListener(box3, "Cryptography code-decoder");
        setBoxClickListener(box4, "Scouting Crafts");

        return view;
    }

    private void setBoxClickListener(View box, String message) {
        box.setOnClickListener(v -> {
            // Example action: Show a Toast with the category name
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}