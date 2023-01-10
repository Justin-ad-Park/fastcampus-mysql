package com.example.fastcampusmysql.util;

import lombok.Getter;

@Getter
public class CursorRequestV2 extends CursorRequest {
    private Long prevKey = null;
    private boolean updatedNextKey = false;
    public static final Long NONE_KEY = -1L;

    public CursorRequestV2(final Long key, final Long size) {
        super(key, size);
    }

    public Boolean updatedNextKey() {
        return updatedNextKey;
    }

    public void setKeyToNext(Long nextKey) {
        this.prevKey = this.key;
        this.key = nextKey;
        this.updatedNextKey = true;
    }
}
