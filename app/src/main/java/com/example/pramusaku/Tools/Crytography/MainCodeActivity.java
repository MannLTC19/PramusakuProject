package com.example.pramusaku.Tools.Crytography;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pramusaku.AboutUsFragment;
import com.example.pramusaku.CertificatesFragment;
import com.example.pramusaku.HomeFragment;
import com.example.pramusaku.ProfileFragment;
import com.example.pramusaku.R;
import com.example.pramusaku.ToolsFragment;
import com.example.pramusaku.databinding.ActivityMainBinding;

public class MainCodeActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        binding.bottomNavigationBar.setBackground(null);

        binding.bottomNavigationBar.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.homes) {
                replaceFragment(new HomeFragment());
            } else if (id == R.id.back) {
                replaceFragment(new ToolsFragment());
            } else {
                return false; // Unhandled case, return false
            }

            return true; // Menu item handled
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    }