package com.da.learn.flink;

import com.da.learn.flink.common.utils.JsonUtil;
import com.da.learn.flink.entity.UserEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.*;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;
import org.apache.flink.util.Collector;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 *
 */
public class PointMain3 {

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

        DataStream<Tuple3<String, String, Integer>> result = detail.keyBy(new KeySelector<Tuple2<UserEvent, Integer>, String>() {
            @Override
            public String getKey(Tuple2<UserEvent, Integer> value) throws Exception {
                Long timestamp = value.f0.getTimestamp();
                LocalDateTime time = LocalDateTime.ofEpochSecond(timestamp / 1000, 0, ZoneOffset.ofHours(8));
                return time.toLocalDate().toString();
            }
        })
                .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
//                .trigger(ContinuousProcessingTimeTrigger.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .evictor(TimeEvictor.of(Time.seconds(0), true))    //每次窗口计算剔除已经消息过数据
                .process(new ProcessWindowFunction<Tuple2<UserEvent, Integer>, Tuple3<String, String, Integer>, String, TimeWindow>() {

                    private transient MapState<String, String> uvCountState;
                    private transient ValueState<Integer> pvCountState;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        super.open(parameters);
                        StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.minutes(60 * 6)).setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite).setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired).build();
                        MapStateDescriptor<String, String> uvDescriptor = new MapStateDescriptor<String, String>("uv_count", String.class, String.class);
                        ValueStateDescriptor<Integer> pvDescriptor= new ValueStateDescriptor<Integer>("pv_count", Integer.class);
                        uvDescriptor.enableTimeToLive(ttlConfig);
                        pvDescriptor.enableTimeToLive(ttlConfig);
                        uvCountState=getRuntimeContext().getMapState(uvDescriptor);
                        pvCountState=getRuntimeContext().getState(pvDescriptor);
                    }


                    @Override
                    public void process(String s, Context context, Iterable<Tuple2<UserEvent, Integer>> iterable, Collector<Tuple3<String, String, Integer>> collector) throws Exception {
                        Integer pv=0;
                        Iterator<Tuple2<UserEvent,Integer>> mapIterator=iterable.iterator();
                        while(mapIterator.hasNext()){
                            pv+=1;
                            UserEvent userEvent=mapIterator.next().f0;
                            String uvName=userEvent.getUserId();
                            uvCountState.put(uvName,null);
                        }
                        Integer uv=0;
                        Iterator<String> uvIterator=uvCountState.keys().iterator();
                        while(uvIterator.hasNext()){
                            uvIterator.next();
                            uv+=1;
                        }
                        Integer originPv=pvCountState.value();
                        if(originPv==null){
                            pvCountState.update(pv);
                        }else{
                            pvCountState.update(originPv+pv);
                        }
                        collector.collect(Tuple3.of(s,"uv",uv));
                        collector.collect(Tuple3.of(s,"pv",pvCountState.value()));
                    }
                });

        result.print();


        result.addSink(new FlinkKafkaProducer011<Tuple3<String, String, Integer>>(
                "localhost:9092", "fink-point-sink", new SerializationSchema<Tuple3<String, String, Integer>>() {
            @Override
            public byte[] serialize(Tuple3<String, String, Integer> tuple2) {
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
