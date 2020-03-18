package com.example.onlineshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    public static final String KEY_PREFERENCE = "switch_preference";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null) return;
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new SettingsFragment()).commit();

        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPreferences.getBoolean(SettingActivity.KEY_PREFERENCE,false);
        Toast.makeText(this, switchPref.toString(), Toast.LENGTH_SHORT).show();
    }
}
