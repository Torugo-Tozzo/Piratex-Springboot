package com.example.JDBCwithSpring;

import java.util.List;

public class Customer {
    private long id;
    private String firstName, lastName;
    private String favorite_music;

    public Customer(long id, String firstName, String lastName, String favorite_music) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favorite_music = favorite_music;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s' , favorite_music='%s']",
                id, firstName, lastName, favorite_music);
    }
}
// getters & setters omitted for brevity
