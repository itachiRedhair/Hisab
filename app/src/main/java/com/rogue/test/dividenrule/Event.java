package com.rogue.test.dividenrule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 12/10/2016.
 */

public class Event implements Serializable{
    private String name;
    private List<ShareForAnEvent> shareForAnEventList;
    private String date;

    public Event(String name,String date){

        this.name=name;
        this.date=date;
        shareForAnEventList=new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShareForAnEvent> getShareForAnEventList() {
        return shareForAnEventList;
    }

    public String getDate() {
        return date;
    }

}