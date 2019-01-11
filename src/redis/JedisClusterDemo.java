package redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.LinkedHashSet;
import java.util.Set;

public class JedisClusterDemo {
    public static void main(String[] args) {
        Set<HostAndPort> hostAndPorts = new LinkedHashSet<>();
        LinkedHashSet<HostAndPort> receive = new LinkedHashSet<>();
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7000));
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7001));
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7002));
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7003));
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7004));
        hostAndPorts.add(new HostAndPort("132.232.175.240", 7005));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        Gson gson=new Gson();
        jedisCluster.set("nodes",gson.toJson(hostAndPorts));
        System.out.println(jedisCluster.get("nodes"));

       receive=gson.fromJson(jedisCluster.get("nodes"),new TypeToken<LinkedHashSet<HostAndPort>>(){}.getType());
        System.out.println(" ");

    }
}
