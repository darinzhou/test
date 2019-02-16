package com.comcast.algorithms;

/**
 * Created by zzhou200 on 8/20/15.
 */
public class Singleton {

    // use volatile to tell JVM always use value from main memory, don't cache it in thread locally
    private static volatile Singleton sInstance;

    private Singleton(){
        // prevent form the reflection api
        if (sInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Singleton getInstance() {
        // lazy initialization

        // double check locking pattern

        // check for the first time
        if (sInstance == null) {
            // synchronized to ensure thread safe
            synchronized (Singleton.class) {
                // check for the second time
                if (sInstance == null) {
                    sInstance = new Singleton();
                }
            }
        }
        return sInstance;
    }
}
