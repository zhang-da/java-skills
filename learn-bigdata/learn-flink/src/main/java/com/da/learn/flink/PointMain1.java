package com.da.learn.flink;

import com.da.learn.flink.common.utils.JsonUtil;
import com.da.learn.flink.entity.UserEvent;
import com.da.learn.flink.entity.UserEventBy;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Properties;

public class PointMain1 {
    public static void main(String[] args) throws Exception {
//        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
//        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        long MAX_EVENT_DELAY = 3500;
        BoundedOutOfOrdernessTimestampExtractor<String> assigner = new BoundedOutOfOrdernessTimestampExtractor<String>(Time.milliseconds(MAX_EVENT_DELAY)) {
            @Override
            public long extractTimestamp(String element) {
                UserEvent userEvent = null;
                try {
                    userEvent = JsonUtil.fromJson(element, UserEvent.class);
                    return userEvent.getTimestamp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Instant.now().toEpochMilli();
            }
        };


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); //value 反序列化

        FlinkKafkaConsumer011<String> kafkaConsumer = new FlinkKafkaConsumer011<>("fink-point-source", new SimpleStringSchema(), props);
        kafkaConsumer.setStartFromEarliest();




        FlinkKafkaConsumerBase<String> consumerWithEventTime = kafkaConsumer.assignTimestampsAndWatermarks(assigner);


        DataStreamSource<String> dataStreamByEventTime = env.addSource(consumerWithEventTime).setParallelism(1);

        dataStreamByEventTime.print(); //把从 kafka 读取到的数据打印在控制台


        TypeInformation<Tuple3<String, UserEvent, String>> typeInformation = TypeInformation.of(new TypeHint<Tuple3<String, UserEvent, String>>() {
        });
        SingleOutputStreamOperator<UserEventBy> uvCounter = dataStreamByEventTime
                .map(s -> JsonUtil.fromJson(s, UserEvent.class))
                .map(userEvent -> new Tuple3<>(userEvent.getPageName(), userEvent, userEvent.getUserId()))
                .returns(typeInformation)
                .keyBy(0, 2)
                .window(SlidingProcessingTimeWindows.of(Time.minutes(5), Time.minutes(1), Time.seconds(-59)))
                .allowedLateness(Time.minutes(1))
                .process(new ProcessWindowFunction<Tuple3<String, UserEvent, String>, UserEventBy, Tuple, TimeWindow>() {
                    @Override
                    public void process(Tuple tuple, Context context, Iterable<Tuple3<String, UserEvent, String>> elements, Collector<UserEventBy> out) throws Exception {
                        long count = 0;
                        Tuple2<String, String> tuple2 = null;
                        if (tuple instanceof Tuple2) {
                            tuple2 = (Tuple2) tuple;
                        }
                        for (Tuple3<String, UserEvent, String> element : elements) {
                            count++;
                        }
                        ;
                        TimeWindow window = context.window();
                        out.collect(new UserEventBy(window.getStart(), window.getEnd(), tuple2.f0, count, tuple2.f1));
                    }
                });
        uvCounter.print();


        uvCounter.addSink(new FlinkKafkaProducer011<UserEventBy>(
                "localhost:9092", "fink-point-sink", new SerializationSchema<UserEventBy>() {
            @Override
            public byte[] serialize(UserEventBy userEventBy) {
                try {
                    return JsonUtil.toJson(userEventBy).getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        )).name("flink-connectors-kafka");

        env.execute("Flink kafka");
    }
}
