package com.casaa.xml;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

public class RSS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss);
         new ascTasck().execute("");
    }
     public static class song {
        String title,image;

        public song(String title, String image) {
            this.title = title;
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }


    class ascTasck extends AsyncTask<String ,Void , List<song> > {


        @Override
        protected List<song> doInBackground(String... strings) {
               return new xml("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml",true).Read_songs();
        }

        @Override
        protected void onPostExecute(List<song> songs) {

              CustomAdapter cu = new CustomAdapter(songs);
            RecyclerView re = (RecyclerView) findViewById(R.id.re);
            re.setAdapter(cu);

        }
    }




}