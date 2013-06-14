package net.zetaeta.util;

import java.util.HashMap;
import java.util.Map;

public class Maps2 {

    public static <K, V> Map<K, V> hashMap(K[] keys, V[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("keys and values must be same length");
        }
        Map<K, V> map = new HashMap<>();
        for (int i=0; i<keys.length; ++i) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
