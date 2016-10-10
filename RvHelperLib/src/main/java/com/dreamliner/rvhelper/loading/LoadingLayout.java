package com.dreamliner.rvhelper.loading;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author chenzj
 * @Title: LoadingLayout
 * @Description: 类的描述 -
 * @date 2016/10/8 23:02
 * @email admin@chenzhongjin.cn
 */
public abstract class LoadingLayout extends RelativeLayout implements LoadingUIHandler {

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
