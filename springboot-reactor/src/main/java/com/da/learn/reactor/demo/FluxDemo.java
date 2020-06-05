package com.da.learn.reactor.demo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Flux: 理解为-非阻塞的Stream
 * Mono: 理解为-非阻塞的Optional
 * Scheduler: 理解为-线程池
 */
public class FluxDemo {
    /**
     * 非阻塞的Stream
     * @param args
     */
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3);
        flux.map(num -> num * 5)     // 将所有数据扩大5倍
                .filter(num -> num > 10)    // 只过滤出数值中超过10的数
                .map(String::valueOf)      // 将数据转为String类型
                .publishOn(Schedulers.elastic())   // 使用弹性线程池来处理数据
                .subscribe(System.out::println);   // 消费数据
    }
}
