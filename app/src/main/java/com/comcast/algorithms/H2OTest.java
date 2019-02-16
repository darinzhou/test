package com.comcast.algorithms;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zzhou200 on 8/19/15.
 */
public class H2OTest {

    static Object lock = new Object();
    static int hc = 0;
    static int oc = 1;
    public static void test2() {

        Thread t1 = new Thread( new Runnable() {
            public void run() {
                while (true) {
                    synchronized(lock) {
                        try {
                            while (oc != 1) {
                                lock.wait();
                            }

                            hc++;
                            if (hc == 2) {
                                oc = 0;
                            }

                            System.out.print((hc == 1) ? "H" : "2");

                            lock.notify();
                        } catch (Exception e) {}
                    }
                }
            }
        });

        Thread t2 = new Thread( new Runnable() {
            public void run() {
                while (true) {
                    synchronized(lock) {
                        try {
                            while (hc != 2) {
                                lock.wait();
                            }

                            hc = 0;
                            oc = 1;

                            System.out.print("O ");

                            lock.notify();
                        } catch (Exception e) {}
                    }
                }
            }
        });

        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (Exception e) {}

    }


    public static void test() {

        final H2O H2Oobj = new H2O();
        Random rnd = new Random();

        for (int i = 0; i < 50; ++i) {
            int r = rnd.nextInt();
            if (r % 3 < 2) {
                new Thread() {
                    @Override
                    public void run() {
                        H2Oobj.H();
                    }
                }.start();
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        H2Oobj.O();
                    }
                }.start();
            }
        }
    }

    static class H2O {
        public static final int H_NUM = 2;
        public static final int O_NUM = 1;
        private int countH;
        private int countO;
        private int releaseH;
        private int releaseO;

        public void H() {
            synchronized (this) {
                countH++;
                System.out.println("H Thread<" + Thread.currentThread().getId() + "> produced H: H=" + countH + ", O=" + countO);
            }

            if (countH >= H_NUM && countO >= O_NUM) {
                synchronized (this) {
                    while (releaseH != 0 || releaseO != 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("H Thread<" + Thread.currentThread().getId() + "> -----> H2O");
                    countH -= H_NUM;
                    countO -= O_NUM;
                    releaseH = H_NUM;
                    releaseO = O_NUM;
                    notifyAll();
                }
            }

            synchronized (this) {
                while (releaseH == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                releaseH--;
                notifyAll();
                System.out.println("H Thread<" + Thread.currentThread().getId() + "> is exiting: H=" + countH + ", O=" + countO);
            }
        }

        public void O() {
            synchronized (this) {
                countO++;
                System.out.println("O Thread<" + Thread.currentThread().getId() + "> produced O: H=" + countH + ", O=" + countO);
            }

            if (countH >= H_NUM && countO >= O_NUM) {
                synchronized (this) {
                    while (releaseH != 0 || releaseO != 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("O Thread<" + Thread.currentThread().getId() + "> -----> H2O");
                    countH -= H_NUM;
                    countO -= O_NUM;
                    releaseH = H_NUM;
                    releaseO = O_NUM;
                    notifyAll();
                }
            }

            synchronized (this) {
                while (releaseO == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                releaseO--;
                notifyAll();
                System.out.println("O Thread<" + Thread.currentThread().getId() + "> is exiting on: H=" + countH + ", O=" + countO);
            }
        }
    }
}