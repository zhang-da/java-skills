package com.da.learn.learnboot.maintainpush.maintain.core;

public class Range<T extends Comparable<? super T>> {
    /**
     * 大段
     * 0：首保
     * 1：第一次定保
     * 2：第二次顶板
     * 。。。。
     */
    private Integer stage;

    /**
     * 小段（时间先后顺序）
     * 1：第一小段
     * 2：第二小段
     */
    private Integer section;

    /**
     * >=
     */
    private T start;

    /**
     * <
     */
    private T end;

    public Range(Integer stage, Integer section, T start, T end) {
        this.stage = stage;
        this.section = section;
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public T getEnd() {
        return end;
    }

    public Integer getStage() {
        return stage;
    }

    public Integer getSection() {
        return section;
    }
}
