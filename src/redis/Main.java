package redis;


import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        RedisPool redisPool = new RedisPool(18, 10, "132.232.175.240", 6379);

        //  Thread threads[] = new Thread[20];//用以保存线程的引用 to save the threads

        for (int i = 0; i < 20; i++) {
            Thread thread = new Thread(new RedisConnection(redisPool), String.valueOf(i));
            thread.start();
            // threads[i] = thread;
        }

//        try {
//            TimeUnit.SECONDS.sleep(20);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < 20; i++) {//发出中断请求 interrupt request
//            threads[i].interrupt();
//
//        }
//        redisPool.releaseRedisPool();//gracefully release source
    }
}
