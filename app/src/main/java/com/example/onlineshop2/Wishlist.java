package com.example.onlineshop2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

public class Wishlist extends AppCompatActivity {

    int[] images ;
    String[] names;
    String[] prices ;

    ListView listView;

   Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wishlist);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Wishlist");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        ArrayList<Integer> list = intent.getIntegerArrayListExtra("Images");
        images = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            images[i] = list.get(i);
        }
        ArrayList<String> listN = intent.getStringArrayListExtra("Names");
        names =  new String[listN.size()];
        for(int i = 0; i < listN.size(); i++) {
            names[i] = listN.get(i);
        }

        ArrayList<String> listP  =  intent.getStringArrayListExtra("Prices");
        prices =  new String[listN.size()];
        for(int i = 0; i < listP.size(); i++) {
            prices[i] = listN.get(i);
        }


        System.out.println(images);
        System.out.println(names);

        listView = findViewById(R.id.wishlistView);
        MyAdapter myAdapter = new MyAdapter(this, names, prices, images);
        listView.setAdapter(myAdapter);

    }




}
