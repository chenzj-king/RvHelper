package com.dreamliner.rvhelper.sample.utils;

import java.util.Collection;
import java.util.HashMap;

public class CollectionUtils {

    public static boolean isNotNull(Collection<?> collection) {
        if (collection != null && collection.size() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotNull(HashMap<?, ?> collection) {
        if (collection != null && collection.size() > 0) {
            return true;
        }
        return false;
    }
}
