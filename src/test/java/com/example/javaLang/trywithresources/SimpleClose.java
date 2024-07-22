package com.example.javaLang.trywithresources;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleClose {

    @Test
    void simpleClose() throws IOException {
        FileInputStream is = null;
        BufferedInputStream bis = null;
        try {
            is = new FileInputStream("src/test/resources/file.txt");
            bis = new BufferedInputStream(is);
            int data;
            while((data = bis.read()) != -1){
                System.out.print((char) data);
            }
        } finally {
            // close resources
            if (is != null) is.close();
            if (bis != null) bis.close();
        }
    }

    @Test
    void tryWithResources() throws IOException {
        try (FileInputStream is = new FileInputStream("src/test/resources/file.txt");
             BufferedInputStream bis = new BufferedInputStream(is)) {
            int data;
            while((data = bis.read()) != -1){
                System.out.print((char) data);
            }
        }
    }
}
