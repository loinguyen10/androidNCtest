package com.laiapplvup.myapplication.Music;

import static com.laiapplvup.myapplication.MusicActivity.Music_Action;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PlayMusicService extends Service {
    MediaPlayer myMusicPlayer;
    Uri uri;
    int Type;
    int playOrPause = 0;
    FMusicDB fMusicDB;
    FMusicDAO fMusicDAO;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fMusicDB = Room.databaseBuilder(getApplicationContext( ), FMusicDB.class, "fmusic.db")
                .allowMainThreadQueries( ).build( );
        fMusicDAO = fMusicDB.getDAO( );
        Log.e("ZZZZ", "Play music service" );
        Type = intent.getIntExtra("type",-1);
        switch (Type){
            case 0: {
                uri = Uri.parse(intent.getStringExtra("uri"));
                if (myMusicPlayer != null) {
                    myMusicPlayer.stop( );
                    myMusicPlayer.release( );
                    myMusicPlayer = null;
                }
                playOrPause = startOrPauseMusic(uri);
                Intent random = new Intent(  );
                random.setAction(Music_Action);
                random.putExtra("action","UIPlay");
                random.putExtra("playOrPause",playOrPause);
                LocalBroadcastManager.getInstance(this).sendBroadcast(random);
                break;
            }
            case 1:{
                uri = Uri.parse(intent.getStringExtra("uri"));
                playOrPause = startOrPauseMusic(uri);
                Intent checkPlay = new Intent(  );
                checkPlay.setAction(Music_Action);
                checkPlay.putExtra("action","UIPlay");
                checkPlay.putExtra("playOrPause",playOrPause);
                LocalBroadcastManager.getInstance(this).sendBroadcast(checkPlay);
                break;
            }
            case 2:{
                String name = intent.getStringExtra("name");
                int user = intent.getIntExtra("user",0);
                FMusic fMusic = new FMusic(name,user);
                fMusicDAO.add(fMusic);
                break;
            }
            case 3:{
                String name = intent.getStringExtra("name");
                int user = intent.getIntExtra("user",0);
                FMusic fMusic = new FMusic(name,user);
                fMusicDAO.delete(fMusic);
                break;
            }
        }
        return START_NOT_STICKY;
    }

    int startOrPauseMusic(Uri uri) {
        if (myMusicPlayer == null) {
            myMusicPlayer = MediaPlayer.create(getApplicationContext( ), uri);
            myMusicPlayer.start( );
            Toast.makeText(this, "chay service", Toast.LENGTH_SHORT).show();

            runnable.run();
            myMusicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener( ) {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    myMusicPlayer.release( );
                    myMusicPlayer = null;
                }
            });
            return 0;
        } else {
            if (myMusicPlayer.isPlaying( )) {
                myMusicPlayer.pause( );
                return 1;
            } else {
                try {
                    if (myMusicPlayer.isLooping( ))
                        myMusicPlayer.prepare( );
                } catch (IOException e) {
                    e.printStackTrace( );
                }
                myMusicPlayer.start( );
                return 0;
            }
        }
    }

    private String formatDuration(long duration) {
        long minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES);

        return String.format("%02d:%02d", minutes, seconds);
    }

    Handler handler = new Handler( );
    Runnable runnable = new Runnable( ) {
        @Override
        public void run() {
            if (myMusicPlayer != null) {
                int totalTime = myMusicPlayer.getDuration( );
                int currentTime = myMusicPlayer.getCurrentPosition( );
                handler.postDelayed(this, 100);

                Intent setTime = new Intent(  );
                setTime.setAction(Music_Action);
                setTime.putExtra("action","setTime");
                setTime.putExtra("time",formatDuration(currentTime) + "/" + formatDuration(totalTime));
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(setTime);
            }
        }
    };
}
