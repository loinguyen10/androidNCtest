package com.laiapplvup.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NewsActivity extends AppCompatActivity {

    String link,linkx;
    Button find;
    EditText txtType;
    ListView listBaoMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        listBaoMoi = findViewById(R.id.listBaoMoi);
        find = findViewById(R.id.findButton);
        txtType = findViewById(R.id.txtType);

//        Bundle extra = getIntent().getExtras();
//        linkx = extra.getString("YourLinkxy");
//
//        if(linkx == null){
//
//        }else{
//            txtType.setText(linkx);
//        }

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link = txtType.getText().toString();
                new ReadRss().execute(link);
            }
        });
    }

    private class ReadRss extends AsyncTask<String, Void, List<Entry>> {

        @Override
        protected void onPostExecute(List<Entry> tinTucList) {
            super.onPostExecute(tinTucList);

            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return tinTucList.size();
                }

                class ViewHolder {
                    ImageView id;
                    TextView title, date;
                }

                @Override
                public Object getItem(int position) {
                    return tinTucList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    ViewHolder viewHolder = null;

                    if (convertView == null) {
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss_feed, parent, false);
                        viewHolder = new ViewHolder();
                        viewHolder.id = convertView.findViewById(R.id.anhNew);
                        viewHolder.title = convertView.findViewById(R.id.titleText);
                        viewHolder.date = convertView.findViewById(R.id.dateText);

                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }

                    Entry tin = tinTucList.get(position);
                    new DownloadImg(viewHolder.id).execute(tin.getId());
                    viewHolder.title.setText(tin.getTitle());
                    viewHolder.date.setText("Time: " + tin.getDate());

                    return convertView;
                }
            };
            listBaoMoi.setAdapter(adapter);


            listBaoMoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String titlex = tinTucList.get(i).getTitle();
                    String linkx = tinTucList.get(i).getLink();

                    Toast.makeText(NewsActivity.this, "" + titlex, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(NewsActivity.this,NewReaderActivity.class);

                    intent.putExtra("YourTitle", titlex);
                    intent.putExtra("YourLink", linkx);
                    intent.putExtra("YourLinkxyz", link);

                    startActivity(intent);
                }
            });
        }

        @Override
        protected List<Entry> doInBackground(String... strings) {
            NewsLoader loader = new NewsLoader();
            List<Entry> list = new ArrayList<>();

            String urlRss = strings[0];

            try {
                URL url = new URL(urlRss);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream inputStream = urlConnection.getInputStream();
                    list = loader.getTinTucList(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    private class DownloadImg extends AsyncTask<String, Void, Bitmap> {

        ImageView img;
        InputStream inputStream;
        Bitmap bitmap;

        public DownloadImg(ImageView img) {
            this.img = img;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setReadTimeout(10000);
                httpsURLConnection.connect();

                int status = httpsURLConnection.getResponseCode();
                if (status == HttpsURLConnection.HTTP_OK) {
                    inputStream = httpsURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
                httpsURLConnection.disconnect();
                inputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            img.setImageBitmap(bitmap);
        }
    }
}