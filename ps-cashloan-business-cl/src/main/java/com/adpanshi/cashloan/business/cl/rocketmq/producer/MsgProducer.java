package com.adpanshi.cashloan.business.cl.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MsgProducer {
    private final Logger logger = LoggerFactory.getLogger(MsgProducer.class);
    private DefaultMQProducer sender;
    protected String nameServer;
    protected String groupName;
    protected String topics;

    public String getNameServer() {
        return nameServer;
    }

    public void setNameServer(String nameServer) {
        this.nameServer = nameServer;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public MsgProducer(String nameServer,String groupName,String topics){
        this.nameServer = nameServer;
        this.groupName = groupName;
        this.topics = topics;
    }

    public void init() {
        sender = new DefaultMQProducer(groupName);
        sender.setNamesrvAddr(nameServer);
        sender.setInstanceName(UUID.randomUUID().toString());
        sender.setVipChannelEnabled(false);
        try {
            sender.start();
        } catch (MQClientException e) {
            logger.error("mq生产者报错"+e.getErrorMessage());
            e.printStackTrace();
        }
    }

//    public MsgProducer(String nameServer, String groupName, String topics) {
//        this.nameServer = nameServer;
//        this.groupName = groupName;
//        this.topics = topics;
//    }

    public void send(Message message) {
        message.setTopic(topics);
        try {
            SendResult result = sender.send(message);
            SendStatus status = result.getSendStatus();
//            System.out.println("messageId=" + result.getMsgId() + ", status=" + status);
            logger.info("messageId=" + result.getMsgId() + ", status=" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
        sender.shutdown();
    }
}
