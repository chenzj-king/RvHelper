package com.dreamliner.rvhelper.sample.ui.activity.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.activity.main.adapter.MainAdapter;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dreamliner.rvhelper.empty.DefaultEmptyLayout.NET_ERROR;
import static com.dreamliner.rvhelper.empty.DefaultEmptyLayout.NO_RESULT;

/**
 * @author chenzj
 * @Title: MainActivity
 * @Description: 类的描述 -
 * @date 2016/6/12 17:06
 * @email admin@chenzhongjin.cn
 */
public class MainActivity extends AppCompatActivity implements OnRefreshListener, LoadMoreHandler, View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerview mOptimumRecyclerview;
    private MainAdapter mAdapter;

    private MyHandler mHandler;
    private int status = 0;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHandler = new MyHandler(this);

        mAdapter = new MainAdapter(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showToast("click " + (position + 1) + " item");
            }
        });
        mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerview.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerview.setAdapter(mAdapter);
        mOptimumRecyclerview.setRefreshListener(this);
        mOptimumRecyclerview.setNumberBeforeMoreIsCalled(1);
        mOptimumRecyclerview.setLoadMoreHandler(this);

        mOptimumRecyclerview.setEmptyOnClick(this);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData(true);
            }
        }, 1500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_status_tv:
                mOptimumRecyclerview.getPtrLayout().autoRefresh();
                break;
        }
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (status) {
                    case NET_ERROR:
                        mOptimumRecyclerview.setEmptyType(NET_ERROR);
                        mAdapter.clear();
                        status = NO_RESULT;
                        break;
                    case NO_RESULT:
                        mOptimumRecyclerview.setEmptyType(NO_RESULT);
                        mAdapter.clear();
                        status = -1;
                        break;
                    default:
                        updateData(true);
                        status = NET_ERROR;
                        break;
                }
                mOptimumRecyclerview.refreshComplete();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        Log.i(TAG, "onLoadMore- itemCount=" + mAdapter.getItemCount());
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData(false);
            }
        }, 1500);
    }

    protected void updateData(boolean isNeedClear) {
        ArrayList<String> strings = new ArrayList<>();
        int size = new Random().nextInt(50);
        size = size < 15 ? 15 : size;

        int itemCount = mAdapter.getItemCount();
        if (isNeedClear) {
            itemCount = 0;
        }
        for (int i = itemCount; i < size + itemCount; i++) {
            strings.add("测试数据 " + (i + 1));
        }
        if (isNeedClear) {
            mAdapter.update(strings);
            status = NET_ERROR;
        } else {
            mAdapter.addAll(strings);
        }

        if (!isNeedClear) {
            if (mAdapter.getItemCount() > 100) {
                mOptimumRecyclerview.loadMoreFinish(false, false);
            } else {
                mOptimumRecyclerview.loadMoreFinish(false, true);
            }
        } else {
            mOptimumRecyclerview.loadMoreFinish(false, true);
        }
    }

    private static class MyHandler extends Handler {

        private WeakReference<MainActivity> mActivityWeakReference;

        MyHandler(MainActivity activityWeakReference) {
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

    private Toast mToast;

    public void showToast(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(msg)) {
                    if (mToast == null) {
                        mToast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                    mToast.setText(msg);
                    mToast.show();
                }
            }
        });
    }
}

