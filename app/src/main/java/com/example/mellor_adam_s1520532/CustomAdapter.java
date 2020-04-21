package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CustomAdapter extends ArrayAdapter<TrafficScotlandFeed> implements Serializable {

    private Context c;
    private ArrayList<TrafficScotlandFeed> itemDetail;

    public CustomAdapter(Context c, int res, List<TrafficScotlandFeed> obj) {
        super(c, res, obj);

        this.c = c;
        this.itemDetail = (ArrayList) obj;
    }

    public View getView(int place, View convertV, ViewGroup parentL) {

        TrafficScotlandFeed trafficScotlandFeed = itemDetail.get(place);

//The layout inflater takes layout XML files to create view objects from contents

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.item_description_layout, null);

//Finds the associated elements in the res folder
        TextView title = v.findViewById(R.id.title);
        TextView description = v.findViewById(R.id.description);
        TextView link = v.findViewById(R.id.link);


        title.setText(trafficScotlandFeed.getTitle());
        description.setText(trafficScotlandFeed.getDescription());
        link.setText(trafficScotlandFeed.getLink());


//The assignment of colour to the length of the roadworks is extracted from the description
        if (trafficScotlandFeed.getDescription().contains("Start Date")) {

            String format = " EEEE, dd MMMM yyyy";

            SimpleDateFormat dateFormat = new SimpleDateFormat(format);

            String[] parts = trafficScotlandFeed.getDescription().split("<br />");

            String part1 = parts[0];
            String part2 = parts[1];

            part1 = part1.substring(part1.indexOf(':') + 1, part1.indexOf('-'));

            part2 = part2.substring(part2.indexOf(':') + 1, part2.indexOf('-'));

            try {
                Date date1 = dateFormat.parse(part1);

                Date date2 = dateFormat.parse(part2);

                long duration = Math.abs(date1.getTime() - date2.getTime());

                long daysDuration = duration / (24 * 60 * 60 * 1000);

                if (daysDuration != 1) {
                    daysDuration = daysDuration + 1;
                }

// If the duration is less than 3 days, green is assigned, less than 7 days orange is assigned and more than 7 days red is assigned
                if (daysDuration <= 3) {
                    v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.myGreen));
                } else if (daysDuration >= 3 && daysDuration <= 7) {
                    v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.myOrange));
                } else {
                    v.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.myRed));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return v;
    }
}