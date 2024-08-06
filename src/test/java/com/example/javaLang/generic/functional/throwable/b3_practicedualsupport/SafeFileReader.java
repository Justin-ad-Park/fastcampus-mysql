package com.example.javaLang.generic.functional.throwable.b3_practicedualsupport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SafeFileReader {
    public <R, E extends Throwable> R read(Path path, ResultFactory<R,E> factory) {
        try {
            return factory.success(Files.readString(path));
        } catch (IOException e) {
            return factory.failure((E) e);
        } catch (Exception e) {
            return factory.failure((E) e);
        }
    }
}
