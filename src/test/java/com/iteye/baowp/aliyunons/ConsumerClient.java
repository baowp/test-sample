package com.iteye.baowp.aliyunons;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * Created by Rayburn on 2015/6/23.
 */
public class ConsumerClient {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_37856351-103");
        properties.put(PropertyKeyConst.AccessKey, "xUJQf7xNiSGqucyy");
        properties.put(PropertyKeyConst.SecretKey, "5X4UD2Dixrp5cUjjUPY6uniBPM5wx0");
        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("public-test-1", "*", new MessageListener() {

            public Action consume(Message message, ConsumeContext context) {
                System.out.println(message);
                return Action.CommitMessage;
            }
        });

        consumer.start();

        System.out.println("Consumer Started");
    }


}
