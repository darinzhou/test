package com.comcast.algorithms;

import android.util.Log;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by zzhou200 on 8/11/15.
 */
public class ClientServerTest {

    // Job
    public static class Job implements Comparable<Job> {
        private String name;
        private Runnable job;
        private long startTime;

        public Job(String name, Runnable job, long delay) {
            this.name = name;
            this.job = job;
            this.startTime = System.currentTimeMillis() + delay;
        }

        public String getName() {
            return name;
        }

        public Runnable getJob() {
            return job;
        }

        public long getStartTime() {
            return startTime;
        }

        @Override
        public int compareTo(Job another) {
            if (startTime < another.getStartTime())
                return -1;
            else if (startTime > another.getStartTime())
                return 1;
            return 0;
        }
    }

    // Server
    public static class Server {
        public final int MAX_JOB_CAPACITY = 10;

        private int jobCount;
        private PriorityQueue<Job> queue = new PriorityQueue<>();

        private synchronized void acceptJob(Job job) {
            while (jobCount == MAX_JOB_CAPACITY) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int oldCount = jobCount;
            queue.add(job);
            jobCount++;

            Log.i("Server", "Job " + job.getName() + " was accepted.");

            if (oldCount == 0)
                notifyAll();
        }

        private synchronized void processJob() {
            while (jobCount == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            long currentTime = System.currentTimeMillis();
            Job job = queue.peek();

            // time to run the job?
            if (currentTime >= job.getStartTime()) {
                int oldCount = jobCount;
                job = queue.remove();
                jobCount--;

                if (oldCount == MAX_JOB_CAPACITY)
                    notifyAll();

                Thread t = new Thread(job.getJob());
                t.start();
            }
        }

        public void start() {
            new Thread() {
                @Override
                public void run() {
                    while (true)
                        processJob();
                }
            }.start();
        }

        public void receiveJob(final Job job) {
            new Thread() {
                @Override
                public void run() {
                    acceptJob(job);
                }
            }.start();
        }
    }

    // Client
    public static class Client {
        private String name;

        public Client(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void sendJob(Server server) {
            Random rnd = new Random();
            Job job = new Job(name, new Runnable() {
                @Override
                public void run() {
                    Log.i("Job", "Job " + name + " is running...");
                }
            }, rnd.nextInt(10000));

            server.receiveJob(job);
        }
    }

//    // TestFCA
//    public static void main(String[] args) {
//        final ClientServerTest.Server server = new ClientServerTest.Server();
//            server.start();
//
//            for (int i=0; i<20;++i) {
//                final String clientName = "client_" + i;
//                ClientServerTest.Client client = new ClientServerTest.Client(clientName);
//                client.sendJob(server);
//            }
//    }
}
