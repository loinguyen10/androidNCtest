package com.laiapplvup.myapplication.Sound;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.laiapplvup.myapplication.R;

public class SoundService extends IntentService {
    MediaPlayer player= null;
    String TAG = "PlayMusic-zzzzzzz";
    int ID_SV;

    public SoundService() {
        super("SoundService");
        Log.d(TAG,"Gọi hàm khởi tạo ....");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, "Gọi hàm onStartCommand id service = " + startId);
        ID_SV = startId;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            Log.d(TAG, "Gọi hàm onHandleIntent - id = " + ID_SV);
            if (player == null) {
                player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
                player.start();
            }

            Toast.makeText(this, "onHandleIntent", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onHandleIntent: " + player.toString());

            while (player != null && player.isPlaying()){
                // đang chạy nhac thì service chưa dừng
                try {
                    wait(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopSelf(ID_SV);
        }
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "Gọi hàm onDestroy - id = " + ID_SV);
        if (player != null) {
            Log.d(TAG, "player != null ");
            player.stop();
            player.release();// giải phóng playler
            player = null;
            Toast.makeText(this, "Hết nhạc rồi", Toast.LENGTH_SHORT).show();
        }

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
