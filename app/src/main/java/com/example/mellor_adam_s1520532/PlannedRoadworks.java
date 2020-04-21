package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlannedRoadworks extends AppCompatActivity {

//Assigns the lists
    private List<TrafficScotlandFeed> plannedRoadworksList;
    private List<TrafficScotlandFeed> selectedList;
//Assigns the Array Adapter
    private ArrayAdapter<TrafficScotlandFeed> plannedRoadworksAdapter;
    private ArrayAdapter<TrafficScotlandFeed> selectedDetailAdapter;
//Assigns the buttons
    private Button selectDateButton;
    private Button getRW;

//Assigns the edit text field and
    private EditText searchBar;
    private ListView plannedRoadworksListView;
    private ListView selectedListView;
    Date selectedDate;

    //Invokes the on create callback for the activity lifecycle
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plannedroadworks_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        selectDateButton = findViewById(R.id.selectDateButton);
        searchBar = findViewById(R.id.searchBar);
        selectedListView = findViewById(R.id.selectedList);
        selectedListView.setVisibility(View.INVISIBLE);
        selectedList = new ArrayList<TrafficScotlandFeed>();

        getRW = findViewById(R.id.getPlannedRw);
        getRW.setInputType(0);

        plannedRoadworksListView = findViewById(R.id.plannedRwList);
        plannedRoadworksList = (ArrayList<TrafficScotlandFeed>) getIntent().getSerializableExtra("plannedRoadworksList");

        plannedRoadworksAdapter = new CustomAdapter(PlannedRoadworks.this, 0, plannedRoadworksList);
        plannedRoadworksListView.setAdapter(plannedRoadworksAdapter);


        plannedRoadworksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int place, long row) {
//Assigns the intents
                TrafficScotlandFeed trafficScotlandFeed = plannedRoadworksList.get(place);

                Intent intent = new Intent(PlannedRoadworks.this, Description.class);
                intent.putExtra("title", trafficScotlandFeed.getTitle());
                intent.putExtra("description", trafficScotlandFeed.getDescription());
                intent.putExtra("link", trafficScotlandFeed.getLink());
                intent.putExtra("georss", trafficScotlandFeed.getGeorss());
                intent.putExtra("author", trafficScotlandFeed.getAuthor());
                intent.putExtra("comments", trafficScotlandFeed.getComments());
                intent.putExtra("pubDate", trafficScotlandFeed.getPubDate());

                startActivity(intent);
            }
        });

        selectedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int place, long row) {

                TrafficScotlandFeed trafficScotlandFeed = plannedRoadworksList.get(place);

                Intent intent = new Intent(PlannedRoadworks.this, Description.class);
                intent.putExtra("title", trafficScotlandFeed.getTitle());
                intent.putExtra("description", trafficScotlandFeed.getDescription());
                intent.putExtra("link", trafficScotlandFeed.getLink());
                intent.putExtra("georss", trafficScotlandFeed.getGeorss());
                intent.putExtra("author", trafficScotlandFeed.getAuthor());
                intent.putExtra("comments", trafficScotlandFeed.getComments());
                intent.putExtra("pubDate", trafficScotlandFeed.getPubDate());

                startActivity(intent);
            }
        });


        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                selectedList = new ArrayList<TrafficScotlandFeed>();

//Sets up the calender which parses the choice of day from the calendar
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                selectedDate = calendar.getTime();

                getSelectedDates();
                if (selectedList.size() > 0) {
                    selectedDetailAdapter = new CustomAdapter(PlannedRoadworks.this, 0, selectedList);
                    selectedListView.setAdapter(selectedDetailAdapter);
                    selectedListView.setVisibility(View.VISIBLE);
                    plannedRoadworksListView.setVisibility(View.INVISIBLE);
                } else {
                    displayErrorMessage();
                    selectedListView.setVisibility(View.INVISIBLE);
                    plannedRoadworksListView.setVisibility(View.VISIBLE);
                }
            }

        };

        selectDateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(PlannedRoadworks.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        getRW.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                } catch (Exception e) {

                }


                selectedList = new ArrayList<TrafficScotlandFeed>();
                String textInput = searchBar.getText().toString();
                getRoadworks(textInput);

                if (selectedList.size() > 0) {
                    selectedDetailAdapter = new CustomAdapter(PlannedRoadworks.this, 0, selectedList);
                    selectedListView.setAdapter(selectedDetailAdapter);
                    selectedListView.setVisibility(View.VISIBLE);
                    plannedRoadworksListView.setVisibility(View.INVISIBLE);
                }
                else {
                    displayErrorMessage();
                    selectedListView.setVisibility(View.INVISIBLE);
                    plannedRoadworksListView.setVisibility(View.VISIBLE);
                }
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

    public void getRoadworks(String find) {

        for (TrafficScotlandFeed t : plannedRoadworksList) {
            String search = t.getTitle();
            if (search.toLowerCase().indexOf(find.toLowerCase()) != -1) {
                selectedList.add(t);
            }
        }
    }

    public void displayErrorMessage() {

        Toast.makeText(PlannedRoadworks.this,
                "No entries found!",
                Toast.LENGTH_LONG).show();
    }

    public void getSelectedDates() {
        for (TrafficScotlandFeed t : plannedRoadworksList) {

            String format = "dd MMMM yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            DateTime selectedDate = new DateTime(this.selectedDate);

            String[] parts = t.getDescription().split("<br />");
            String part1 = parts[0];
            String part2 = parts[1];

            part1 = part1.substring(part1.indexOf(',') + 1, part1.indexOf('-')).substring(1);
            part2 = part2.substring(part2.indexOf(',') + 1, part2.indexOf('-')).substring(1);

            try {
                Date from = dateFormat.parse(part1);
                Date to = dateFormat.parse(part2);

                DateTime startDate = new DateTime(from);
                DateTime endDate = new DateTime(to);

                for (DateTime current = startDate; current.isBefore(endDate);
                     current = current.plusDays(1)) {

                    String currentDate = current.toString().substring(0, 10);

                    String dateSelected = selectedDate.toString().substring(0, 10);

                    if (currentDate.equals(dateSelected)) {
                        selectedList.add(t);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
