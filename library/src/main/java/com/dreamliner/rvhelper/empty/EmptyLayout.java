package com.dreamliner.rvhelper.empty;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author chenzj
 * @Title: EmptyLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:08
 * @email admin@chenzhongjin.cn
 */
public abstract class EmptyLayout extends LinearLayout implements EmptyUIHandler {

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
