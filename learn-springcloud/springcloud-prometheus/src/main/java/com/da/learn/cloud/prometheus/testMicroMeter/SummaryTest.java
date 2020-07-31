package com.da.learn.cloud.prometheus.testMicroMeter;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class SummaryTest {
    public static void main(String[] args) {
        DistributionSummary summary = DistributionSummary.builder("summary")
                .tag("summary", "summary")
                .description("summary")
                .register(new SimpleMeterRegistry());
        summary.record(2D);
        summary.record(3D);
        summary.record(4D);
        System.out.println(summary.measure());
        System.out.println(summary.count());
        System.out.println(summary.max());
        System.out.println(summary.mean());
        System.out.println(summary.totalAmount());
    }
}
