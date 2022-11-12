package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class NewReaderActivity extends AppCompatActivity {

    String title,link,titleType,linkType;
    WebView webView;
    Button back;
    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reader);

        webView = findViewById(R.id.webView);
        back = findViewById(R.id.backNewsButton);
        txtTitle = findViewById(R.id.txtTitle);

        Bundle extra = getIntent().getExtras();

        title = extra.getString("YourTitle");
        link = extra.getString("YourLink");
        linkType = extra.getString("YourLinkxyz");

        Log.d("==tagz==",""+titleType);
        Log.d("==tagz==",""+linkType);

        txtTitle.setText(title);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewReaderActivity.this,NewsActivity.class);
                intent.putExtra("YourLinkxy",linkType);
                startActivity(intent);

            }
        });
    }
}