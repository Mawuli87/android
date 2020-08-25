package com.messieyawo.unityassembly.DalyVerses;

import androidx.annotation.NonNull;

public class DailyVerses {
    private String Name;
    private String Date;
    private String Description;
    private String Verse;
    private String Type;


    public DailyVerses() {
    }

    public DailyVerses(String name, String date, String description, String verse,String type) {
        Name = name;
        Date = date;
        Description = description;
        Verse = verse;
        Type = type;

    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVerse() {
        return Verse;
    }

    public void setVerse(String verse) {
        Verse = verse;
    }

    @NonNull
    @Override
    public String toString() {
        return Name +" "+Type+" "+Description+""+Verse+" "+Date;
    }
}
