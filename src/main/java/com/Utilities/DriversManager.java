package com.Utilities;

import com.Models.Driver;
import com.Models.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class DriversManager {
    private static final LinkedList<Driver> drivers;
    private static final Comparator<Driver> sortByPoints;
    private static final Comparator<Driver> sortByName; //used to sort drivers by their name.

    static {
        drivers = DriverSerialization.load();
        //initializes the drivers constant by loading a linked list of drivers from a file.
        sortByPoints = new Comparator<Driver>() {
            @Override
            public int compare(Driver o1, Driver o2) {
                return  Double.compare(o2.getPoints(), o1.getPoints());
            }
            //compares drivers based on their points
        };
        sortByName = new Comparator<Driver>() {
            @Override
            public int compare(Driver o1, Driver o2) {
                return o1.getName().compareTo(o2.getName());
            }
        };
    }

    public static void addDriver(Driver driver) {
        drivers.add(driver);//adds a Driver object to the drivers list
        DriverSerialization.save(drivers);//saves the updated list to a file
        // using the DriverSerialization.save() method.
    }

    public static void removeDriver(Driver driver) {
        drivers.remove(driver); // removes a Driver object from the drivers list
        DriverSerialization.save(drivers);//saves the updated list to a file
        // using the DriverSerialization.save() method.
    }

    public static void updateDriver(Driver oldDriver, Driver newDriver) {
        //updates a Driver object in the drivers list by replacing an old driver with a new driver
        drivers.update(oldDriver, newDriver);
        DriverSerialization.save(drivers);////saves the updated list to a file
        // using the DriverSerialization.save() method.
    }

    public static ObservableList<Driver> retrieveAllDrivers () {
        ////method retrieves all the drivers in the system and returns them in an ObservableList.
        drivers.sort(sortByName);// //sorts the drivers list by name using the sortByName comparator.
        return retrieve();////converts the drivers list into an ObservableList and returns it.
    }

    public static ObservableList<Driver> retrieveAllStandings () {
        ////method retrieves all the drivers in the system and returns them in an ObservableList.
        drivers.sort(sortByPoints);//sorts the drivers list by points using the sortByPoints comparator.

        return retrieve();//converts the drivers list into an ObservableList and returns it.
    }

    public static ObservableList<Driver> searchDrivers (String searchText) {
        //method searches for drivers whose information contains the searchText string and
        // returns them in an ObservableList.
        drivers.sort(sortByName);//sorts the drivers list by name using the sortByName comparator.
        return search(searchText);//calls the search method with the searchText parameter,
        // which returns an ObservableList of drivers that match the search criteria.
    }

    public static ObservableList<Driver> searchStandings (String searchText) {
        // method searches for drivers whose information contains the searchText string and
        // returns them in an ObservableList.
        drivers.sort(sortByPoints);//sorts the drivers list by points using the sortByPoints comparator.
        return search(searchText);//calls the search method with the searchText parameter,
        // which returns an ObservableList of drivers that match the search criteria.
    }

    public static void syncStorage () {//saves the current state of the drivers list to a file
        // using the DriverSerialization.save method.

        DriverSerialization.save(drivers);
    }

    public static LinkedList<Driver> getRandomDrivers(int count) {// returns a random subset of
        // count drivers from the drivers list.
        List<Driver> driverList = new ArrayList<>();
        // creates a new ArrayList called driverList and adds all the drivers in the drivers list to it.
        LinkedList<Driver> randomDiversList = new LinkedList<Driver>();
        for (Driver driver : drivers) driverList.add(driver);
        Collections.shuffle(driverList);//shuffles the driver list
        for (int i = 0; i < driverList.size(); i++) if (i < count) randomDiversList.add(driverList.get(i));
        //then creates a new LinkedList called randomDriversList and adds the first
        // count drivers from the shuffled driverList to it.
        return randomDiversList;
    }

    public static void resetSelectedStatus() {

        for (Driver driver : drivers) driver.setSelected(false);//sets the selected field of all drivers in the drivers list to false.
    }

    private static ObservableList<Driver> search(String searchText) {
        //searchText parameter and searches the drivers list for drivers that match the search criteria
        String searchQuery = searchText.toLowerCase(Locale.ROOT);
        ObservableList<Driver> observableDriversList = FXCollections.observableArrayList();
        for (Driver driver : drivers) {
            if (
                    driver.getName().toLowerCase(Locale.ROOT).contains(searchQuery) ||
                            Double.toString(driver.getAge()).contains(searchQuery) ||
                            driver.getCar().toLowerCase(Locale.ROOT).contains(searchQuery) ||
                            driver.getTeam().toLowerCase(Locale.ROOT).contains(searchQuery)
            ) {
                observableDriversList.add(driver);
            }
        }
        return  observableDriversList; //method returns an ObservableList of drivers that match the search criteria.
    }

    private static ObservableList<Driver> retrieve() {
        ObservableList<Driver> observableDriversList = FXCollections.observableArrayList();
        for (Driver driver : drivers) {//iterates over an array or collection of objects of
            // type "Driver" called "drivers".
            observableDriversList.add(driver);//each "Driver" object in "drivers",
            // the loop adds it to the "observableDriversList using add
        }
        return observableDriversList;
        //each "Driver" object in "drivers", the loop adds it to the "observableDriversList
    }
}
