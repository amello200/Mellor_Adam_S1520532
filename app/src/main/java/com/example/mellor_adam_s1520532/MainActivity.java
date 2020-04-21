package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String currentIncidentsUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String roadworksUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String plannedRoadworksUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";


    private Button currentIncidentsButton;
    private Button roadworksButton;
    private Button plannedRoadworksButton;
    private Button maps;


    private ArrayList<TrafficScotlandFeed> currentIncidentsList;
    private ArrayList<TrafficScotlandFeed> roadworksList;
    private ArrayList<TrafficScotlandFeed> plannedRoadworksList;

//    private TextView rawDataDisplay;
//    private String result;
//    private Button startButton;

    String format = " EEEE, dd MMMM yyyy";
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
//        startButton = (Button)findViewById(R.id.startButton);
//        startButton.setOnClickListener(this);

        //links the buttons to the XML layout
        currentIncidentsButton = findViewById(R.id.currentIncidentsButton);
        roadworksButton = findViewById(R.id.roadworksButton);
        plannedRoadworksButton = findViewById(R.id.plannedRoadworksButton);
        maps = findViewById(R.id.mapsButton);

        //This creates a new ArrayList
        currentIncidentsList = new ArrayList<TrafficScotlandFeed>();
        roadworksList = new ArrayList<TrafficScotlandFeed>();
        plannedRoadworksList = new ArrayList<TrafficScotlandFeed>();



//Sets the on click listener for the buttons to start its assigned activity
        new FetchFeedTask().execute((Void) null);

        currentIncidentsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Incidents.class);
                intent.putExtra("currentIncidentsList", currentIncidentsList);
                MainActivity.this.startActivity(intent);

            }
        });

        roadworksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Roadworks.class);
                intent.putExtra("roadworksList", roadworksList);
                MainActivity.this.startActivity(intent);
            }
        });

        plannedRoadworksButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlannedRoadworks.class);
                intent.putExtra("plannedRoadworksList", plannedRoadworksList);
                MainActivity.this.startActivity(intent);
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityMaps.class);
                startActivity(intent);
            }
        });


    }

    public final class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        Context context;
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

//Pre Execution dialog which informs the user data is being loaded
        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog.setMessage("Loading Data..");
            progressDialog.show();


        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            String urlLink = currentIncidentsUrl;

            if (TextUtils.isEmpty(urlLink))
                return false;

            try {

                URL url = new URL(currentIncidentsUrl);
                InputStream inputStream = url.openConnection().getInputStream();
                currentIncidentsList = MainActivity.parseFeed(inputStream);

                URL url2 = new URL(plannedRoadworksUrl);
                InputStream inputStream2 = url2.openConnection().getInputStream();
                plannedRoadworksList = MainActivity.parseFeed(inputStream2);

                URL url3 = new URL(roadworksUrl);
                InputStream inputStream3 = url3.openConnection().getInputStream();
                roadworksList = MainActivity.parseFeed(inputStream3);


                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error Occurred - IOE Exception", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error Occurred - Parser Exception", e);
            }

            return false;
        }

        private ArrayAdapter<TrafficScotlandFeed> currentIncidentsAdapter;
        private ArrayAdapter<TrafficScotlandFeed> roadworksAdapter;
        private ArrayAdapter<TrafficScotlandFeed> plannedRoadworksAdapter;

        @Override
        protected void onPostExecute(Boolean success) {

            if (success) {

                currentIncidentsAdapter = new CustomAdapter(MainActivity.this, 0, currentIncidentsList);
                roadworksAdapter = new CustomAdapter(MainActivity.this, 0, roadworksList);
                plannedRoadworksAdapter = new CustomAdapter(MainActivity.this, 0, plannedRoadworksList);

                progressDialog.dismiss();

            } else {

                Toast.makeText(MainActivity.this,
                        "An Error has occured!",
                        Toast.LENGTH_LONG).show();

            }


        }

    }

    public static ArrayList<TrafficScotlandFeed> parseFeed(InputStream input) throws XmlPullParserException, IOException {


        String link = null;
        String description = null;
        String georss = null;
        String author = null;
        String comments = null;
        String pubDate = null;

        boolean inside = false;

        ArrayList<TrafficScotlandFeed> items = new ArrayList<>();

// Factory features for XML Pull parser
        try {
            XmlPullParser xpp = Xml.newPullParser();
            xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xpp.setInput(input, null);

            xpp.nextTag();
            String title = null;
            while (xpp.next() != XmlPullParser.END_DOCUMENT) {

                int eventType = xpp.getEventType();

                String name = xpp.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        inside = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    Log.i("Xpp", "Start Tag Found");
                    if (name.equalsIgnoreCase("item")) {
                        inside = true;
                        continue;
                    }
                }

                Log.i("XmlParser", "Parsing Tag " + name);
                String result = "";
                if (xpp.next() == XmlPullParser.TEXT) {
                    result = xpp.getText();
                    xpp.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                    if (title == "") {
                        title = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                    if (description == "") {
                        description = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                    if (link == "") {
                        link = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("georss:point")) {
                    georss = result;
                    if (georss == "") {
                        georss = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("author")) {
                    author = result;
                    if (author == "") {
                        author = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("comments")) {
                    comments = result;
                    if (comments == "") {
                        comments = "Not Found";
                    }
                } else if (name.equalsIgnoreCase("pubDate")) {
                    pubDate = result;
                    if (pubDate == "") {
                        pubDate = "Not Found";
                    }
                }

                if (title != null && link != null && description != null && georss != null) {
                    if (inside) {
                        TrafficScotlandFeed item = new TrafficScotlandFeed(title, description, link, georss, author, comments, pubDate);
                        items.add(item);
                    } else {
                        return null;
                    }

                    title = null;
                    link = null;
                    description = null;
                    inside = false;
                }
            }
            return items;
        } finally {
            input.close();
        }
    }
}
