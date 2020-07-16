package com.da.learn.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    /*
     * create Context
     */
    public static JavaSparkContext createContext() {

        SparkConf sparkConf = new SparkConf().setAppName("FunctionDemo").setMaster("local[*]");

        JavaSparkContext ctx = new JavaSparkContext(sparkConf);

        return ctx;

    }

    public static void main(String[] args) {

        demo5();
    }

    /*
     * RDD1.subtract(RDD2):返回一个新的RDD，内容是：RDD1中存在的，RDD2中不存在的
     */
    public static void demo2() {

        JavaSparkContext ctx = createContext();
        List<String> list1 = new ArrayList<String>();
        list1.add("hello1");
        list1.add("hello2");
        list1.add("hello3");
        list1.add("hello4");

        List<String> list2 = new ArrayList<String>();
        list2.add("hello3");
        list2.add("hello4");
        list2.add("world5");
        list2.add("world6");

        JavaRDD<String> a = ctx.parallelize(list1);
        JavaRDD<String> b = ctx.parallelize(list2);

        a.subtract(b).foreach(new VoidFunction<String>() {
            public void call(String t) throws Exception {
                System.out.println(t.toString());
            }
        });
    }

    /**
     * Created by spark on 15-1-19. RDD1.subtractByKey(RDD2):返回一个新的RDD，内容是：RDD1
     * key中存在的，RDD2 key中不存在的 foreach 结果带key (4, bird) (5, hello) (3, cat) output
     * - (4,bird) (4,bird)
     */

    public static void demo3() {
        JavaSparkContext ctx = createContext();
        JavaRDD<String> a = ctx.parallelize(new ArrayList<String>(Arrays.asList("cat", "hello", "bird", "bird")));
        JavaRDD<String> b = ctx.parallelize(new ArrayList<String>(Arrays.asList("cat", "hello", "testing")));

        JavaPairRDD<Integer, String> c = a.keyBy(new org.apache.spark.api.java.function.Function<String, Integer>() {

            public Integer call(String v1) throws Exception {

                return v1.length();
            }

        });

        // c.foreach(new VoidFunction<Tuple2<Integer,String>>(){
        //
        // public void call(Tuple2<Integer, String> t) throws Exception {
        // // TODO Auto-generated method stub
        // System.out.println("("+t._1+", "+t._2+")");
        // }
        // });

        JavaPairRDD<Integer, String> d = b.keyBy(new org.apache.spark.api.java.function.Function<String, Integer>() {

            public Integer call(String v1) throws Exception {

                return v1.length();
            }

        });

        c.subtract(d).foreach(new VoidFunction<Tuple2<Integer, String>>() {
            public void call(Tuple2<Integer, String> t) throws Exception {
                // TODO Auto-generated method stub
                System.out.println("(" + t._1 + ", " + t._2 + ")");
            }
        });
    }

    /**
     * 取出RDD的前n个元素，以数组的形式返回
     */
    public static void demo4() {
        JavaSparkContext ctx = createContext();
        JavaRDD<String> a = ctx.parallelize(new ArrayList<String>(Arrays.asList("1", "4", "2", "3")));

        List<String> b = a.take(3);

        for (String c : b) {
            System.out.println(c);
        }

    }

    /**
     * 获得前几个最大值 output - hello 3
     */
    public static void demo5() {
        JavaSparkContext ctx = createContext();
        JavaRDD<String> a = ctx.parallelize(new ArrayList<String>(Arrays.asList("1", "hello", "2", "3")));
        List<String> b = a.top(2);
        for (String c : b) {
            System.out.println(c);
        }
    }
}
