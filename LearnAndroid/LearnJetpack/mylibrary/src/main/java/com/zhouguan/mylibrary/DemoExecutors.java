package com.zhouguan.mylibrary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoExecutors {

    class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        }
    }

    public void main() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executorService.submit(new Task());
        }
        
        executorService.shutdown();
    }
}
