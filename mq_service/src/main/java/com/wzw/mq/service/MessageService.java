package com.wzw.mq.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "message",topic = "myTop")
@Slf4j
public class MessageService implements RocketMQListener<String> {
    @Autowired
    WebSocket webSocket;
    @Override
    public void onMessage(String string) {
        log.info("收到消息:{}", string);
        webSocket.sendMessage(string);
    }
}
