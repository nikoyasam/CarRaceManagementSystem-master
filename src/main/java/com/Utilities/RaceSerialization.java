package com.Utilities;

import com.Models.LinkedList;
import com.Models.Race;
import java.io.*;

public class RaceSerialization {
    private static final String FILENAME = "races.txt"; //Name of the file data is stored

    public static void save(LinkedList<Race> results) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            //This method takes a LinkedList of Race objects as an argument, and writes the
            // serialized data to the file specified by the FILENAME variable
            //PrintWriter object to write the data to the file,
            for (Race race : results) {
                writer.println(race.toCsvString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<Race> load() {
        //s method reads the serialized data from the file specified by the FILENAME variable
        // and returns a LinkedList of Race objects.
        LinkedList<Race> races = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // For each non-empty line of data, it calls the fromCsvString() method to
                // deserialize the data into a Race object, and adds the object to the LinkedList.
                if (!line.equals("")) races.add(Race.fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return races;
    }
}
