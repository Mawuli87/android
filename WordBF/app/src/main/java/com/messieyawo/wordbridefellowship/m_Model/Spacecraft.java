package com.messieyawo.wordbridefellowship.m_Model;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. OUR MODEL CLASS
 */
public class Spacecraft {

    String name,propellant,description,date;

    public Spacecraft() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPropellant() {
        return propellant;
    }

    public void setPropellant(String propellant) {
        this.propellant = propellant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}