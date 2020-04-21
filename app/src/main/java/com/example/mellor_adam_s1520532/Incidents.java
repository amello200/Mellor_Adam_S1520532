package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Incidents extends AppCompatActivity {


    private ListView currentIncidentsListView;


    private ArrayAdapter<TrafficScotlandFeed> currentIncidentAdapter;
    private List<TrafficScotlandFeed> currentIncidentList;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_incidents_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentIncidentsListView = findViewById(R.id.currentIncidentsList);
        currentIncidentsListView.setAdapter(currentIncidentAdapter);

        currentIncidentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int place, long row) {
                TrafficScotlandFeed trafficScotlandFeed = currentIncidentList.get(place);

                Intent intent = new Intent(Incidents.this, Description.class);
                intent.putExtra("title", trafficScotlandFeed.getTitle());
                intent.putExtra("description", trafficScotlandFeed.getDescription());
                intent.putExtra("link", trafficScotlandFeed.getLink());
                intent.putExtra("georss", trafficScotlandFeed.getGeorss());
                intent.putExtra("author", trafficScotlandFeed.getAuthor());
                intent.putExtra("comments", trafficScotlandFeed.getComments());
                intent.putExtra("pubDate", trafficScotlandFeed.getPubDate());

                startActivity(intent);


                currentIncidentAdapter = new CustomAdapter(Incidents.this, 0, currentIncidentList);
                currentIncidentList = (ArrayList<TrafficScotlandFeed>) getIntent().getSerializableExtra("currentIncidentsList");

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}