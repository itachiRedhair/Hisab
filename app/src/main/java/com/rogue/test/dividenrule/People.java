package com.rogue.test.dividenrule;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by akshay on 12/10/2016.
 */

public class People implements Serializable {
    private String name;
    private UUID id;
    private ArrayList<Event> eventList;

    People() {
        id = UUID.randomUUID();
        eventList = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        int totalAmount = 0;
        for (int i = 0; i < eventList.size(); i++) {
            for (int j = 0; j < eventList.get(i).getShareForAnEventList().size(); j++) {
                if (eventList.get(i).getShareForAnEventList().get(j).getPeople().getId().equals(id)) {
                    if (eventList.get(i).getShareForAnEventList().get(j).getStatus().equals("GREEN")) {
                        totalAmount = totalAmount + Integer.parseInt(eventList.get(i).getShareForAnEventList().get(j).getAmount());
                    } else {
                        totalAmount = totalAmount - Integer.parseInt(eventList.get(i).getShareForAnEventList().get(j).getAmount());
                    }
                }
            }
        }
        return Integer.toString(totalAmount);
    }

    public int getColor() {
        if (Integer.parseInt(getTotal()) > 0) {
            return Color.GREEN;
        } else if (Integer.parseInt(getTotal()) < 0) {
            return Color.RED;
        }else{
            return Color.GRAY;
        }
    }
    }


