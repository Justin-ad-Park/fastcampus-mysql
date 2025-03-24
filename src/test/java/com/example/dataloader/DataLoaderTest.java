package com.example.dataloader;

import com.example.dataloader.object.Invoice;
import com.example.dataloader.object.Person;
import com.example.dataloader.object.Play;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class DataLoaderTest {

    @Test
    void classTypeDataLoadTest() {
        Person person = DataLoader.loadJsonData(DataLoaderTest.class, "person.json",  Person.class);

        System.out.println(person);
        Assertions.assertEquals("Bob", person.name());
    }

    @Test
    void mapDataLoadTest() {
        Map<String, Play> plays = DataLoader.loadJsonData(DataLoaderTest.class, "playsData.json",  new TypeReference<>() {});

        var result = plays.get("hamlet");
        System.out.println(result);
        Assertions.assertEquals("Hamlet", plays.get("hamlet").name());

        System.out.println(plays);
    }

    @Test
    void listLoaderTest() {
        List<Invoice> invoices = DataLoader.loadJsonData(DataLoaderTest.class, "invoicesData.json",  new TypeReference<>() {});
        Assertions.assertEquals("BigCo", invoices.get(0).customer());

        System.out.println(invoices);
    }


    @Test
    void complexDataLoaderTest() {
        ObjectMapper mapper = new ObjectMapper();

        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType listPersons = typeFactory.constructCollectionType(List.class, Person.class);
        JavaType stringKey = typeFactory.constructType(String.class);
        JavaType mapType = typeFactory.constructMapType(Map.class, stringKey, listPersons);
        JavaType complexType = typeFactory.constructCollectionType(List.class, mapType);

        List<Map<String, List<Person>>> result = DataLoader.loadJsonData(getClass(), "complexJavaType.json", complexType);

        result.forEach(System.out::println);

    }

    @Test
    void complexDataLoaderTest2() {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();

        JavaType mapType = typeFactory.constructType(new TypeReference<Map<String, List<Person>>>() {});
        JavaType complexType = typeFactory.constructCollectionType(List.class, mapType);

        List<Map<String, List<Person>>> result = DataLoader.loadJsonData(DataLoaderTest.class, "complexJavaType.json", complexType);

        result.forEach(System.out::println);
    }

}
