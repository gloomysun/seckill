package com.ly.seckill.queue.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.SIMPLE_QUEUE, msg);
    }

    public void sendWork(Object msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.WORK_QUEUE, msg);
    }

    public void sendRouting(Object msg){
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE,"routing.key1",msg+"1");
        rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE,"routing.key2",msg+"2");
    }

    public void sendTopic(Object msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.key1", msg + "1");
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE, "topic.key2", msg + "2");
    }

    public void sendFanout(Object msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", msg + "1");
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", msg + "2");
    }
}
