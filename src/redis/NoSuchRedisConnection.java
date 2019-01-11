package redis;

public class NoSuchRedisConnection extends Throwable {
    NoSuchRedisConnection(String msg) {
        super(msg);
    }
}
