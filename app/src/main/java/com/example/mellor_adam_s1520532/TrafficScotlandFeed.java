package com.example.mellor_adam_s1520532;


//Student Name: Adam James Mellor
//Student Matriculation ID: S1520532

import java.io.Serializable;

public class TrafficScotlandFeed implements Serializable {

    private String title;
    private String description;
    private String link;
    private String georss;
    private String author;
    private String comments;
    private String pubDate;

//Declaring variables and initialising them

    public TrafficScotlandFeed()
    {
        title = "";
        description = "";
        link = "";
        georss = "";
        author = "";
        comments = "";
        pubDate = "";
    }



    public TrafficScotlandFeed(String atitle , String adescription, String alink, String ageorss, String aauthor, String acomments, String apubDate)
    {
        title = atitle;
        description = adescription;
        link = alink;
        georss = ageorss;
        author = aauthor;
        comments = acomments;
        pubDate = apubDate;
    }

//Getter and setter for the Title
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

//Getter and setter for the Description
    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

//Getter and setter for the Link
    public String getLink() {

        return link;
    }

    public void setLink(String link) {

        this.link = link;
    }

//Getter and setter for the Georss Point
    public String getGeorss() {

        return georss;
    }

    public void setGeorss(String georss) {

        this.georss = georss;
    }

//Getter and setter for the Author
    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

//Getter and setter for the Comments
    public String getComments() {

        return comments;
    }

    public void setComments(String comments) {

        this.comments = comments;
    }

//Getter and setter for the Published Date
    public String getPubDate() {

        return pubDate;
    }

    public void setPubDate(String pubDate) {

        this.pubDate = pubDate;
    }

}