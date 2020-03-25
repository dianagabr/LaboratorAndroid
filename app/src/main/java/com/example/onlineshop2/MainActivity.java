package com.example.onlineshop2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener {

    Toolbar toolbar;
    ListView listView;
    private static String LOG_TAG;
    TextView txtView;
    int[] images = {R.drawable.levis, R.drawable.ck, R.drawable.ellesse};
    String[] names = {
            "Levis sweatshirt",
            "Calvin Klein jeans",
            "Ellesse jacket"
    };

    String[] prices = {"300 LEI", "419 LEI", "350 LEI"};


    ArrayList<String> Wishlist = new ArrayList<>();
    ArrayList<String> WishlistPrices = new ArrayList<>();
    ArrayList<Integer> WishlistImages = new ArrayList<>();

    ArrayList<String> ShoppingCart= new ArrayList<>();
    ArrayList<String> SCPrices = new ArrayList<>();
    ArrayList<Integer> SCImages = new ArrayList<>();

    String text;

    Boolean isLogged = false;
    String username = "Diana";
    String password = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null) {
            text = savedInstanceState.getString("txt");
        }

        //set the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        txtView = findViewById(R.id.textView);
        listView = findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter(this, names, prices, images);
        listView.setAdapter(myAdapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtView.setText(names[position]);
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.account :
                if(isLogged == Boolean.FALSE) {
                    openDialog();
                }
                return true;
            case R.id.wish:
                if(isLogged == Boolean.TRUE) {
                    openWishlistActivity();
                }
                else{
                    Toast.makeText(this, "PLEASE LOGIN", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.shoppingCart:
                if(isLogged == Boolean.TRUE) {
                    openShoppingCartActivity();
                }
                else{
                    Toast.makeText(this, "PLEASE LOGIN", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.internalstorage:
                startActivity(new Intent(this, InternalStorage.class));
            case R.id.sensors:
                startActivity(new Intent(this, SensorActivity.class));
            case R.id.location:
                startActivity(new Intent(this, LocationActivity.class));
            default:  return super.onOptionsItemSelected(item);

        }

    }

    public void openShoppingCartActivity() {
        Intent intent = new Intent(this, ShoppingCart.class);
        intent.putExtra("Names", Wishlist);
        intent.putExtra("Prices", WishlistPrices);
        intent.putExtra("Images", WishlistImages);
        startActivity(intent);
    }

    public void openWishlistActivity() {
        Intent intent = new Intent(this, Wishlist.class);
        intent.putExtra("Names", Wishlist);
        intent.putExtra("Prices", WishlistPrices);
        intent.putExtra("Images", WishlistImages);
        startActivity(intent);
    }

    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "dialog");

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.floating_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.basket:
                ShoppingCart.add(names[info.position]);
                SCPrices.add(prices[info.position]);
                SCImages.add(images[info.position]);
                return true;
            case R.id.wishlist:
                Wishlist.add(names[info.position]);
                WishlistPrices.add(prices[info.position]);
                WishlistImages.add(images[info.position]);
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, names[info.position]);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "OnStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "OnRestart");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("txt", txtView.getText().toString());
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        txtView.setText(savedInstanceState.getString("txt"));
    }

    @Override
    public void verify(String username, String password) {
        if (username.equals(this.username )&& password.equals(this.password)){
            isLogged = true;
            Toast.makeText(this, isLogged.toString(), Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this,"Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}



