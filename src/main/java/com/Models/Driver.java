package com.Models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import java.util.Locale;

public class Driver {  //objects in the Driver class
    private String name;
    private int age;
    private String team;
    private String car;
    private int points;

    private boolean selected = false; //indicate whether the driver is selected or not.

    public Driver(String name, int age, String team, String car, int points) {
        this.name = name;
        this.age = age;
        this.team = team;
        this.car = car;
        this.points = points;
    }
    // return the values of the respective instance variables.
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getTeam() {
        return team;
    }

    public String getCar() {
        return car;
    }
    public int getPoints() {
        return points;
    }

    //set the values of the respective instance variables.
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() { //method returns a string representation of the Driver object.
        return "Driver{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", team='" + team + '\'' +
                ", car='" + car + '\'' +
                ", points=" + points +
                '}';
    }

    public String toCsvString() {
        return String.format(Locale.US, "%s,%d,%s,%s,%d", name, age, team, car, points);
    }
    //returns a comma-separated string representation of the Driver object, which can be used to
    // write the object to a CSV file.
    public static Driver fromCsvString(String csvString) { //takes a comma-separated string
        // representation of a Driver object and returns a new Driver object with the same property
        // values.

        String[] fields = csvString.split(",");
        String name = fields[0];
        int age = Integer.parseInt(fields[1]);
        String team = fields[2];
        String car = fields[3];
        int points = Integer.parseInt(fields[4]);
        return new Driver(name, age, team, car, points);
    }

    public ObservableValue<Boolean> selectedProperty() {
        return new SimpleBooleanProperty(selected);
    }
    //represents the selected variable, which can be observed and bound to user interface elements
}
