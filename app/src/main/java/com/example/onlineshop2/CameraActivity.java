package com.example.onlineshop2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Toolbar toolbar;
    Button button;
    SurfaceView surfaceView;
    static Camera camera = null;
    SurfaceHolder surfaceHolder;
    Camera.PictureCallback pictureCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Camera");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        surfaceView = findViewById(R.id.surfaceView);
        button = findViewById(R.id.btncapture);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, pictureCallback);
            }
        });

        pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                Bitmap cbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),null, true);
                String pathFileName = currentDateFormat();
                storePhotoToStorage(cbmp, pathFileName);

                Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_LONG).show();
                CameraActivity.this.camera.startPreview();
            }
        };

    }

    private void storePhotoToStorage(Bitmap cbmp, String pathFileName) {
        File outputFile = new File(Environment.getExternalStorageDirectory(), "/DCIM/" +
                "photo_" + pathFileName + ".jpg");
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            cbmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String currentDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_MM_ss");
        String currentTime = dateFormat.format(new Date());
        return currentTime;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
           camera = Camera.open();

           Camera.Parameters parameters;
           parameters = camera.getParameters();
           parameters.setPreviewFrameRate(20);
           parameters.setPreviewSize(352,288);
           camera.setParameters(parameters);
           camera.setDisplayOrientation(90);
           try{
               camera.setPreviewDisplay(surfaceHolder);
               camera.startPreview();
           } catch (IOException e) {
               e.printStackTrace();
           }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }




}
