package com.rogue.test.dividenrule;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by akshay on 12/10/2016.
 */

public class ShareForAnEvent implements Serializable{
    People people;
    String amount;
    String Status;
    private String green="GREEN";
    private String red="RED";

    ShareForAnEvent(People people,String amount,String status){
        this.people=people;
        this.amount=amount;
        this.Status=status;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public String getAmount() {
        return amount;
    }

    public String getStatus() {
        return Status;
    }
    public int getColor(){
        if(getStatus().equals(green)){
            return Color.GREEN;
        }else{
            return Color.RED;
        }
    }

}
