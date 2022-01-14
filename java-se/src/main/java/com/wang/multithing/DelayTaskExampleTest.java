package com.wang.multithing;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.*;

public class DelayTaskExampleTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        for (int i = 0; i < 100; i++) {
            Future<String> future = DelayTaskExample.newInstance().notice(i);
            future.get(); //使用get将线程阻塞避免先执行 stop
        }
        DelayTaskExample.stop();
    }
}

class DelayTaskExample {

    public static final int[] times = {0, 1, 5, 10, 30};

    private final static ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 10, 10L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    private static final DelayTaskExample TASK_EXAMPLE = new DelayTaskExample();


    private DelayTaskExample() {

    }

    public static DelayTaskExample newInstance() {
        return TASK_EXAMPLE;
    }

    public Future<String> notice(Integer id) throws ExecutionException, InterruptedException {
        Future<String> future = THREAD_POOL.submit(() -> {
            String finalRes = null;
            for (int i = 0; i < times.length; i++) {
                try {
                    ScheduledFuture<Integer> schedule = SCHEDULED_EXECUTOR_SERVICE.schedule(this::getResult, times[i], TimeUnit.SECONDS);
                    int res = schedule.get();
                    finalRes = (res == 1 ? "SUCCESS" : "FAIL");
                    System.out.println("通知结果:" + (res == 1 ? "SUCCESS" : "FAIL") + "\t业务ID:" + id + "\t" + Thread.currentThread().getName() + "\t执行次数：" + (i + 1) + "\t结果：" + res + "\t时间：" + LocalDateTime.now());
                    if (res == 1) {
                        break;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            return finalRes;
        });
        return future;
    }

    private int getResult() {
        return new Random().nextInt(5);
    }

    public static void stop() {
        SCHEDULED_EXECUTOR_SERVICE.shutdown();
        THREAD_POOL.shutdown();

    }
}
