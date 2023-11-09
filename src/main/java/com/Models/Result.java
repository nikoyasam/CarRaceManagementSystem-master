package com.Models;

import java.util.Locale;

public class Result extends Driver {
    private int place;
    private double timeInHours;

    public Result(String name, int age, String team, String car, int points, int place) {
        super(name, age, team, car, points);
        this.place = place;
        this.timeInHours = 0.5;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String toCsvString() {
        return String.format(Locale.US, "%s,%d,%s,%s,%d,%d", super.getName(), super.getAge(), super.getTeam(), super.getCar(), super.getPoints(), place);
    // The String.format method is used to create the string, with %s, %d, and ,
        // placeholders to specify the format of each variable
    }


    public static Result fromCsvString(String csvString) {
        Driver driver = Driver.fromCsvString(csvString);
        //parse the driver information
        int place = Integer.parseInt(csvString.split(",")[5]);
        //Integer.parseInt to parse the place
        return new Result(driver.getName(), driver.getAge(), driver.getTeam(), driver.getCar(), driver.getPoints(), place);
    //constructs and returns a new Result object using the parsed values.
    }

    public double getTimeInHours() {
        return timeInHours;
    }
}
