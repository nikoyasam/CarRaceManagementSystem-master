package com.Utilities;

import com.Models.LinkedList;
import com.Models.User;

import java.util.Locale;

public class UserManager {
    static {
        initialize();
    }

    public static boolean signIn(String username, String password) {
        LinkedList<User> users = UserSerialization.load();
        for (User user : users) {
            if (user.getUsername().toLowerCase(Locale.ROOT).equals(username.toLowerCase(Locale.ROOT))
                    && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static boolean signUp(String username, String password) {
        LinkedList<User> users = UserSerialization.load();
        for (User user : users) {
            if (user.getUsername().toLowerCase(Locale.ROOT).equals(username.toLowerCase(Locale.ROOT))
                    && user.getPassword().equals(password)) {
                return false;
            }
        }
        users.add(new User(username, password));
        UserSerialization.save(users);
        return true;
    }

    public static void initialize() {
        LinkedList<User> users = UserSerialization.load();
        if (users.size() == 0) {
            users.add(new User(Constants.Credentials.USERNAME, Constants.Credentials.PASSWORD));
            UserSerialization.save(users);
        }
    }
}
