package redis;

import redis.clients.jedis.Jedis;


public class RedisConnection implements Runnable {
    private final RedisPool redisPool;
    private Jedis jedis = null;

    RedisConnection(RedisPool redisPool) {
        this.redisPool = redisPool;
    }

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

    private void releaseConnection() throws InterruptedException, NoSuchRedisConnection {
        synchronized (redisPool) {
            redisPool.close(jedis);
            redisPool.notify();
            redisPool.wait();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                getConnection();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " Thread main required stop when waiting for getConnection");
            }
            try {
                //make more actions to keep connection alive longer
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
                //e.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " 当前未获取连接");
            }
            try {
                releaseConnection();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " Thread main require stop when waiting releaseConnection");
            } catch (NoSuchRedisConnection noSuchRedisConnection) {
                //noSuchRedisConnection.printStackTrace();
                System.out.println(Thread.currentThread().getName() + " " + noSuchRedisConnection.getMessage());

            }


        }
    }
}
