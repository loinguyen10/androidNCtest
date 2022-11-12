package com.laiapplvup.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.laiapplvup.myapplication.Sound.SoundService;

public class MainActivity extends AppCompatActivity {

    Button loadRSS,loadMusic,loadAnime,loadMap,loadSound,loadMapNow;
    ComponentName sv_play = null;
    private FusedLocationProviderClient fusedLocationClient;
    static String TAG = "==tag==";
    EditText txtNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRSS = findViewById(R.id.loadRSS);
        loadMusic = findViewById(R.id.loadMusic);
        loadAnime = findViewById(R.id.loadAnime);
        loadMap = findViewById(R.id.loadMap);
        loadSound = findViewById(R.id.loadSound);
        loadMapNow = findViewById(R.id.loadMapNow);
        txtNow = findViewById(R.id.txtNow);

        loadRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsActivity.class));
            }
        });

        loadMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MusicActivity.class));
            }
        });

        loadAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AnimationActivity.class));
            }
        });

        loadMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapActivity.class));
            }
        });

        loadSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoundService.class);
                if(sv_play == null){
                    // chống click nhiều
                    sv_play =  startService(intent);
                }else{
                    stopService(intent);
                    sv_play  = null;
                }
            }
        });

        loadMapNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    loadNow();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadNow(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Bạn cần cấp quyền truy cập định vị", Toast.LENGTH_SHORT).show();

            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: khong lay duoc thong tin" + e.getMessage());
                        e.printStackTrace();
                    }
                })
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // lấy được tọa độ
                            Log.d(TAG, "onSuccess: Lat = " + location.getLatitude());
                            Log.d(TAG, "onSuccess: Lng = " + location.getLongitude());
                            String msg1 = "http://www.google.com/maps/search/?api=1&query=" + location.getLatitude() + "%2C" + location.getLongitude();
                            Log.d(TAG, "map: " + msg1);
                            txtNow.setText(""+msg1);

                        }else{
                            Log.d(TAG, "onSuccess: Location null"); //mở Map nên xem mở vị trí hiện tại chưa
                        }
                    }
                });
    }

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                }
                Boolean coarseLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    coarseLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION,false);
                }
                if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.

                            Log.d(TAG, "Đã cấp quyền truy cập chính xác vị trí ");

                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            Log.d(TAG, "Đã cấp quyền truy cập vị trí gần chính xác");
                        } else {
                            Log.d(TAG, "Đã từ chối cấp quyền");
                        }
                    }
            );
}