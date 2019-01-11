package redis;

import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.LinkedList;

/*简单的redis连接池 非线程安全*/
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
   public RedisPool(String host){
        this(host,6379);
    }

    Jedis getJedis() throws RedisPoolIsFullException {
        if (count < maxIdle) {
            count++;
            Jedis tmp = idleJedises.removeFirst();
            return tmp;

        } else if (count < max) {
            Jedis tmp=new Jedis(host, port);
            jedises.add(tmp);
            count++;
            return tmp;

        } else {
            throw new RedisPoolIsFullException("redis连接池已满");
        }
    }

    void close(Jedis jedis) {
        if (count > maxIdle) {
            jedises.remove(jedis);
            jedis.close();
        } else {
            idleJedises.add(jedis);
        }
        count--;
    }
}
