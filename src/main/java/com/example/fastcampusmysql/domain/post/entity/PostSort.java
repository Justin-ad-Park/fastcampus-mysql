package com.example.fastcampusmysql.domain.post.entity;

import org.springframework.data.domain.Sort;

public enum PostSort {
    ID_ASC("id", Sort.Direction.ASC),
    ID_DESC("id", Sort.Direction.DESC),
    CREATED_DATE_ASC("createdDate", Sort.Direction.ASC),
    CREATED_DATE_DESC("createdDate", Sort.Direction.DESC);

    PostSort(String field, Sort.Direction direction) {
        this.sort = Sort.by(direction, field);
    }

    private Sort sort;

    public Sort getSort() {
        return this.sort;
    }

}
