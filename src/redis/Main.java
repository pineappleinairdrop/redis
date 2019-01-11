package redis;


public class Main {
    public static void main(String[] args)  {
        RedisPool redisPool=new RedisPool(17,10,"132.232.175.240",6379);

        //Thread threads[] = new Thread[20];//用以保存线程的引用

        for (int i = 0; i < 20; i++) {
            Thread theI = new Thread(new RedisConnection(redisPool), String.valueOf(i));
            theI.start();
           // threads[i] = theI;//将新建的10个消费者保存
        }


//        for (int i = 0; i < 20; i++) {//发出中断请求
//            threads[i].interrupt();
//
//        }
    }
}
