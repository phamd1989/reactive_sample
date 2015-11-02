package com.univtop.univtop.utils;

import com.univtop.univtop.models.paging.PageableList;

import rx.functions.Func1;

/**
 * Created by dungpham on 11/2/15.
 */
public class DataParserFunc1Handler<T> implements Func1<PageableList<T>, T> {

    @Override
    public T call(PageableList<T> tPageableList) {
//        return tPageableList.getData();
        return null;
    }
}
