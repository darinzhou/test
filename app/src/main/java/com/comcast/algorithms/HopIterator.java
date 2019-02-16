package com.comcast.algorithms;

import java.util.Iterator;

/**
 * Created by zzhou200 on 8/18/15.
 */
public class HopIterator implements Iterator {

    private Iterator it;
    private int hop;

    public HopIterator(Iterator itr, int hopValue) {
        it = itr;
        hop = hopValue;

        for (int i=0; i<hop-1; ++i) {
            if (it.hasNext())
                it.next();
            else
                break;
        }
    }

    @Override
    public boolean hasNext() {
        return it.hasNext();
    }

    @Override
    public Object next() {
        int result = (int)it.next();

        for (int i=0; i<hop-1; ++i) {
            if (it.hasNext())
                it.next();
            else
                break;
        }

        return result;
    }

    @Override
    public void remove() {

    }
}
