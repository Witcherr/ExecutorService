package com.potopalskyi.executorservice.executor;

import java.util.*;
import java.util.concurrent.Executor;

public class ThreadPoolExecutor implements Executor {
    private List<Thread> threads = new ArrayList<>();
    private final Queue<Runnable> taskQueue = new LinkedList<>();
    private volatile boolean isShoutDown;

    public ThreadPoolExecutor(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ThreadTask());
            threads.add(thread);
            thread.start();
        }
    }

    @Override
    public void execute(Runnable command) {
        if (!isShoutDown) {
            synchronized (taskQueue) {
                taskQueue.add(command);
            }
        } else if (isShoutDown) {
            System.out.println("Executor service is going to down, new task can not be added");
            //throw new RuntimeException("Executor service is going to down, new task can not be added");
        }
    }

    public void shutdown() {
        isShoutDown = true;
        while (true) {
            synchronized (taskQueue) {
                if (taskQueue.isEmpty()) {
                    for (Thread thread : threads) {
                        thread.interrupt();
                    }
                    break;
                }
            }
        }
    }

    public List<Runnable> shutdownNow() {
        isShoutDown = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
        System.exit(0);
        return (List<Runnable>) taskQueue;
    }

    class ThreadTask implements Runnable {
        public void run() {
            while (!Thread.interrupted()) {
                synchronized (taskQueue) {
                    if (!taskQueue.isEmpty()) {
                        Runnable runnable = taskQueue.poll();
                        runnable.run();
                    }
                }
            }
        }
    }

}
