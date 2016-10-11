package com.dreamliner.rvhelper.sample.ui.activity.customfooter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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
import com.dreamliner.rvhelper.sample.view.Sq580LoadmoreView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * @author chenzj
 * @Title: CustomFooterActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 9:42
 * @email admin@chenzhongjin.cn
 */
public class CustomFooterActivity extends BaseActivity implements OnRefreshListener, LoadMoreHandler {

    private static final String TAG = "CustomFooterActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerview mOptimumRecyclerview;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal;
    }

    @Override
    protected void initViews() {

        mAdapter = new TextAdapter(new ItemClickIml(this));
        mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerview.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerview.setAdapter(mAdapter);

        //设置下拉刷新
        mOptimumRecyclerview.setRefreshListener(this);

        //设置加载更多
        mOptimumRecyclerview.setNumberBeforeMoreIsCalled(1);
        mOptimumRecyclerview.setLoadMoreHandler(this, new Sq580LoadmoreView(this));

        getNewData(true);
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //get Data by db/server
                updateData(isNeedClear);
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
