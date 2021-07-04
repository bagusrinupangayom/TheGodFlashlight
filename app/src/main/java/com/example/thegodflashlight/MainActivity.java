package com.example.thegodflashlight;

// RequiresApi berguna untuk menambahkan sebuah API
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
// Berguna untuk meng-import camera access exception
import android.hardware.camera2.CameraAccessException;

// Berguna untuk meng-import camera manager
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

// Berguna untuk mengimpor Library Dexter ke dalam activity
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

//  Membuat objek untuk Image Button
    ImageButton imageButton;
    boolean state;

//  variabel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      Inisialisasi image button
        imageButton = findViewById(R.id.flBtn);

//      Untuk Asking Persmisson, di sini kita menggunakan Dexter Library
//      Untuk Dexter Library - dependencies nya kita copy dan paste kan ke build.gradle(Module:app)
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {

//          Menambahakan requires api build versi code Lollipop yang mana aplikasi menimal dijalankan di android versi lollilop
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

//              Memanggil method runFlashlight
                runFlashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

//              Membuat menampilkan pesan untuk diperlukannya izin kamera
                Toast.makeText(MainActivity.this, "Camera permission required.", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
//         check ini berguna untuk melakukan check (Dexter Library)
        }).check();
    }

//  Menambahakan requires api build versi code Lollipop yang mana aplikasi menimal dijalankan di android versi lollilop
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runFlashlight() {

//      Membuat onClickListener untuk Image Button dalam bentuk if dan else
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
//              ini bentuk bagian dari if
                if (!state)
                {
//                  CameraManager melakukan mendeteksi dan mengkoneksi ke camera devices
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
//                      Jika ketika menekan tombaol button, maka nanti senter akan menyala
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, true);
                        }
//                      Setelah menekan button, maka nanti senter akan menyala dan gambar pun berubah ke senter yang menyala
                        state = true;
                        imageButton.setImageResource(R.drawable.torch_on);
                    }
//                  Pengecualian
                    catch (CameraAccessException e)
                    {}
                }
                else
                {
//                  CameraManager melakukan mendeteksi dan mengkoneksi ke camera devices
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
//                      Jika ketika menekan tombaol button, maka nanti senter akan mati
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraId, false);
                        }
//                      Setelah menekan button, maka nanti senter akan mati dan gambar pun berubah ke senter yang tidak menyala lagi
                        state = false;
                        imageButton.setImageResource(R.drawable.torch_off);
                    }
//                  Pengecualian
                    catch (CameraAccessException e)
                    {}
                }
            }
        });
    }
}
