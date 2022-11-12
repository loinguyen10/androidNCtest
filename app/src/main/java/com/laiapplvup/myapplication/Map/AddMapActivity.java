package com.laiapplvup.myapplication.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.laiapplvup.myapplication.MapActivity;
import com.laiapplvup.myapplication.R;

public class AddMapActivity extends AppCompatActivity {

    EditText txtAddMapName,txtAddMapLocation;
    Button add;
    MapDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_map);

        txtAddMapName = findViewById(R.id.txtAddMapName);
        txtAddMapLocation = findViewById(R.id.txtAddMapLocation);
        add = findViewById(R.id.addMap);

        dao = new MapDAO(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = txtAddMapName.getText().toString();
                String viTri = txtAddMapLocation.getText().toString().trim();

                if(ten.length() > 10){
                    Toast.makeText(AddMapActivity.this,"Tên phải trên 10 ký tự",Toast.LENGTH_SHORT).show();
                }else{
                    MapModel x = new MapModel(ten, viTri);
                    dao.add(x);
                    startActivity(new Intent(AddMapActivity.this, MapActivity.class));
                    finish();
                }
            }
        });
    }
}