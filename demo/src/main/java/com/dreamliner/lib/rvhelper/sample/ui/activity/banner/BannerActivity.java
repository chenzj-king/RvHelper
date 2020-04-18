package com.dreamliner.lib.rvhelper.sample.ui.activity.banner;

import android.util.SparseIntArray;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.AppContext;
import com.dreamliner.lib.rvhelper.sample.BR;
import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.databinding.ActDbBannerBinding;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.lib.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.adapter.BaseMixtureDBAdapter;
import com.dreamliner.rvhelper.interfaces.OnItemClickListener;
import com.dreamliner.rvhelper.interfaces.OnRefreshListener;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * Created by chenzj on 2017/4/11.
 */

public class BannerActivity extends BaseActivity implements OnItemClickListener<String>, OnRefreshListener, OnBannerListener {

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
        mAdapter.setDecorator((holder, position, viewType) -> holder.getBinding().setVariable(BR.onBannerListener, BannerActivity.this));
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
        mHandler.postDelayed(() -> {
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                strings.add("测试数据 " + (i + 1));
            }
            mAdapter.update(strings);
        }, 1000);
    }

    @Override
    public void onItemClick(View v, int position, String s) {
        showToast("click" + position);
    }

    @Override
    public void OnBannerClick(Object data, int position) {
        showToast("click banner " + data);
    }
}
