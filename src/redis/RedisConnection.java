package redis;

import redis.clients.jedis.Jedis;


public class RedisConnection implements Runnable {
    private final RedisPool redisPool;
    private Jedis jedis=null;

    RedisConnection(RedisPool redisPool) {
        this.redisPool = redisPool;
    }

    //synchronized
    private void getConnection() throws InterruptedException {
        synchronized (redisPool) {
            if (redisPool.count < redisPool.max) {
                try {
                    jedis = redisPool.getJedis();
                } catch (RedisPoolIsFullException e) {
                    e.printStackTrace();
                }
            }
            redisPool.notify();
            redisPool.wait();
        }
    }

    private void releaseConnection() throws InterruptedException {
        synchronized (redisPool) {
            redisPool.close(jedis);
            redisPool.notify();
            redisPool.wait();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                getConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping());
                System.out.println(Thread.currentThread().getName() + " - " + jedis.ping() + "--");
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("当前未连接");
            }
            try {
                releaseConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
