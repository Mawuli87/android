package com.messieyawo.unityassembly.Testimonials;

public class Testimonials {
    private String Name;
    private String Date;
    private String Description;
    private String Title;
    private String Location;



    public Testimonials() {
    }

    public Testimonials(String name, String date, String description, String title,String location) {
        Name = name;
        Date = date;
        Description = description;
        Title = title;
        Location = location;


    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
