package com.example.mellor_adam_s1520532;

//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class Description extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView titleView = findViewById(R.id.title);
        TextView descriptionView = findViewById(R.id.description);
        TextView linkView = findViewById(R.id.link);
        TextView georssView = findViewById(R.id.georss);
        TextView authorView = findViewById(R.id.author);
        TextView commentsView = findViewById(R.id.comments);
        TextView pubDateView = findViewById(R.id.pubDate);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String link = intent.getStringExtra("link");
        String georss = intent.getStringExtra("georss");
        String author = intent.getStringExtra("author");
        String comments = intent.getStringExtra("comments");
        String pubDate = intent.getStringExtra("pubDate");


        titleView.setText(title);
        descriptionView.setText(description);
        linkView.setText("Link: " + link);
        georssView.setText("Georss: " + georss);
        authorView.setText("Author: " + author);
        commentsView.setText("Comments: " + comments);
        pubDateView.setText("Date Published: " + pubDate);

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