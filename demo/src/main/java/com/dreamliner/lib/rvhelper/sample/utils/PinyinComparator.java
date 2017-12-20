package com.dreamliner.lib.rvhelper.sample.utils;


import com.dreamliner.lib.rvhelper.sample.view.decoration.ItemDecorationTag;

import java.util.Comparator;

/**
 * @文件名: PinyinComparator
 * @功能描述: 按照拼音排序
 * @Create by chenzj on 2015-12-15 下午5:45:49
 * @email: chenzj@sq580.com
 * @修改记录:
 */
public class PinyinComparator<T extends ItemDecorationTag> implements Comparator<T> {

    public int compare(T object1, T object2) {
        if (object1.getTag().equals("@") || object2.getTag().equals("#")) {
            return -1;
        } else if (object1.getTag().equals("#") || object2.getTag().equals("@")) {
            return 1;
        } else {
            return object1.getTag().compareTo(object2.getTag());
        }
    }

}
