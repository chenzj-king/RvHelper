package com.dreamliner.rvhelper.sample.ui.activity.customheader;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerview;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.adapter.TextAdapter;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.rvhelper.sample.view.Sq580HeaderView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;

/**
 * @author chenzj
 * @Title: CustomHeaderActivity
 * @Description: 类的描述 -
 * @date 2016/10/11 9:40
 * @email admin@chenzhongjin.cn
 */
public class CustomHeaderActivity extends BaseActivity implements OnRefreshListener {

    private static final String TAG = "CustomHeaderActivity";

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
        mOptimumRecyclerview.setRefreshListener(this, new Sq580HeaderView(this));
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
        mOptimumRecyclerview.loadMoreFinish(false, true);
    }

    @Override
    protected void onItemClick(View view, int position) {
        showToast("click " + (position + 1) + " item");
    }
}
