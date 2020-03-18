package com.example.onlineshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class InternalStorage extends AppCompatActivity {

    public static final String FILENAME = "example.txt";
    EditText editText;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        editText = findViewById(R.id.edit_text);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Save/Load");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void save(View v)  {
        String text = editText.getText().toString();
        FileOutputStream fos = null;

        try{
            fos = openFileOutput(FILENAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            editText.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILENAME, Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void load(View v){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            editText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
