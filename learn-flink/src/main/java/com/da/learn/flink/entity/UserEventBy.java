package com.da.learn.flink.entity;

public class UserEventBy {
    private Long start;
    private Long end;
    private String f0;
    private Long count;
    private String f1;

    public UserEventBy() {
    }

    public UserEventBy(Long start, Long end, String f0, Long count, String f1) {
        this.start = start;
        this.end = end;
        this.f0 = f0;
        this.count = count;
        this.f1 = f1;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getF0() {
        return f0;
    }

    public void setF0(String f0) {
        this.f0 = f0;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    @Override
    public String toString() {
        return "UserEventBy{" +
                "start=" + start +
                ", end=" + end +
                ", f0='" + f0 + '\'' +
                ", count=" + count +
                ", f1=" + f1 +
                '}';
    }
}
