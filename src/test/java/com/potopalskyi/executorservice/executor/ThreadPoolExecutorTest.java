package com.potopalskyi.executorservice.executor;

import com.potopalskyi.executorservice.task.WorkerTask;
import org.junit.Test;

public class ThreadPoolExecutorTest {

    @Test
    public void threadPoolExecutorShoutDownTest() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5);
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new WorkerTask());
        }
        threadPoolExecutor.shutdown();
    }

    @Test(expected = RuntimeException.class)
    public void threadPoolExecutorShoutDownExceptionTest() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5);
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new WorkerTask());
        }
        threadPoolExecutor.shutdown();
        threadPoolExecutor.execute(new WorkerTask());
    }

    @Test
    public void threadPoolExecutorShoutDownNowTest() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5);
        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(new WorkerTask());
        }
        threadPoolExecutor.shutdownNow();
    }
}
