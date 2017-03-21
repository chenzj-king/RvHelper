package com.dreamliner.rvhelper.sample.ui.activity.databind;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.adapter.BaseDBAdapter;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.databinding.ActDatabindAllBinding;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.rvhelper.sample.view.DlHeaderView;
import com.dreamliner.rvhelper.sample.view.DlLoadmoreView;

import java.util.ArrayList;
import java.util.Random;

import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;

/**
 * Created by chenzj on 2017/3/21.
 */
public class DataBindActivity extends BaseActivity implements OnRefreshListener, LoadMoreHandler, View.OnClickListener {

    private static final String TAG = "DataBindActivity";

    private ActDatabindAllBinding mActDatabindAllBinding;
    private OptimumRecyclerView mOptimumRecyclerView;
    private BaseDBAdapter<String> mAdapter;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {
        mActDatabindAllBinding = DataBindingUtil.setContentView(this, R.layout.act_databind_all);
        mOptimumRecyclerView = mActDatabindAllBinding.optimumRv;

        mAdapter = new BaseDBAdapter<>(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(View v, int position, String s) {
                showToast(s);
            }
        }, R.layout.item_databind_text);

        mOptimumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerView.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerView.setAdapter(mAdapter);

        //设置下拉刷新
        mOptimumRecyclerView.setRefreshListener(this, new DlHeaderView(this));

        //设置加载更多
        mOptimumRecyclerView.setNumberBeforeMoreIsCalled(1);
        mOptimumRecyclerView.setLoadMoreHandler(this, new DlLoadmoreView(this));

        //设置空白页面中界面的点击事件
        mOptimumRecyclerView.setEmptyOnClick(this);

        getNewData(true);
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
            mOptimumRecyclerView.move(0, false);
            mOptimumRecyclerView.setEmptyType(NET_ERROR);
            autoRefresh(true);
            return true;
        } else if (id == R.id.action_no_result) {
            mOptimumRecyclerView.move(0, false);
            mOptimumRecyclerView.setEmptyType(NO_RESULT);
            autoRefresh(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (null == mOptimumRecyclerView.getOnRefreshListener()) {
            mOptimumRecyclerView.showLoadingView();
            if (!isGoEmpty) {
                getNewData(true);
            }
        } else {
            mOptimumRecyclerView.getPtrLayout().autoRefresh();
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
                mOptimumRecyclerView.loadMoreFinish(false, false);
            } else {
                mOptimumRecyclerView.loadMoreFinish(false, true);
            }
        } else {
            mOptimumRecyclerView.loadMoreFinish(false, true);
        }
    }
}
