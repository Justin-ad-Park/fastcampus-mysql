package com.example.fastcampusmysql.util;

public class CursorRequest {
    protected Long key;
    protected final Long size;

    public static final Long NONE_KEY = -1L;

    public CursorRequest(final Long key, final Long size) {
        this.key = key;
        this.size = size;
    }

    public Boolean hasKey() {
        return key != null;
    }

    public Long key() {
        return key;
    }

    public Long size() {
        return size;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
