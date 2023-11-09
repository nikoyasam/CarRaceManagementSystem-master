package com.Utilities;

import com.Models.LinkedList;
import com.Models.Race;
import com.Models.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Comparator;
import java.util.Locale;

public class RaceManager {
    private static final LinkedList<Race> races;
    private static final Comparator<Race> sortByDate;

    static {
        races = RaceSerialization.load();//loads a list of Race objects
        sortByDate = new Comparator<Race>() {
            @Override
            public int compare(Race o1, Race o2) {

                return o2.getDate().compareTo(o1.getDate());//compare the dates of two
                //Race objects, with the more recent date being considered "greater".
            }
        };
        races.sort(sortByDate);// list will be sorted in descending order of date.
    }

    public static void addRace(Race race, LinkedList<Result> results) {
        races.add(race);//adds the "Race" object to the "races" list.
        RaceSerialization.save(races);// saves the updated "races" list to a file
        ResultsManager.saveResults(race, results);//"Result" objects associated with the "Race" object
        races.sort(sortByDate);//sorts the "races" list using the "sortByDate" comparator
        //new "Race" object is added in the appropriate position.
    }

    public static ObservableList<Race> retrieveAllRaces () {
        ObservableList<Race> observableRacesList = FXCollections.observableArrayList();
        for (Race race : races) {
            observableRacesList.add(race);
        }
        return observableRacesList;
    }

    public static ObservableList<Race> searchRaces (String searchText) {
        //returns an "ObservableList" of "Race" objects.
        String searchQuery = searchText.toLowerCase(Locale.ROOT);
        ObservableList<Race> observableRacesList = FXCollections.observableArrayList();
        for (Race race : races) {
            if (
                race.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                race.getLocation().toLowerCase().contains(searchQuery.toLowerCase()) ||
                race.getDate().toString().toLowerCase(Locale.ROOT).contains(searchQuery) ||
                Double.toString(race.getTrackLength()).contains(searchQuery)
            ) {
                observableRacesList.add(race);
            }
        }
        return observableRacesList;
    }

    public static void syncStorage () {

        RaceSerialization.save(races);//saves the "races" list to a file
    }
}
