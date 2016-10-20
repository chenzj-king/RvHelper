package com.dreamliner.rvhelper.sample.ui.activity.customempty;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.adapter.TextAdapter;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

import static com.dreamliner.rvhelper.util.StatusConstant.NET_ERROR;
import static com.dreamliner.rvhelper.util.StatusConstant.NO_RESULT;


/**
 * @author chenzj
 * @Title: CustomEmptyActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 9:42
 * @email admin@chenzhongjin.cn
 */
public class CustomEmptyActivity extends BaseActivity implements OnRefreshListener, View.OnClickListener {

    @BindView(R.id.optimum_rv)
    OptimumRecyclerview mOptimumRecyclerview;

    private TextAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
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

        mAdapter = new TextAdapter(new ItemClickIml(this));
        mOptimumRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerview.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mOptimumRecyclerview.setAdapter(mAdapter);

        //设置下拉刷新
        mOptimumRecyclerview.setRefreshListener(this);

        //设置空白页面中界面的点击事件
        mOptimumRecyclerview.setEmptyOnClick(this);

        getNewData(false);
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
        mOptimumRecyclerview.getPtrLayout().autoRefresh();
        if (isGoEmpty) {
            getNewData(true);
        }
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        getNewData(false);
    }

    private void getNewData(final boolean clearAdapter) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (clearAdapter) {
                    mAdapter.clear();
                } else {
                    //get Data by db/server
                    updateData();
                }
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
        mOptimumRecyclerview.loadMoreFinish(false, true);
    }

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click " + (position + 1) + " item");
    }
}
