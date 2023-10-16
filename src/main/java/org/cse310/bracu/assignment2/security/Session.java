package org.cse310.bracu.assignment2.security;

import lombok.*;
import org.cse310.bracu.assignment2.entities.User;
import org.cse310.bracu.assignment2.entities.UserType;

@EqualsAndHashCode
@ToString
public class Session {
    private static Session session;
    private final User user;


    private Session(User user) {
        this.user = user;
    }

    public static User getSession() {
        return session.user;
    }

    public static void invalidateSession() {
        session = null;
    }

    public static void setSession(User user) {
        session = new Session(user);
    }
    public static boolean isSession() {
        return session != null;
    }
}
