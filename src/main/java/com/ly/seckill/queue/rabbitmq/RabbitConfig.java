package com.ly.seckill.queue.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    /**
     * rabbitmq共有5种模式 简单模式、work 模式，发布订阅模式、路由模式、主题模式
     * https://www.cnblogs.com/ysocean/p/9251884.html
     * https://blog.csdn.net/u011059021/article/details/79827793
     * 交换器主要有四种类型:direct（路由模式）、fanout（发布订阅模式）、topic（主题模式）、headers（不常用）
     *
     *  简单模式
     * 生产者和消费者，具有相同的交换机名称（Exchange）、交换机类型和相同的密匙（routingKey），那么消费者即可成功获取到消息。
     *
     * work模式
     *  只有一个消费者可以接收到消息
     *
     * 发布订阅模式
     * P发送 M 到X后，所有订阅（通过bindingkey绑定到这个X）的Q都会复制这条M。
     * 如果一个Q对应多个C，rabbitmq中采用轮询round-robin的方法来处理消息
     * 所以其他文章里说发布订阅模式下每个消费者都会收到订阅的消息，这是不对，不严谨的。
     *
     * 路由模式
     * P发送消息到交换机并且要指定Routngkey，消费者将队列绑定到交换机时需要指定Bindingkey。
     *
     * topic模式
     * 即匹配模式,通过匹配交换器，我们可以配置更灵活的消息系统，你可以在匹配交换器模式下发送这样的路由关键字：
     */

    public static String SIMPLE_QUEUE = "simple-queue";

    public static String WORK_QUEUE = "work-queue";

    public static String TOPIC_EXCHANGE = "topic-exchange";
    public static String TOPIC_QUEUE1 = "topic-queue1";
    public static String TOPIC_QUEUE2 = "topic-queue2";

    public static String FANOUT_EXCHANGE = "fanout-exchange";
    public static String FANOUT_QUEUE1 = "fanout-queue1";
    public static String FANOUT_QUEUE2 = "fanout-queue2";

    public static String DIRECT_EXCHANGE = "direct-exchange";
    public static String ROUTING_QUEUE1 = "routing-queue1";
    public static String ROUTING_QUEUE2 = "routing-queue2";


    //===================简单模式=================//
    @Bean
    public Queue queue() {
        return new Queue(SIMPLE_QUEUE, true); //durable 消息是否持久化
    }

    //===============work模式========================//
    @Bean
    public Queue workQueue() {
        return new Queue(WORK_QUEUE, true); //durable 消息是否持久化
    }

    //===============以下是发布订阅模式（fanout）====================//
    @Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1, true);
    }

    @Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE2, true);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    //===============以下是路由模式（direct）====================//

    @Bean
    public Queue routingQueue1() {
        return new Queue(ROUTING_QUEUE1);
    }

    @Bean
    public Queue routingQueue2() {
        return new Queue(ROUTING_QUEUE2);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding directBinding1() {
        return BindingBuilder.bind(routingQueue1()).to(directExchange()).with("routing.key1");
    }

    @Bean
    public Binding directBinding2() {
        return BindingBuilder.bind(routingQueue2()).to(directExchange()).with("routing.key2");
    }

    //===============以下是主题模式（topicExchange）====================//
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBind1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBind2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }
}
