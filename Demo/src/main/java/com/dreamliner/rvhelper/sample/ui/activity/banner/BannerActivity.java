package com.dreamliner.rvhelper.sample.ui.activity.banner;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseIntArray;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.adapter.BaseDataDBAdapter;
import com.dreamliner.rvhelper.adapter.BaseMixtureDBAdapter;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.databinding.ActDbBannerBinding;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.rvhelper.viewholder.BaseBindViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by chenzj on 2017/4/11.
 */

public class BannerActivity extends BaseActivity implements OnItemClickListener<String>, OnRefreshListener, BaseSliderView
        .OnSliderClickListener {

    private ActDbBannerBinding mActDbBannerBinding;

    private OptimumRecyclerView mOptimumRecyclerView;
    private BaseMixtureDBAdapter<String> mAdapter;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {
        mActDbBannerBinding = DataBindingUtil.setContentView(this, R.layout.act_db_banner);
        mOptimumRecyclerView = mActDbBannerBinding.optimumRv;

        mOptimumRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mOptimumRecyclerView.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));

        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(1, R.layout.item_db_banner);
        sparseIntArray.put(2, R.layout.item_databind_text);
        mAdapter = new BaseMixtureDBAdapter<String>(this, sparseIntArray) {
            @Override
            public int getItemViewType(int position) {
                if (position == 0) {
                    return 1;
                }
                return 2;
            }
        };
        mAdapter.setDecorator(new BaseDataDBAdapter.Decorator() {
            @Override
            public void decorator(BaseBindViewHolder holder, int position, int viewType) {
                holder.getBinding().setVariable(BR.onSliderClick, BannerActivity.this);
            }
        });
        mOptimumRecyclerView.setAdapter(mAdapter);

        mOptimumRecyclerView.getRecyclerView().setOverScrollMode(OVER_SCROLL_NEVER);
        mOptimumRecyclerView.setRefreshListener(this);
        mOptimumRecyclerView.getPtrLayout().disableWhenHorizontalMove(true);
        getData();
    }

    @Override
    public void onRefresh(PtrFrameLayout ptrFrameLayout) {
        getData();
    }

    private void getData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> strings = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    strings.add("测试数据 " + (i + 1));
                }
                mAdapter.update(strings);
            }
        }, 1000);
    }

    @Override
    public void onItemClick(View v, int position, String s) {
        showToast("click" + position);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        showToast("click slider" + slider.getUrl());
    }
}
