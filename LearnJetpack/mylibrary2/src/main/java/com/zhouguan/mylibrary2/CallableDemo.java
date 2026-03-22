package com.zhouguan.mylibrary2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<Integer>{
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}

public class CallableDemo {
    public static void main(String[] args) {
        MyCallable myCallable = new MyCallable();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(myCallable);
        Thread t = new Thread(futureTask);

        t.start();

        try{
            Integer result = futureTask.get();
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
