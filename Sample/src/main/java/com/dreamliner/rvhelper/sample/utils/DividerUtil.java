package com.dreamliner.rvhelper.sample.utils;

import android.content.Context;
import android.graphics.Color;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * @author chenzj
 * @Title: DividerUtil
 * @Description: 类的描述 -
 * @date 2016/8/4 17:35
 * @email admin@chenzhongjin.cn
 */
public class DividerUtil {

    public static HorizontalDividerItemDecoration getDefalutDivider(Context context) {
        return getDefalutDivider(context, 1);
    }

    public static HorizontalDividerItemDecoration getDefalutDivider(Context context, int dpSize) {
        return new HorizontalDividerItemDecoration.Builder(context)
                .color(Color.parseColor("#efeef3"))
                .size(PixelUtil.dp2px(dpSize))
                .build();
    }
}  

