package com.dreamliner.rvhelper.sample.ui.activity.main;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.activity.main.adapter.MainAdapter;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.rvhelper.sample.view.Sq580HeaderView;
import com.dreamliner.rvhelper.sample.view.Sq580LoadmoreView;

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
public class MainActivity extends BaseActivity implements OnRefreshListener, LoadMoreHandler, View.OnClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerview mOptimumRecyclerview;
    private MainAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_net_error) {
            mOptimumRecyclerview.setEmptyType(NET_ERROR);
            mOptimumRecyclerview.getPtrLayout().autoRefresh();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.clear();
                }
            }, 1000);
            return true;
        } else if (id == R.id.action_no_result) {
            mOptimumRecyclerview.setEmptyType(NO_RESULT);
            mOptimumRecyclerview.getPtrLayout().autoRefresh();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAdapter.clear();
                }
            }, 1000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
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
        mOptimumRecyclerview.setHeaderView(new Sq580HeaderView(this));
        mOptimumRecyclerview.setLoadMoreHandler(this, new Sq580LoadmoreView(this));

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
                updateData(true);
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
        }, 1000);
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
}

