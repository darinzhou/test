package com.comcast.algorithms;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zzhou200 on 8/19/15.
 */
public class H2OTest2 {

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
        Lock lock = new ReentrantLock();
        Condition generateH2O = lock.newCondition();
        Condition releaseHorO = lock.newCondition();

        public void H() {
            lock.lock();
            try {
                countH++;
                System.out.println("H Thread<" + Thread.currentThread().getId() + "> produced H: H=" + countH + ", O=" + countO);
            } finally {
                lock.unlock();
            }

            if (countH >= H_NUM && countO >= O_NUM) {
                lock.lock();
                try {
                    while (releaseH != 0 || releaseO != 0) {
                        try {
                            generateH2O.await();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("H Thread<" + Thread.currentThread().getId() + "> -----> H2O");
                    countH -= H_NUM;
                    countO -= O_NUM;
                    releaseH = H_NUM;
                    releaseO = O_NUM;
                    releaseHorO.signalAll();
                } finally {
                    lock.unlock();
                }
            }

            lock.lock();
            try {
                while (releaseH == 0) {
                    try {
                        releaseHorO.await();
                    } catch (InterruptedException e) {
                    }
                }
                releaseH--;
                generateH2O.signalAll();
                System.out.println("H Thread<" + Thread.currentThread().getId() + "> is exiting: H=" + countH + ", O=" + countO);
            } finally {
                lock.unlock();
            }
        }

        public void O() {
            lock.lock();
            try {
                countO++;
                System.out.println("O Thread<" + Thread.currentThread().getId() + "> produced H: H=" + countH + ", O=" + countO);
            } finally {
                lock.unlock();
            }

            if (countH >= H_NUM && countO >= O_NUM) {
                lock.lock();
                try {
                    while (releaseH != 0 || releaseO != 0) {
                        try {
                            generateH2O.await();
                        } catch (InterruptedException e) {
                        }
                    }
                    System.out.println("O Thread<" + Thread.currentThread().getId() + "> -----> H2O");
                    countH -= H_NUM;
                    countO -= O_NUM;
                    releaseH = H_NUM;
                    releaseO = O_NUM;
                    releaseHorO.signalAll();
                } finally {
                    lock.unlock();
                }
            }

            lock.lock();
            try {
                while (releaseO == 0) {
                    try {
                        releaseHorO.await();
                    } catch (InterruptedException e) {
                    }
                }
                releaseO--;
                generateH2O.signalAll();
                System.out.println("H Thread<" + Thread.currentThread().getId() + "> is exiting: H=" + countH + ", O=" + countO);
            } finally {
                lock.unlock();
            }
        }
    }
}