package com.dreamliner.rvhelper.sample.ui.activity.customall;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.adapter.TextAdapter;
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
 * @Title: CustomAllActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 10:38
 * @email admin@chenzhongjin.cn
 */
public class CustomAllActivity extends BaseActivity implements OnRefreshListener, LoadMoreHandler, View.OnClickListener {

    private static final String TAG = "CustomAllActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerview mOptimumRecyclerview;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all;
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
            mOptimumRecyclerview.move(0, false);
            mOptimumRecyclerview.setEmptyType(NET_ERROR);
            autoRefresh(true);
            return true;
        } else if (id == R.id.action_no_result) {
            mOptimumRecyclerview.move(0, false);
            mOptimumRecyclerview.setEmptyType(NO_RESULT);
            autoRefresh(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        mAdapter = new TextAdapter(new ItemClickIml(this));
        mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerview.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerview.setAdapter(mAdapter);

        //设置下拉刷新
        mOptimumRecyclerview.setRefreshListener(this, new Sq580HeaderView(this));

        //设置加载更多
        mOptimumRecyclerview.setNumberBeforeMoreIsCalled(1);
        mOptimumRecyclerview.setLoadMoreHandler(this, new Sq580LoadmoreView(this));

        //设置空白页面中界面的点击事件
        mOptimumRecyclerview.setEmptyOnClick(this);

        getNewData(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty_status_tv:
                autoRefresh(false);
                break;
        }
    }

    private void autoRefresh(boolean isGoEmpty) {
        //根据具体业务需求.现在做法是如果不需要下拉刷新的话.那么全部加载中都用loadingView来进行交互.否则就是用下拉刷新的样式.
        //自己在项目中抉择选择什么方式即可.
        if (null == mOptimumRecyclerview.getOnRefreshListener()) {
            mOptimumRecyclerview.showLoadingView();
            if (!isGoEmpty) {
                getNewData(true);
            }
        } else {
            mOptimumRecyclerview.getPtrLayout().autoRefresh();
        }
        if (isGoEmpty) {
            getNewData(false, true);
        }
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        getNewData(true);
    }

    @Override
    public void onLoadMore(LoadMoreContainer loadMoreContainer) {
        Log.i(TAG, "onLoadMore- itemCount=" + mAdapter.getItemCount());
        getNewData(false);
    }

    private void getNewData(final boolean isNeedClear) {
        getNewData(isNeedClear, false);
    }

    private void getNewData(final boolean isNeedClear, final boolean clearAdapter) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (clearAdapter) {
                    mAdapter.clear();
                } else {
                    //get Data by db/server
                    updateData(isNeedClear);
                }
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

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click item " + (position + 1));
    }
}
