package redis;

class RedisPoolIsFullException extends Throwable {

    RedisPoolIsFullException(String message) {
        super(message);
    }
}
