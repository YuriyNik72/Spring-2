package ru.geekbrains.rest.entites;

import java.io.Serializable;
import java.util.List;

public class CustomPage<T> implements Serializable {

    private long totalCount;
    private List<T> list;

    public CustomPage(long totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public List<T> getList() {
        return list;
    }
}
