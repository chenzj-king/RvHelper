package com.dreamliner.lib.rvhelper.sample.ui.activity.main;

import android.support.v7.widget.GridLayoutManager;
import android.util.SparseArray;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.R;
import com.dreamliner.lib.rvhelper.sample.ui.activity.addressbook.AddressbookActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.banner.BannerActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.customall.CustomAllActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.customempty.CustomEmptyActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.customfooter.CustomFooterActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.customheader.CustomHeaderActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.customloading.CustomLoadingActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.databind.DataBindActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.defaultall.DefaultAllActivity;
import com.dreamliner.lib.rvhelper.sample.ui.activity.gridloadmore.GridLoadMoreActivity;
import com.dreamliner.lib.rvhelper.sample.ui.adapter.TypeAdapter;
import com.dreamliner.lib.rvhelper.sample.ui.base.BaseActivity;
import com.dreamliner.rvhelper.OptimumRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: MainActivity
 * @Description: 类的描述 -
 * @date 2016/6/12 17:06
 * @email admin@chenzhongjin.cn
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.optimum_rv)
    OptimumRecyclerView mOptimumRecyclerView;

    private TypeAdapter mAdapter;

    private SparseArray<String> mStringSparseArray = new SparseArray<>();

    private List<Class<?>> mActivities = new ArrayList<>();

    private final int CUSTOM_LOADING = 0;
    private final int CUSTOM_EMPTY = 1;
    private final int CUSTOM_HEAD = 2;
    private final int CUSTOM_FOOTER = 3;
    private final int DEFAULT_ALL = 4;
    private final int CUSTOM_ALL = 5;
    private final int ADDRESS_BOOK = 6;
    private final int GRID_LOAD_MORE = 7;
    private final int DATA_BINDING = 8;
    private final int BANNER = 9;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        mAdapter = new TypeAdapter(new ItemClickIml(this));
        mOptimumRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mOptimumRecyclerView.setAdapter(mAdapter);

        String[] strings = getResources().getStringArray(R.array.custom_type);
        for (int i = 0; i < strings.length; i++) {
            mStringSparseArray.put(i, strings[i]);
        }
        mAdapter.update(Arrays.asList(strings));

        mActivities.add(CustomLoadingActivity.class);
        mActivities.add(CustomEmptyActivity.class);
        mActivities.add(CustomHeaderActivity.class);
        mActivities.add(CustomFooterActivity.class);
        mActivities.add(DefaultAllActivity.class);
        mActivities.add(CustomAllActivity.class);
        mActivities.add(AddressbookActivity.class);
        mActivities.add(GridLoadMoreActivity.class);
        mActivities.add(DataBindActivity.class);
        mActivities.add(BannerActivity.class);
    }

    @Override
    protected void onItemClick(View view, int position) {
        if (mAdapter.getItemCount() - 1 == position) {
            showToast("持续更新");
        } else {
            readyGo(mActivities.get(position));
        }
    }
}

