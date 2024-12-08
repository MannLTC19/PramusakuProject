package com.example.pramusaku;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pramusaku.Tools.CryptographyActivity;
import com.example.pramusaku.Tools.Crytography.MainCodeActivity;
import com.example.pramusaku.Tools.NationalDataActivity;
import com.example.pramusaku.Tools.ScoutCodeActivity;
import com.example.pramusaku.Tools.ScoutingActivity;

public class ToolsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        View box1 = view.findViewById(R.id.box_1);
        View box2 = view.findViewById(R.id.box_2);
        View box3 = view.findViewById(R.id.box_3);
        View box4 = view.findViewById(R.id.box_4);


        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(requireActivity(), ScoutCodeActivity.class);
                startActivity(intent1);
            }
        });

        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(requireActivity(), NationalDataActivity.class);
                startActivity(intent2);
            }
        });

        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(requireActivity(), CryptographyActivity.class);
                startActivity(intent3);
            }
        });

        box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(requireActivity(), ScoutingActivity.class);
                startActivity(intent4);
            }
        });

        return view;
    }

    private void setBoxClickListener(View box, String message) {
        box.setOnClickListener(v -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}