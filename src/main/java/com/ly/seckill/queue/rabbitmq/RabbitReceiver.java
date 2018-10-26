package com.ly.seckill.queue.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class RabbitReceiver {
    private static Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    //===================简单模式接收====================//
    @RabbitListener(queues = "simple-queue")
    public void receive(String str) {
        logger.info("=====================》simple-queue Receive:" + str);
    }

    //===================work模式接收====================//
    @RabbitListener(queues = "work-queue")
    public void receiveWork1(String str) {
        logger.info("=====================》receiver1收到work-queue :" + str);
    }

    @RabbitListener(queues = "work-queue")
    public void receiveWork2(String str) {
        logger.info("=====================》receiver2收到work-queue :" + str);
    }

    //===================work模式接收====================//
    @RabbitListener(queues = "routing-queue1")
    public void receiveRouting1(String str) {
        logger.info("=====================》receiver1收到routing-queue1 :" + str);
    }

    @RabbitListener(queues = "routing-queue2")
    public void receiveRouting2(String str) {
        logger.info("=====================》receiver2收到routing-queue2 :" + str);
    }

    //===================主题模式接收====================//
    @RabbitListener(queues = "topic-queue1")
    public void receiveTopic1(String str) {
        logger.info("===============》receiver1收到topic-queue1:" + str);
    }

    @RabbitListener(queues = "topic-queue2")
    public void receiveTopic2(String str) {
        logger.info("===============》receiver2收到topic-queue2 :" + str);
    }

    //===================发布/订阅模式接收====================//
    @RabbitListener(queues = "fanout-queue1")
    public void receiveFanout1(String str) {
        logger.info("============》receiver1收到fanout-queue1:" + str);
    }

    @RabbitListener(queues = "fanout-queue2")
    public void receiveFanout2(String str) {
        logger.info("============》receiver2收到fanout-queue2:" + str);
    }
}
