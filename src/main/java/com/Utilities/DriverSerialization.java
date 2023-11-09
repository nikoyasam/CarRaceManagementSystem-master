package com.Utilities;

import com.Models.Driver;
import com.Models.LinkedList;

import java.io.*;

public class DriverSerialization {
    private static final String FILENAME = "drivers.txt"; //Name of the file data will be stored

    public static LinkedList<Driver> load() {
        //This line declares a public static method load() that returns a LinkedList<Driver>.
        // This method will be used to deserialize the data from the file.
        LinkedList<Driver> drivers = new LinkedList<>(); //stores driver objects
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {//read
            //data from the file specified by filename
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) drivers.add(Driver.fromCsvString(line));
                //checks whether the line read from the file is empty
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public static void save(LinkedList<Driver> drivers) { // method will be used to
        // serialize the Driver objects and write them to a file.
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Driver driver : drivers) {
                writer.println(driver.toCsvString());
            }
        } catch (IOException e) {
            //catches any IOException that may occur during the file writing operation and
            // prints the stack trace.
            e.printStackTrace();
        }
    }
}
