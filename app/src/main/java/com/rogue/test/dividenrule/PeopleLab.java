package com.rogue.test.dividenrule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by akshay on 12/10/2016.
 */

public class PeopleLab {
    private static PeopleLab peopleLab;

    private ArrayList<People> peopleArrayList;

    public static PeopleLab get() {
        if (peopleLab == null) {
            peopleLab = new PeopleLab();
        }
        return peopleLab;
    }

    private PeopleLab() {
        peopleArrayList= new ArrayList<>();
    }

    public List<People> getPeoples() {
        return peopleArrayList;
    }

    public People getSinglePeople(UUID id) {
        for (People people : peopleArrayList) {
            if (people.getId().equals(id)) {
                return people;
            }
        }
        return null;
    }
}
