package com.dreamliner.lib.rvhelper.sample.ui.activity.defaultall;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.ui.adapter.TextAdapter;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.lib.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;


/**
 * @author chenzj
 * @Title: DefaultAllActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 10:57
 * @email admin@chenzhongjin.cn
 */
public class DefaultAllActivity extends BaseActivity implements OnRefreshListener, LoadMoreHandler, View.OnClickListener {

    private static final String TAG = "DefaultAllActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerView mOptimumRecyclerView;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_normal;
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
            mOptimumRecyclerView.move(0);
            mOptimumRecyclerView.setEmptyType(NET_ERROR);
            autoRefresh(true);
            return true;
        } else if (id == R.id.action_no_result) {
            mOptimumRecyclerView.move(0);
            mOptimumRecyclerView.setEmptyType(NO_RESULT);
            autoRefresh(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        mOptimumRecyclerView.setLoadMoreHandler(this);

        //设置空白页面中界面的点击事件
        mOptimumRecyclerView.setEmptyOnClick(this);

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
        mOptimumRecyclerView.getPtrLayout().autoRefresh();
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

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click item " + (position + 1));
    }
}
