package com.Utilities;

import com.Models.LinkedList;
import com.Models.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultSerialization {
    public static void save(LinkedList<Result> results, String fileName) {//LinkedList of Result objects and a
        // String file name as parameters.
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Result result : results) {
                writer.println(result.toCsvString());//writes each Result object from the LinkedList to the file
            }
        } catch (IOException e) {//catches any IOException that may occur during file writing and prints the
            // stack trace to the console.
            e.printStackTrace();
        }
    }

    public static LinkedList<Result> load(String fileName) {
        LinkedList<Result> results = new LinkedList<>();//new LinkedList object of Result type to store the results.
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {// catches any IOException that may occur during file reading and
                // prints the stack trace to the console.
                if (!line.equals("")) results.add(Result.fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;//eturns the LinkedList of Result objects read from the file.
    }
}
