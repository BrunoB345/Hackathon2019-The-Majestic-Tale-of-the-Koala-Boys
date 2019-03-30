package com.example.asus.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventActivity extends AppCompatActivity {

    int index;
    String name, date, hour, location;
    String duration, description, link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");
        hour = getIntent().getStringExtra("hour");
        duration = getIntent().getStringExtra("duration");
        description = getIntent().getStringExtra("description");
        location = getIntent().getStringExtra("location");
        link = getIntent().getStringExtra("link");
        index = getIntent().getIntExtra("index", -1);



        ((TextView) findViewById(R.id.textView)).setText(name);
        ((TextView) findViewById(R.id.textView2)).setText(date);
        ((TextView) findViewById(R.id.textView3)).setText(hour);
        ((TextView) findViewById(R.id.textView7)).setText(duration);
        ((TextView) findViewById(R.id.textView6)).setText(location);
        ((TextView) findViewById(R.id.textView4)).setText(description);
        TextView tv = findViewById(R.id.textView5);
        tv.setText(link);
        tv.setPaintFlags(tv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
    }

    public void delete(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("delete", true);
        intent.putExtra("index", index);
        startActivity(intent);
        finish();
    }

    public void sendSMS(View view){
        Intent intent = new Intent(this, ContactsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("hour", hour);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void showLoc(View view){

        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void openLink(View view){
        String theurl = "http://" + link;
        Uri urlstr = Uri.parse(theurl);
        Intent urlintent = new Intent();
        urlintent.setData(urlstr);
        urlintent.setAction(Intent.ACTION_VIEW);
        startActivity(urlintent);
    }

}

