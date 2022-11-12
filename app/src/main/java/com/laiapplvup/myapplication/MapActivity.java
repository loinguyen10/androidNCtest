package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.laiapplvup.myapplication.Map.AddMapActivity;
import com.laiapplvup.myapplication.Map.MapDAO;
import com.laiapplvup.myapplication.Map.MapModel;
import com.laiapplvup.myapplication.News.Entry;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    FloatingActionButton add;
    ListView listView;
    List<MapModel> listMap;
    String TAG = "==tag==";
    MapDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        listView = findViewById(R.id.listMap);

        add = findViewById(R.id.addButton);

        dao = new MapDAO(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, AddMapActivity.class));
            }
        });

        listMap = (ArrayList<MapModel>) dao.start();

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return listMap.size();
            }

            class ViewHolder {
                TextView name, location;
                Button view;
            }

            @Override
            public Object getItem(int position) {
                return listMap.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder viewHolder = null;

                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map, parent, false);
                    viewHolder = new ViewHolder();
                    viewHolder.name = convertView.findViewById(R.id.txtMapName);
                    viewHolder.location = convertView.findViewById(R.id.txtMapLocation);
                    viewHolder.view = convertView.findViewById(R.id.viewMap);

                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                MapModel x = listMap.get(position);
                viewHolder.name.setText(x.getName());
                viewHolder.location.setText(x.getLocation());

                viewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String xxx = "";
//                        String yyy = "";
//                        String msg1 = "http://www.google.com/maps/search/?api=1&query=" + xxx + "%2C" + yyy;
//                        Log.d(TAG, "map: " + msg1);

                        Log.d(TAG, "map: " + x.getLocation());
                        Intent openMap = new Intent(Intent.ACTION_VIEW);
                        openMap.setData(Uri.parse("geo:"+x.getLocation()));
                        Intent mapOpen = Intent.createChooser(openMap,"Open Map");
                        startActivity(mapOpen);
                    }
                });

                return convertView;
            }
        };

        listView.setAdapter(adapter);

    }
}