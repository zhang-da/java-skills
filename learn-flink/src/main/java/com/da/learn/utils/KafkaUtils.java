package com.da.learn.utils;

import com.da.learn.flink.common.utils.JsonUtil;
import com.da.learn.flink.entity.UserEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class KafkaUtils {
    public static final String broker_list = "localhost:9092";
    public static final String topic = "fink-point-source";  // kafka topic

    public static void writeToKafka() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //key 序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value 序列化
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        UserEvent userEvent = new UserEvent();
        userEvent.setTimestamp(System.currentTimeMillis());
        userEvent.setUserId(String.valueOf(new Random().nextInt(10)));
        userEvent.setAppName("app1");
        userEvent.setPageName("page" + new Random().nextInt(10));
        userEvent.setItem(null);
        userEvent.setEvent("open");
        Map<String, String> params = new HashMap<>();
        params.put("color", "black");
        params.put("size", "2");
        userEvent.setParams(params);


        String message = JsonUtil.toJson(userEvent);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, message);
        producer.send(record);
        System.out.println("发送数据" + message);

        producer.flush();
    }

    public static void main(String[] args) throws Exception {
        while (true) {
            Thread.sleep(3000);
            writeToKafka();
        }
    }
}
