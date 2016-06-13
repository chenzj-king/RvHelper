package com.dreamliner.rvhelper.sample.ui.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.OnMoreListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.activity.main.adapter.TextAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: MainActivity
 * @Description: 类的描述 -
 * @date 2016/6/12 17:06
 * @email admin@chenzhongjin.cn
 */
public class MainActivity extends AppCompatActivity implements OnRefreshListener, OnMoreListener {

    @BindView(R.id.list)
    OptimumRecyclerview mOptimumRecyclerview;

    private TextAdapter mAdapter;

    private MyHandler mHandler;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new MyHandler(this);

        mAdapter = new TextAdapter();
        mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerview.setAdapter(mAdapter);
        mOptimumRecyclerview.setRefreshListener(this);
        mOptimumRecyclerview.setupMoreListener(this, 1);
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        mAdapter.clear();
        updateData();
        mOptimumRecyclerview.setOnMoreListener(this);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        if (overallItemsCount < 120) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateData();
                }
            }, 1000);

        } else {
            mOptimumRecyclerview.hideMoreProgress();
            mOptimumRecyclerview.removeMoreListener();
        }
    }

    protected void updateData() {
        ArrayList<String> strings = new ArrayList<>();

        int size = (int) (Math.random() * 50);
        size = size < 15 ? 15 : size;
        for (int i = mAdapter.getItemCount(); i < size + mAdapter.getItemCount(); i++) {
            strings.add("测试数据 " + (i + 1));
        }
        mAdapter.addAll(strings);
    }

    private class MyHandler extends Handler {

        private WeakReference<MainActivity> mActivityWeakReference;

        public MyHandler(MainActivity activityWeakReference) {
            mActivityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = mActivityWeakReference.get();

            if (null != mainActivity) {
                mainActivity.handleMessage(msg);
            }
        }
    }

    private void handleMessage(Message msg) {
        //
    }
}

