package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.laiapplvup.myapplication.Music.FMusic;
import com.laiapplvup.myapplication.Music.FMusicDAO;
import com.laiapplvup.myapplication.Music.FMusicDB;
import com.laiapplvup.myapplication.Music.MusicDTO;
import com.laiapplvup.myapplication.Music.PlayMusicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicActivity extends AppCompatActivity {

    ListView list;
    TextView song, time;
    ImageButton prev, startOrPause, next, play_random_music;
    private ArrayList<MusicDTO> songArrayList;
    Uri uri;
    int index;

    FMusicDB fMusicDB;
    FMusicDAO fMusicDAO;

    public static String Music_Action = "MUSIC_ACTION";

    public String TAG = "ZZZZZZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        anhXa();
        initPlayList( );

        prev.setOnClickListener(v -> {
            prevMusic( );
        });

        startOrPause.setOnClickListener(v -> {
            startOrPauseMusic( );
        });

        next.setOnClickListener(v -> {
            nextMusic( );
        });

        play_random_music.setOnClickListener(v -> {
            randomMusic( );
        });
    }

    void randomMusic() {
        index = new Random( ).nextInt(songArrayList.size( ) - 12) + 12;
        Log.e(TAG, "randomMusic: " + index);
        MusicDTO musicDTO = songArrayList.get(index);

        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra("type", 0);
        intent.putExtra("uri", musicDTO.file_path);
        this.startService(intent);

        setTextMusic( );
    }

    void nextMusic() {
        index += 1;
        MusicDTO musicDTO = songArrayList.get(index);

        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra("type", 0);
        intent.putExtra("uri", musicDTO.file_path);
        intent.putExtra("index", index);
        this.startService(intent);

        setTextMusic( );
    }

    void startOrPauseMusic() {
        Log.e(TAG, "startOrPauseMusic: " + index);
        MusicDTO musicDTO = songArrayList.get(index);
        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra("type", 1);
        intent.putExtra("uri", musicDTO.file_path);
        intent.putExtra("index", index);
        this.startService(intent);

    }

    void prevMusic() {
        index -= 1;
        MusicDTO musicDTO = songArrayList.get(index);

        Intent intent = new Intent(this, PlayMusicService.class);
        intent.putExtra("type", 0);
        intent.putExtra("uri", musicDTO.file_path);
        intent.putExtra("index", index);
        this.startService(intent);

        setTextMusic( );
    }

    private void anhXa() {
        list = findViewById(R.id.list);
        song = findViewById(R.id.song);
        prev = findViewById(R.id.prev);
        startOrPause = findViewById(R.id.startOrPause);
        next = findViewById(R.id.next);
        time = findViewById(R.id.time);
        play_random_music = findViewById(R.id.play_random_music);
        songArrayList = new ArrayList<>( );
        fMusicDB = Room.databaseBuilder(this, FMusicDB.class, "fmusic.db")
                .allowMainThreadQueries( ).build( );
        fMusicDAO = fMusicDB.getDAO( );
        IntentFilter intentFilter = new IntentFilter(Music_Action);
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadCast, intentFilter);
    }

    void initPlayList() {
        getSongList( );
        ArrayAdapter<MusicDTO> adapter = new ArrayAdapter<MusicDTO>(this, android.R.layout.simple_list_item_1, songArrayList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicDTO musicDTO = songArrayList.get(position);
                index = position;
                Log.e(TAG, "onItemClick: " + index);
                Log.e(TAG, "onItemClick: " + uri);

                Intent intent = new Intent(MusicActivity.this, PlayMusicService.class);
                intent.putExtra("type", 0);
                intent.putExtra("uri", musicDTO.file_path);
                intent.putExtra("index", index);
                startService(intent);
                setTextMusic( );
            }
        });
    }

    private boolean checkFMusic(String name) {
        List<FMusic> list = new ArrayList<>( );
        for (FMusic x : list) {
            if (x.getName( ).equals(name)) {
                return false;
            }
        }
        return true;

    }

    private void setTextMusic() {
        MusicDTO musicDTO = songArrayList.get(index);
        song.setText(musicDTO.name);
        Log.e(TAG, "setTextMusic: " + index);
    }

    public void getSongList() {

        ContentResolver contentResolver = getContentResolver( );
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = contentResolver.query(musicUri, null, null, null, null, null);


        if (musicCursor != null && musicCursor.moveToFirst( )) {

            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int file_path_Column = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                MusicDTO musicDTO = new MusicDTO( );
                String thisTitle = musicCursor.getString(titleColumn);

                musicDTO.name = thisTitle;
                musicDTO.file_path = musicCursor.getString(file_path_Column);

                songArrayList.add(musicDTO);


            }
            while (musicCursor.moveToNext( ));

            musicCursor.close( );
        }
    }

    BroadcastReceiver myBroadCast = new BroadcastReceiver( ) {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getStringExtra("action");
            switch (action) {
                case "UIPlay": {
                    int playOrPause = intent.getIntExtra("playOrPause", 0);
                    if (playOrPause == 0) {
                        startOrPause.setImageResource(R.drawable.pause);
                    } else if (playOrPause == 1) {
                        startOrPause.setImageResource(R.drawable.play);
                    }
                    break;
                }
                case "setTime": {
                    String setTime = intent.getStringExtra("time");
                    time.setText(setTime);
                    break;
                }
                case "Them/Xoa": {
                    int trangThai = intent.getIntExtra("trangThai", -1);
                    if (trangThai == 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show( );
                        break;
                    }
                    if (trangThai == 1) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show( );
                        break;
                    }
                }
            }
        }
    };
}