package com.rogue.test.dividenrule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 12/10/2016.
 */

public class EventLab {
    private static EventLab eventLab;

    private ArrayList<Event> eventArrayList;

    public static EventLab get() {
        if (eventLab == null) {
            eventLab = new EventLab();
        }
        return eventLab;
    }

    private EventLab() {
        eventArrayList= new ArrayList<>();
    }

    public List<Event> getEventList() {
        return eventArrayList;
    }

}
