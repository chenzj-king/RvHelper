package com.dreamliner.lib.rvhelper.sample.ui.activity.customfooter;

import android.util.Log;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.ui.adapter.TextAdapter;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.lib.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.lib.rvhelper.sample.view.DlLoadmoreView;
import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;

import java.util.ArrayList;
import java.util.Random;

import androidx.recyclerview.widget.LinearLayoutManager;
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
    OptimumRecyclerView mOptimumRecyclerView;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal;
    }

    @Override
    protected void initViews() {

        mAdapter = new TextAdapter(new ItemClickIml(this));
        mOptimumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerView.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerView.setAdapter(mAdapter);

        //设置下拉刷新
        mOptimumRecyclerView.setRefreshListener(this);

        //设置加载更多
        mOptimumRecyclerView.setNumberBeforeMoreIsCalled(1);
        mOptimumRecyclerView.setLoadMoreHandler(this, new DlLoadmoreView(this));

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
        mHandler.postDelayed(() -> {
            //get Data by db/server
            updateData(isNeedClear);
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
        mOptimumRecyclerView.loadSuccess(isNeedClear, strings, 100);
    }

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click item " + (position + 1));
    }
}
