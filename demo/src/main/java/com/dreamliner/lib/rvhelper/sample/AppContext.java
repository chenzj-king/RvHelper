package com.dreamliner.lib.rvhelper.sample;

import android.app.Application;

import com.dreamliner.lib.rvhelper.sample.utils.PixelUtil;

/**
 * @author chenzj
 * @Title: AppContext
 * @Description: 类的描述 -
 * @date 2016/6/12 17:05
 * @email admin@chenzhongjin.cn
 */
public class AppContext extends Application {

    private static AppContext mInstance;

    private boolean isDebug;

    public static AppContext getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initDebug();
        initNet();
        PixelUtil.init(this);
        //如初始化net一样.把相关的图片加载框架/Log/其他有debug开关的一些依赖.都可以进行差异化构建
    }

    public boolean isDebug() {
        return isDebug;
    }

    private void initDebug() {
        isDebug = Boolean.parseBoolean(getString(R.string.isDebug));
    }

    private void initNet() {
        if (isDebug()) {
            //使用本地服务器/测试服务器的地址
        } else {
            //使用生成服的服务器地址
        }
    }
}

