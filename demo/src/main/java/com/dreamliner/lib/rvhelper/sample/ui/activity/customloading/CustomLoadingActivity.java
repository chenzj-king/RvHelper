package com.dreamliner.lib.rvhelper.sample.ui.activity.customloading;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.ui.adapter.TextAdapter;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.lib.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * @author chenzj
 * @Title: CustomLoadingActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 9:42
 * @email admin@chenzhongjin.cn
 */
public class CustomLoadingActivity extends BaseActivity implements OnRefreshListener {

    private static final String TAG = "CustomLoadingActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerView mOptimumRecyclerView;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_loading;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_try_loading) {
            mOptimumRecyclerView.showLoadingView();
            getNewData();
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
        getNewData();
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        getNewData();
    }

    private void getNewData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //get Data by db/server
                updateData();
            }
        }, 1000);
    }

    protected void updateData() {

        ArrayList<String> strings = new ArrayList<>();
        int size = new Random().nextInt(50);
        size = size < 15 ? 15 : size;
        int itemCount = 0;
        for (int i = itemCount; i < size + itemCount; i++) {
            strings.add("测试数据 " + (i + 1));
        }

        mAdapter.update(strings);
        mOptimumRecyclerView.loadMoreFinish(false, true);
    }

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click " + (position + 1) + " item");
    }
}
