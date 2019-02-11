package com.aaparicio.redis.command;


import com.aaparicio.redis.Entry;

import java.util.function.Consumer;

public interface SetConsumer<K, V> extends Consumer<Entry<K, V>> {
}
