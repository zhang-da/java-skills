package com.da.learn.learnboot.maintainpush;

public class Range<T extends Comparable<? super T>> {
    private Integer stage;

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
