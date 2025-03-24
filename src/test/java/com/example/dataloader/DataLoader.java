package com.example.dataloader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class DataLoader {
    public static <T> T loadJsonData(Class aClass, String filePath, Class<T> valueType) {
      ObjectMapper objectMapper = new ObjectMapper();

      try (InputStream inputStream = aClass.getResourceAsStream(filePath)) {
            return objectMapper.readValue(inputStream, valueType);
      } catch (Exception e) {
            throw new RuntimeException(e);
      }
    }

    public static <T> T loadJsonData(Class aClass, String filePath, TypeReference<T> valueTypeRef) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = aClass.getResourceAsStream(filePath)) {
            return objectMapper.readValue(inputStream, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T loadJsonData(Class aClass, String filePath, JavaType javaType) {
        ObjectMapper objectMapper = new ObjectMapper();

        try (InputStream inputStream = aClass.getResourceAsStream(filePath)) {
            return objectMapper.readValue(inputStream, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
