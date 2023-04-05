package com.cl.code.alarm.util;

import java.util.Map;

/**
 * @author chengliang
 * @since 1.0.0
 */
public class CollectionUtils {

    public static boolean isNullOrEmpty(Iterable<?> iterable) {
        return iterable == null || !iterable.iterator().hasNext();
    }

    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.size() == 0;
    }

}
