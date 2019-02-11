package com.aaparicio.redis;

public class Range {
    private Integer start;
    private Integer end;

    public Range(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }
}
