package com.example.javaLang.generic.streamtest.chap08;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionAPI {

    @Test
    void mapFactoryTest() {
        Map<String, Integer> ageOfFriends =
                Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);

        System.out.println(ageOfFriends);

        ageOfFriends = Map.ofEntries(
                entry("Rachael", 30),
                entry("Olivia", 25),
                entry("Justin", 26)
        );
        System.out.println(ageOfFriends);

        Map<String, Integer> finalAgeOfFriends = ageOfFriends;
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
                () -> {
                    finalAgeOfFriends.put("Justin", 30);
                });

        Assertions.assertEquals(26, finalAgeOfFriends.get("Justin"));

    }

    @Test
    void putALl() {
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));

        Map<String, String> friends = Map.ofEntries(
                entry("Rapael", "Star Wars"));

        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);

        System.out.println(everyone);

    }

    @Test
    void merge() {
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));

        Map<String, String> friends = Map.ofEntries(
                entry("Rapael", "Star Wars"),
                entry("Cristina", "Matrix"));

        Map<String, String> everyone = new HashMap<>(family);
        friends.forEach((k, v) ->
                everyone.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));

        System.out.println(everyone);

    }

    @Test
    void mergeNull() {
        Map<String, Long> moviesToCount = new HashMap<>();
        String movieName = "JavesBond";

        Long count = moviesToCount.get(movieName);

        if(count == null)
            moviesToCount.put(movieName, 1L);
        else
            moviesToCount.put(movieName, count + 1L);

        System.out.println(moviesToCount);

    }

    @Test
    void mergeNull2() {
        Map<String, Long> moviesToCount = new HashMap<>();
        String movieName = "JavesBond";

        moviesToCount.merge(movieName, 1L, (key, count) -> count + 1L);

        System.out.println(moviesToCount);
    }

    @Test
    void quiz_289() {
        Map<String, Integer> movies = new HashMap<>();
        movies.put("James Bond", 20);
        movies.put("Matrix", 15);
        movies.put("Harry Potter", 5);

        movies.entrySet().removeIf(entity -> entity.getValue() < 10);

        System.out.println(movies);
    }

}