package com.Utilities;

import com.Models.LinkedList;
import com.Models.Race;
import com.Models.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResultsManager {

    public static ObservableList<Result> retrieveAllResults (Race race) {
        ObservableList<Result> observableResultsList = FXCollections.observableArrayList();
        LinkedList<Result> results = ResultSerialization.load(race.generateFileName());
        //generate the filename that corresponds to the results of the given race.
        for (Result result : results) {
            observableResultsList.add(result);
            // iterates over each "Result" object in the "results" list and adds it to
            // the "observableResultsList".
        }
        return observableResultsList;
    }

    public static void saveResults(Race race, LinkedList<Result> results) {
        ResultSerialization.save(results, race.generateFileName());
        // saves the "results" list to a file
    }

    public static ObservableList<Result> transformToObservableList (LinkedList<Result> results) {
        ObservableList<Result> observableResultsList = FXCollections.observableArrayList();
        for (Result result : results) {
            observableResultsList.add(result);
        }
        return observableResultsList;
        //returns the "observableResultsList" containing all the "Result" objects in the
        // "results" list
    }
}
