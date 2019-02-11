package com.aaparicio.redis.command;

import java.util.Map;

public class DBSizeMapSupplier implements DBSizeSupplier {

    private final Map map;

    public DBSizeMapSupplier(Map map) {
        this.map = map;
    }

    @Override
    public Integer get() {
        return map.size();
    }
}
