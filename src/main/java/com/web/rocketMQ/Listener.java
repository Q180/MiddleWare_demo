package com.web.rocketMQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.StaticSession;
import com.web.webSocket.MyWebSocketHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@NoArgsConstructor
public class Listener {
    @Setter
    private MyWebSocketHandler myWebSocketHandler;
    public void start() throws Exception{
        // 创建消费者实例
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("getAnswer_group", true);
        // 设置NameServer地址
        consumer.setNamesrvAddr("localhost:9876");
        // 订阅消息
        consumer.subscribe("answer_topic", MessageSelector.byTag("tagA"));
        // 注册消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    String info = new String(msg.getBody(), StandardCharsets.UTF_8);
                    JSONObject response = JSON.parseObject(info);
                    try{
                        // myWebSocketHandler.sendMessage(response.getString("sessionID"), response.getString("answer"));
                        StaticSession.getMyWebSocketHandler().sendMessage(response.getString("sessionID"), response.getString("answer"));
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                    System.out.printf("ChatGPT的answer是: %s%n", response.getString("answer"));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
        System.out.println("Consumer started");
        // 关闭消费者
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consumer.shutdown();
        }));
    }
}
