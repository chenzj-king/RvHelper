package com.dreamliner.rvhelper.sample.ui.activity.main;

import android.support.v7.widget.GridLayoutManager;
import android.util.SparseArray;
import android.view.View;

import com.dreamliner.rvhelper.OptimumRecyclerView;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.activity.addressbook.AddressbookActivity;
import com.dreamliner.rvhelper.sample.ui.activity.customall.CustomAllActivity;
import com.dreamliner.rvhelper.sample.ui.activity.customempty.CustomEmptyActivity;
import com.dreamliner.rvhelper.sample.ui.activity.customfooter.CustomFooterActivity;
import com.dreamliner.rvhelper.sample.ui.activity.customheader.CustomHeaderActivity;
import com.dreamliner.rvhelper.sample.ui.activity.customloading.CustomLoadingActivity;
import com.dreamliner.rvhelper.sample.ui.activity.defaultall.DefaultAllActivity;
import com.dreamliner.rvhelper.sample.ui.activity.gridloadmore.GridLoadMoreActivity;
import com.dreamliner.rvhelper.sample.ui.adapter.TypeAdapter;
import com.dreamliner.rvhelper.sample.ui.base.BaseActivity;

import java.util.Arrays;

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

    private final int CUSTOM_LOADING = 0;
    private final int CUSTOM_EMPTY = 1;
    private final int CUSTOM_HEAD = 2;
    private final int CUSTOM_FOOTER = 3;
    private final int DEFAUTL_ALL = 4;
    private final int CUSTOM_ALL = 5;
    private final int ADDRESS_BOOK = 6;
    private final int GRID_LOADMORE = 7;

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
    }

    @Override
    protected void onItemClick(View view, int position) {

        switch (mStringSparseArray.indexOfValue(mAdapter.getItem(position))) {
            case CUSTOM_LOADING:
                readyGo(CustomLoadingActivity.class);
                break;
            case CUSTOM_EMPTY:
                readyGo(CustomEmptyActivity.class);
                break;
            case CUSTOM_HEAD:
                readyGo(CustomHeaderActivity.class);
                break;
            case CUSTOM_FOOTER:
                readyGo(CustomFooterActivity.class);
                break;
            case DEFAUTL_ALL:
                readyGo(DefaultAllActivity.class);
                break;
            case CUSTOM_ALL:
                readyGo(CustomAllActivity.class);
                break;
            case ADDRESS_BOOK:
                readyGo(AddressbookActivity.class);
                break;
            case GRID_LOADMORE:
                readyGo(GridLoadMoreActivity.class);
                break;
            default:
                showToast("持续更新");
                break;
        }
    }
}

