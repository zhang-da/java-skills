package com.da.learn.flink;

import com.da.learn.flink.common.utils.JsonUtil;
import com.da.learn.flink.entity.UserEvent;
import com.da.learn.flink.entity.UserEventBy;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousProcessingTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * TumblingEventTimeWindows会缓存一天数据，每次重新计算，内存吃不消
 */
public class PointMain2 {

    public static final DateTimeFormatter TIME_FORMAT_YYYY_MM_DD_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
//        final ParameterTool parameterTool = ExecutionEnvUtil.createParameterTool(args);
//        StreamExecutionEnvironment env = ExecutionEnvUtil.prepare(parameterTool);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  //key 反序列化
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest"); //value 反序列化

        FlinkKafkaConsumer011<String> kafkaConsumer = new FlinkKafkaConsumer011<>("fink-point-source", new SimpleStringSchema(), props);
        kafkaConsumer.setStartFromLatest();

        DataStream<String> detailStream = env.addSource(kafkaConsumer).name("uv-pv_log").disableChaining();
        detailStream.print();

        SingleOutputStreamOperator<Tuple2<UserEvent, Integer>> detail = detailStream.map(new MapFunction<String, Tuple2<UserEvent, Integer>>() {
            @Override
            public Tuple2<UserEvent, Integer> map(String s) throws Exception {
                try {
                    UserEvent userEvent = JsonUtil.fromJson(s, UserEvent.class);
                    return Tuple2.of(userEvent, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return Tuple2.of(null, null);
            }
        }).filter(tuple2 -> tuple2 != null && tuple2.f0 != null).assignTimestampsAndWatermarks(new AscendingTimestampExtractor<Tuple2<UserEvent, Integer>>() {
            @Override
            public long extractAscendingTimestamp(Tuple2<UserEvent, Integer> element) {
                return element.f0.getTimestamp();
            }
        });

        SingleOutputStreamOperator<Tuple2<String, Integer>> result = detail.windowAll(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
//                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .process(new ProcessAllWindowFunction<Tuple2<UserEvent, Integer>, Tuple2<String, Integer>, TimeWindow>() {
                    @Override
                    public void process(Context context, Iterable<Tuple2<UserEvent, Integer>> iterable, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        Set<String> uvNameSet = new HashSet<>();
                        Integer pv = 0;
                        Iterator<Tuple2<UserEvent, Integer>> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            pv += 1;
                            String uvName = iterator.next().f0.getUserId();
                            uvNameSet.add(uvName);
                        }
                        collector.collect(Tuple2.of("uv", uvNameSet.size()));
                        collector.collect(Tuple2.of("pv", pv));
                    }
                });

        result.print();


        result.addSink(new FlinkKafkaProducer011<Tuple2<String, Integer>>(
                "localhost:9092", "fink-point-sink", new SerializationSchema<Tuple2<String, Integer>>() {
            @Override
            public byte[] serialize(Tuple2<String, Integer> tuple2) {
                try {
                    return JsonUtil.toJson(tuple2).getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        })).name("flink-connectors-kafka");

        env.execute("Flink kafka");
    }
}
