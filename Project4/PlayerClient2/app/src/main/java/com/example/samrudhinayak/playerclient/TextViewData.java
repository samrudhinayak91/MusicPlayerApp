package com.example.samrudhinayak.playerclient;

/**
 * Created by Samrudhi Nayak on 4/8/2016.
 */
public class TextViewData {
    private String date1;
    private String activity_action;
    private String clip_number;
    private String description;
    //getter and setter method to get and set the elements of the list such as the textviews
    public String getdate1() {
        return date1;
    }

    public void setdate1(String date1) {
        this.date1 = date1;
    }

    public String getactivity_action() {
        return activity_action;
    }

    public void setactivity_action(String activity_action) {
        this.activity_action = activity_action;
    }

    public String getclip_number() {
        return clip_number;
    }

    public void setclip_number(String clip_number) {
        this.clip_number = clip_number;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //constructor to create an instance of the class
    public TextViewData(String date1, String activity_action, String clip_number,String description) {
        this.setdate1(date1);
        this.setactivity_action(activity_action);
        this.setclip_number(clip_number);
        this.setDescription(description);
    }
}

