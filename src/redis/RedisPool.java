package redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 为了理解池化技术实现的简单redis连接池 非线程安全
 * there is an official redis pool:
 *
 * @see redis.clients.jedis.JedisPool
 */

class RedisPool {

    int max;
    private ArrayList<Jedis> jedises;
    private LinkedList<Jedis> idleJedises = new LinkedList<>();
    private int maxIdle;
    int count;
    private String host;
    private int port;

    public RedisPool(int max, int maxIdle, String host, int port) {
        this.max = max;
        jedises = new ArrayList<>(max);
        this.host = host;
        this.port = port;
        this.maxIdle = maxIdle;
        for (int i = 0; i < maxIdle; i++) {
            jedises.add(new Jedis(host, port));
        }
        idleJedises.addAll(jedises);

        count = 0;
    }

    public RedisPool(String host, int port) {

        this(12, 8, host, port);

    }

    public RedisPool(String host) {
        this(host, 6379);
    }

    Jedis getJedis() throws RedisPoolIsFullException {
        if (count < maxIdle) {
            count++;
            return idleJedises.removeFirst();

        } else if (count < max) {
            Jedis tmp = new Jedis(host, port);
            jedises.add(tmp);
            count++;
            return tmp;

        } else {
            throw new RedisPoolIsFullException("redis连接池已满");
        }
    }

    void close(Jedis jedis) throws NoSuchRedisConnection {
        if (jedis == null) throw new NoSuchRedisConnection("当前并未获取连接");
        if (count > maxIdle) {
            jedises.remove(jedis);
            jedis.close();
        } else {
            idleJedises.add(jedis);
        }
        count--;
        jedis = null;

    }

    void releaseRedisPool() {
        for (Jedis ajedis : jedises) {
            try {
                close(ajedis);
            } catch (NoSuchRedisConnection noSuchRedisConnection) {
                noSuchRedisConnection.printStackTrace();
            }
        }
        jedises.clear();
        jedises = null;//help GC
        idleJedises.clear();
        idleJedises = null;//help GC ,2
    }
}
