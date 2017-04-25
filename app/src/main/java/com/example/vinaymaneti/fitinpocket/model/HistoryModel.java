package com.example.vinaymaneti.fitinpocket.model;

import java.util.ArrayList;

/**
 * Created by VinayManeti on 25/04/17.
 */

public class HistoryModel {
    private String date;
    private String weight;
    private String s;

    public HistoryModel(String s) {
        this.s = s;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public static ArrayList<HistoryModel> createHistorysList(int numContacts) {
        ArrayList<HistoryModel> contacts = new ArrayList<HistoryModel>();

        for (int i = 1; i <= 50; i++) {
            contacts.add(new HistoryModel("Person " + i));
        }

        return contacts;
    }
}
