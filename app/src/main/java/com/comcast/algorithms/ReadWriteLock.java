package com.comcast.algorithms;

public class ReadWriteLock {
    int readers = 0;
    int writers = 0;
    int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException{
        while (writers > 0 || writeRequests > 0) {
            wait();
        }
        readers++;
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;

        while (writers > 0 || readers > 0) {
            wait();
        }

        writeRequests--;
        writers++;
    }

    public synchronized void unlockRead() throws InterruptedException {
        readers--;
        notifyAll();
    }

    public synchronized void unlockWrite() throws InterruptedException {
        writers--;
        notifyAll();
    }
}
