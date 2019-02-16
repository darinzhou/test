package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 Data structure: insert, remove, contains, get random element, all at O(1)
 */
public class Bag {
    HashMap map =  new HashMap();       // <value, indexInList>
    ArrayList list = new ArrayList();   // (value)

    // O(1)
    public void insert(Object o) {
        list.add(o);
        map.put(o, list.size()-1);
    }

    // O(1)
    public boolean remove(Object o) {
        if (!map.containsKey(o))
            return false;

        int lastIndex = list.size()-1;
        Object objLast = list.get(list.size() - 1);
        int index = (int)map.get(o);

        // set last element in list to index
        list.set(index, objLast);
        // update last object in hashmap
        map.put(objLast, index);

        // remove last element in list
        list.remove(lastIndex);
        // remove target from hashmap
        map.remove(o);

        return true;
    }

    // O(1)
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    // O(1)
    public Object getRandom() {
        Random rnd = new Random();
        int r = rnd.nextInt(list.size());
        return list.get(r);
    }
}
