package org.cse310.bracu.assignment2.security;

import lombok.*;
import org.cse310.bracu.assignment2.entities.User;
import org.cse310.bracu.assignment2.entities.UserType;

@EqualsAndHashCode
@ToString
public class Session {
    private static Session session;
    private final User user;
    private final UserType userType;


    private Session(User user, UserType userType) {
        this.user = user;
        this.userType = userType;
    }

    public static Session getSession() {
        return session;
    }

    public static void invalidateSession() {
        session = null;
    }

    public static void setSession(User user, UserType userType) {
        session = new Session(user, userType);
    }
}
