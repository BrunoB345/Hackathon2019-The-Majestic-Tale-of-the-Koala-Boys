package com.example.asus.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void addEvent(View view){
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String date = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String hour = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String description = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String link = ((EditText) findViewById(R.id.editText5)).getText().toString();
        String location = ((EditText) findViewById(R.id.editText6)).getText().toString();
        String duration = ((EditText) findViewById(R.id.editText7)).getText().toString();
        String picture = null;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("date", date);
        intent.putExtra("hour", hour);
        intent.putExtra("duration",duration);
        intent.putExtra("description", description);
        intent.putExtra("location", location);
        intent.putExtra("link", link);
        intent.putExtra("picture", picture);


        startActivity(intent);
        finish();
    }
}
