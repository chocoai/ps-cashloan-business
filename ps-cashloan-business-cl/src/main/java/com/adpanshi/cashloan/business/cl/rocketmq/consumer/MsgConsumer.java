package com.adpanshi.cashloan.business.cl.rocketmq.consumer;

import com.adpanshi.cashloan.business.cl.domain.Sms;
import com.adpanshi.cashloan.business.cl.mapper.SmsMapper;
import com.adpanshi.cashloan.business.core.chuanglan.SmsApi;
import com.adpanshi.cashloan.business.core.common.util.DateUtil;
import com.adpanshi.cashloan.business.core.common.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MsgConsumer {
    private final Logger logger = LoggerFactory.getLogger(MsgConsumer.class);
    private DefaultMQPushConsumer consumer;
//    private MessageListener listener;
    protected String nameServer;
    protected String groupName;
    protected String topics;
    @Resource
    private SmsMapper smsMapper;

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



//    public MsgConsumer(String nameServer, String groupName, String topics) {
////        this.listener = listener;
//        this.nameServer = nameServer;
//        this.groupName = groupName;
//        this.topics = topics;
//    }

    public void init() throws InterruptedException, MQClientException {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameServer);
        try {
            consumer.subscribe(topics, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.setInstanceName(UUID.randomUUID().toString());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//        consumer.registerMessageListener((MessageListenerConcurrently) mqListener);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                SmsApi api = new SmsApi();
                for (MessageExt message : msgs) {
                    String msg = new String(message.getBody());
                    logger.info("msg data from rocketMQ:" + msg);
                    if(msg.contains("mobile")){
                        JSONObject object = JSON.parseObject(msg);
                        Map<String,Object> map = new HashMap<>();
                        map.put("message",object.get("message"));
                        map.put("mobile",object.get("mobile"));
                        map.put("vcode",object.get("vcode"));
                        map.put("orderNo",object.get("orderNo"));
                        String result = api.sendMsg(map);
                        JSONObject resultJson = JSONObject.parseObject(result);
                        String phoneOrEmail = (String) object.get("mobile");
                        String type = (String) object.get("type");
                        logger.info("------------->result:{},phone:{},type={}",new Object[]{result,phoneOrEmail,type});
                        if(null!=resultJson && !resultJson.isEmpty()){
                            Sms sms = new Sms();
                            if(StringUtil.isMail(phoneOrEmail)){
                                sms.setEmail(phoneOrEmail);
                            }else{
                                sms.setPhone(phoneOrEmail);
                            }
                            sms.setSendTime(DateUtil.getNow());
                            sms.setSmsType(type);
                            sms.setContent(resultJson.getString("content"));
                            sms.setCode(resultJson.getString("vcode"));
                            sms.setOrderNo(resultJson.getString("orderNo"));
                            if(resultJson.getString("msgid")!=null)
                                sms.setMsgid(resultJson.getString("msgid"));
                            sms.setVerifyTime(0);
                            if (resultJson.getInteger("code") == 0) {
                                sms.setContent(resultJson.getString("content"));
                                sms.setResp("短信已发送");
                                sms.setState("10");
                            }else{
                                String content = resultJson.getString("error");
                                logger.error("[新短信平台发送异常][phone:"+phoneOrEmail+",异常信息:"+content+"]");
                                sms.setResp(content);
                                sms.setState("20");
                            }
                            logger.info(JSONObject.toJSONString(sms));
                            logger.info("保存结果为:"+smsMapper.save(sms));
                        }
                    }
                }

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
//        consumer.setVipChannelEnabled(false);
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        logger.info("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + " instance=" + consumer.getInstanceName());
//        System.out.println("RocketMQConsumer Started! group=" + consumer.getConsumerGroup() + " instance=" + consumer.getInstanceName());
    }
    public void destroy() {
        consumer.shutdown();
    }
}
