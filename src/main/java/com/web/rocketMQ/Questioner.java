package com.web.rocketMQ;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.nio.charset.StandardCharsets;

public class Questioner {
    public Questioner(){
    }
    public void sendQuestion(String question,int index) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("sendQuestion_group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();
        JSONObject jsonObject = new JSONObject();
        Message msg =
                new Message("question_topic", "tagA", question.getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 设置消息唯一标识符，用于后续消息回溯
        msg.setKeys("KEY_" + index);
        // 发送消息并获取消息ID
        String msgId = producer.send(msg).getMsgId();
        System.out.print("消息ID:");
        System.out.print(msgId);
        System.out.println(",内容:" + new String(msg.getBody(), StandardCharsets.UTF_8));
        // 关闭生产者
        producer.shutdown();
    }
}
