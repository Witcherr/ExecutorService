package com.potopalskyi.executorservice.task;

public class WorkerTask implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread());
    }
}
