package com.example.dataloader;

import com.example.dataloader.object.Person;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.Map;

public class ComplexCollectionExample {
    public static void main(String[] args) throws Exception {
        String json = "[{\"groupA\":[{\"name\":\"Alice\",\"age\":30},{\"name\":\"Bob\",\"age\":25}]},{\"groupB\":[{\"name\":\"Charlie\",\"age\":28}]}]";
        ObjectMapper mapper = new ObjectMapper();

        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType listPersons = typeFactory.constructCollectionType(List.class, Person.class);
        JavaType stringKey = typeFactory.constructType(String.class);
        JavaType mapType = typeFactory.constructMapType(Map.class, stringKey, listPersons);

        JavaType complexType = typeFactory.constructCollectionType(List.class, mapType);

        List<Map<String, List<Person>>> result = mapper.readValue(json, complexType);

        result.forEach(System.out::println);
    }
}