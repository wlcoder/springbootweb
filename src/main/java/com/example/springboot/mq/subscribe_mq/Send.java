package com.example.springboot.mq.subscribe_mq;

import com.example.springboot.util.rabbitmq.MqConnectUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
/*
 * 1个生产者，多个消费者
 *每一个消费者都有自己的一个队列
 *生产者没有将消息直接发送到队列，而是发送到了交换机
 *每个队列都要绑定到交换机
 *生产者发送的消息，经过交换机，到达队列，实现，一个消息被多个消费者获取的目的
 *注意：一个消费者队列可以有多个消费者实例，只有其中一个消费者实例会消费
 * */

public class Send {
    private final static String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = MqConnectUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 消息内容
        String message = "Hello huihui!";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
