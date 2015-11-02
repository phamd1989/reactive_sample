package com.univtop.univtop.models.paging;

import java.io.Serializable;

/**
 * Created by dungpham on 11/1/15.
 */
public class Meta implements Serializable {
    int limit;
    String next;
    int offset;
    String previous;
    int total_count;

    public int getLimit() {
        return limit;
    }

    public String getNext() {
        return next;
    }

    public int getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }

    public int getTotal_count() {
        return total_count;
    }
}
