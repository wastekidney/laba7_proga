package ru.itmo.client.session;

import ru.itmo.common.Collection.User.User;

public class SessionHandler {

    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
    public static boolean isAuthorized() {
        return currentUser != null;
    }
    public static void clear() {
        currentUser = null;
    }
}