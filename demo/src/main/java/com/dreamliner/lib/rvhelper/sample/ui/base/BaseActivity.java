package com.dreamliner.lib.rvhelper.sample.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.dreamliner.rvhelper.interfaces.ItemClickListener;

import java.lang.ref.WeakReference;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: BaseActivity
 * @Description: 类的描述 - Act的基类.初始化/跳转工具封装/toast/handler等等
 * @date 2016/6/19 11:32
 * @email admin@chenzhongjin.cn
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void initViews();

    public MyHandler mHandler;

    protected void handleMes(Message msg) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        mHandler = new MyHandler(this);
        initViews();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public static class MyHandler extends Handler {

        private final WeakReference<BaseActivity> mActivity;

        public MyHandler(BaseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity BaseActivity = mActivity.get();
            if (null != BaseActivity) {
                // do someThing
                BaseActivity.handleMes(msg);
            }
        }
    }

    protected Toast mToast;

    public Toast getToast() {
        return mToast;
    }

    /**
     * show toast
     *
     * @param msg
     */
    public void showToast(final String msg) {
        // 防止遮盖虚拟按键
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT);
                    }
                    mToast.setText(msg);
                    mToast.show();
                }
            }
        });
    }

    public void showToast(@StringRes final int resId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(getApplicationContext(), resId, Toast.LENGTH_SHORT);
                }
                mToast.setText(resId);
                mToast.show();
            }
        });
    }

    protected static class ItemClickIml implements ItemClickListener {

        private WeakReference<BaseActivity> mWeakReference;

        public ItemClickIml(BaseActivity baseActivity) {
            mWeakReference = new WeakReference<>(baseActivity);
        }

        @Override
        public void onItemClick(View view, int position) {
            BaseActivity baseActivity = mWeakReference.get();
            if (null != baseActivity) {
                baseActivity.onItemClick(view, position);
            }
        }
    }

    protected void onItemClick(View view, int position) {
    }
}
