package com.example.asus.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EventActivity extends AppCompatActivity {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;

    int index;
    String name, date, hour, location;
    String duration, description, link;
    String smsMessage;
    byte[] picture;

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
        smsMessage = "Olá, estás interessado em ir comigo a " + name + " no dia " + date + " às " + hour +"?";
        picture = getIntent().getByteArrayExtra("picture");
        Bitmap pictureD = BitmapFactory.decodeByteArray(picture, 0, picture.length);


        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(pictureD);

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
        if(!checkPermission(Manifest.permission.SEND_SMS))
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", smsMessage);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);

    }

    private boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (PackageManager.PERMISSION_GRANTED == check);
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

