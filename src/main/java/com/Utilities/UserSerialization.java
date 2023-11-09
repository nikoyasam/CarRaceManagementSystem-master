package com.Utilities;

import com.Models.User;
import com.Models.LinkedList;
import java.io.*;

public class UserSerialization {
    private static final String FILENAME = "users.txt";

    public static LinkedList<User> load() {
        LinkedList<User> users = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) users.add(User.fromCsvString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static void save(LinkedList<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (User user : users) {
                writer.println(user.toCsvString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
