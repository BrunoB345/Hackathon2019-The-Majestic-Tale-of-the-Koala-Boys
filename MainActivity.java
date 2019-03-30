package com.example.asus.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        events = load(this);
        createEvent();
        if(getIntent().getBooleanExtra("delete", false))
            deleteEvent();

        LinearLayout ll = findViewById(R.id.layout);

        for(int i = events.size()-1; i>=0; i--){
            Button btn = new Button(this);
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT ));
            btn.setId(i);
            btn.setText(events.get(i).getName());
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    action(view);
                }
            });

            ll.addView(btn);
        }
    }



    public void action(View view){
        int i = view.getId();
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("name", events.get(i).getName());
        intent.putExtra("date", (events.get(i).getDate()));
        intent.putExtra("hour", (events.get(i).getHour()));
        intent.putExtra("duration",(events.get(i).getDuration()));
        intent.putExtra("description", events.get(i).getDescription());
        intent.putExtra("location", events.get(i).getLocation());
        intent.putExtra("link", events.get(i).getLink());
        intent.putExtra("index", i);
        startActivity(intent);
        //finish();
    }

    public void addEvent(View view){
        startActivity(new Intent(this, AddEventActivity.class));
        //finish();
    }

    private void createEvent(){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("date");
        String hour = intent.getStringExtra("hour");
        String duration = intent.getStringExtra("duration");
        String description = intent.getStringExtra("description");
        String location = intent.getStringExtra("location");
        String link = intent.getStringExtra("link");
        String picture = intent.getStringExtra("picture");
        if(name != null && date != null && hour != null && duration != null && description != null && location != null /*TODO && picture != null*/) {
            if (link==null)
                events.add(new Event(name, date, hour, duration, location, description, picture));
            else
                events.add(new Event(name, date, hour, duration, location, description, picture, link));
            save(this, events);
        }
    }

    private void deleteEvent(){
        events.remove(getIntent().getIntExtra("index", -1));
        save(this, events);
    }

    public List<Event> load(Context context) {
        SharedPreferences settings;
        List<Event> events;

        settings = context.getSharedPreferences("events", Context.MODE_PRIVATE);

        if (settings.contains("events")) {
            String jsonFavorites = settings.getString("events", null);
            Gson gson = new Gson();
            Event[] eventsArr = gson.fromJson(jsonFavorites,
                    Event[].class);

            events = Arrays.asList(eventsArr);
            events = new LinkedList<Event>(events);
        } else
            return new LinkedList<Event>();

        return  events;
    }

    private void save(Context context, List<Event> events){
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences("events",   Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonEvents = gson.toJson(events);

        editor.putString("events", jsonEvents);

        editor.commit();
    }
}


