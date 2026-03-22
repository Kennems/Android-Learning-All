package com.zhouguan.mylibrary;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoMap {
    @Test
    public void main() {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        map.put("key4", 4);
        map.put("key5", 5);
        System.out.println(map.get("key1"));
        System.out.println(map.get("key6"));

        System.out.println(map.getOrDefault("key6", 0));
        /**
         * 1
         * null
         * 0
         */
//        ExecutorService executor = Executors.newFixedThreadPool(2);

//        executor.submit(() -> {
//            for (int i = 0; i < 5; i++) {
//                map.put("key" + i, i);
//                System.out.println(Thread.currentThread().getName() + " put: key" + i + " -> " + i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        executor.submit(() -> {
//            for (int i = 0; i < 5; i++) {
//                Integer value = map.get("key" + i);
//                System.out.println(Thread.currentThread().getName() + " get: key" + i + " -> " + value);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        executor.shutdown();
    }
}
