package com.univtop.univtop.models.paging;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dungpham on 11/1/15.
 */
public class PageableList<T> implements Serializable {
    Meta meta;

    List<T> objects;

    public List<T> getData() {
        return objects;
    }

    public Meta getMeta() {
        return meta;
    }
}
