package com.rogue.test.dividenrule;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by akshay on 1/6/2017.
 */

public class DataManager {
    public static void updateData(Context c){
        File updateEvents=new File(c.getFilesDir(),"events.ser");
        File updatePeople=new File(c.getFilesDir(),"people.ser");
        try {
            FileOutputStream fos1=new FileOutputStream(updatePeople,false);
            ObjectOutputStream out1=new ObjectOutputStream(fos1);
            for(int i=0;i<PeopleLab.get().getPeoples().size();i++){
                out1.writeObject(PeopleLab.get().getPeoples().get(i));
            }
            out1.flush();
            FileOutputStream fos=new FileOutputStream(updateEvents);
            ObjectOutputStream out=new ObjectOutputStream(fos);
            for(int i=0;i<EventLab.get().getEventList().size();i++){
                out.writeObject(EventLab.get().getEventList().get(i));
            }
            out.flush();
            fos.close();
            fos1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void loadData(Context c){
        File loadEvents=new File(c.getFilesDir(),"events.ser");
        File loadPeople=new File(c.getFilesDir(),"people.ser");

        try {
            FileInputStream fis3=new FileInputStream(loadPeople);
            ObjectInputStream in3=new ObjectInputStream(fis3);
            People p;
            int i=0;
            //Log.i("akki","in load data in datamanger and value of i at start="+String.valueOf(i));
            while((p=(People)in3.readObject())!=null){
                PeopleLab.get().getPeoples().add(i,p);
                i++;
              //  Log.i("akki","in load data in datamanger and value of i="+String.valueOf(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            FileInputStream fis2=new FileInputStream(loadEvents);
            ObjectInputStream in2=new ObjectInputStream(fis2);
            Event e;
            int j=0;
            //Log.i("akki","in load data in datamanger and value of j at start="+String.valueOf(j));

            while((e=(Event)in2.readObject())!=null){
                EventLab.get().getEventList().add(j,e);
                j++;
                Log.i("akki","in load data in datamanger and value of j="+String.valueOf(j));
            }
           // Log.i("akki","in load data in datamanger and value of j="+String.valueOf(j));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//    public static void writeEventObject(Context c,Event event){
//        File updateEvents=new File(c.getFilesDir(),"events.ser");
//        try {
//            FileOutputStream fos=new FileOutputStream(updateEvents);
//            ObjectOutputStream out=new ObjectOutputStream(fos);
//            out.writeObject(event);
//            out.flush();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void writePeopleObject(Context c,People people){
//        File updatePeople=new File(c.getFilesDir(),"people.ser");
//        try {
//            FileOutputStream fos=new FileOutputStream(updatePeople);
//            ObjectOutputStream out=new ObjectOutputStream(fos);
//            out.writeObject(people);
//            out.flush();
//            fos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
