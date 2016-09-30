package com.dreamliner.rvhelper.sample.ui.activity.test;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamliner.loadmore.LoadMoreContainer;
import com.dreamliner.loadmore.LoadMoreHandler;
import com.dreamliner.loadmore.LoadMoreRecycleViewContainer;
import com.dreamliner.ptrlib.PtrClassicFrameLayout;
import com.dreamliner.ptrlib.PtrDefaultHandler;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.ptrlib.PtrHandler;
import com.dreamliner.rvhelper.interfaces.ItemClickListener;
import com.dreamliner.rvhelper.sample.AppContext;
import com.dreamliner.rvhelper.sample.R;
import com.dreamliner.rvhelper.sample.ui.activity.main.adapter.TextAdapter;
import com.dreamliner.rvhelper.sample.utils.DividerUtil;
import com.dreamliner.rvhelper.sample.view.CustomizedClickableSpan;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author chenzj
 * @Title: TestActivity
 * @Description: 类的描述 -
 * @date 2016/9/24 09:09
 * @email admin@chenzhongjin.cn
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    @BindView(android.R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.ptr_layout)
    PtrClassicFrameLayout mPtrLayout;
    @BindView(R.id.loadmore_container)
    LoadMoreRecycleViewContainer mLoadmoreContainer;

    @BindView(R.id.empty_viewstub)
    ViewStub mEmptyViewstub;
    @BindView(R.id.progress_viewstub)
    ViewStub mProgressViewstub;

    private boolean isLoading = false;
    private View mProgressView;
    private ImageView mLoadingIv;
    private TextView mLoadingTipTv;

    private View mEmptyView;
    private ImageView mEmptyIv;
    private TextView mEmtptTipTv;

    private TextAdapter mAdapter;

    private MyHandler mHandler;

    private final int NO_RESULT = 1;
    private final int NET_ERROR = 2;

    private int status = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rvhelper);
        ButterKnife.bind(this);

        mHandler = new MyHandler(this);

        mProgressViewstub.setLayoutResource(R.layout.layout_default_progress);
        mProgressView = mProgressViewstub.inflate();
        mLoadingIv = (ImageView) mProgressView.findViewById(R.id.loading_iv);
        mLoadingTipTv = (TextView) mProgressView.findViewById(R.id.loading_tip_tv);
        showProgress();

        mEmptyViewstub.setLayoutResource(R.layout.layout_default_empty);
        mEmptyView = mEmptyViewstub.inflate();
        mEmptyIv = (ImageView) mEmptyView.findViewById(R.id.empty_status_iv);
        mEmtptTipTv = (TextView) mEmptyView.findViewById(R.id.empty_status_tv);

        mAdapter = new TextAdapter(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(TestActivity.this, "click " + (position + 1) + " item", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(DividerUtil.getDefalutDivider(AppContext.getInstance()));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loadEmptyView();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                loadEmptyView();
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                loadEmptyView();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                loadEmptyView();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                loadEmptyView();
            }

            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                loadEmptyView();
            }

            private void loadEmptyView() {
                Log.i(TAG, "loadEmptyView and hideProgress");
                hideProgress();
                if (mAdapter.getItemCount() == 0) {
                    showEmpty();
                } else {
                    hideEmpty();
                }
            }
        });

        mPtrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (isLoading) {
                    return false;
                } else {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
                }
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (status % 3 == NET_ERROR) {
                            mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_net_error));
                            setEmptyTv(mEmtptTipTv, "亲,网络有点差哦 ", "重新加载");
                            showEmpty();
                        } else if (status % 3 == NO_RESULT) {
                            mEmptyIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_status_empty_result));
                            setEmptyTv(mEmtptTipTv, "亲，暂无数据", "");
                            showEmpty();
                        } else {
                            updateData(true);
                        }
                        status += 1;
                        mPtrLayout.refreshComplete();
                    }
                }, 1000);
            }
        });

        mLoadmoreContainer.setRecyclerViewAdapter(mAdapter);
        mLoadmoreContainer.useDefaultFooter();
        mLoadmoreContainer.setAutoLoadMore(true);
        mLoadmoreContainer.setShowLoadingForFirstPage(true);
        mLoadmoreContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                Log.i(TAG, "onLoadMore");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateData(false);
                    }
                }, 1500);
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
                updateData(true);
            }
        }, 3000);
    }

    private void showProgress() {
        isLoading = true;
        mProgressViewstub.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mEmptyViewstub.setVisibility(View.GONE);
        ((AnimationDrawable) mLoadingIv.getDrawable()).start();
    }

    private void hideProgress() {
        isLoading = false;
        mProgressViewstub.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        ((AnimationDrawable) mLoadingIv.getDrawable()).stop();
    }

    private void showEmpty() {
        hideProgress();
        mRecyclerView.setVisibility(View.GONE);
        mEmptyViewstub.setVisibility(View.VISIBLE);
    }

    private void hideEmpty() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyViewstub.setVisibility(View.GONE);
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
            if (mAdapter.getItemCount() > 120) {
                mLoadmoreContainer.loadMoreFinish(false, false);
            } else {
                mLoadmoreContainer.loadMoreFinish(false, true);
            }
        } else {
            mLoadmoreContainer.loadMoreFinish(false, true);
        }
    }

    private void setEmptyTv(TextView textView, String tipStr, String clickStr) {
        textView.setText(tipStr);
        if (!TextUtils.isEmpty(clickStr)) {
            SpannableString spStr = new SpannableString(clickStr);
            ClickableSpan clickSpan = new CustomizedClickableSpan(clickStr, this instanceof View.OnClickListener ?
                    (View.OnClickListener) this : null);
            spStr.setSpan(clickSpan, 0, clickStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            textView.append(spStr);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        mPtrLayout.autoRefresh();
    }

    private static class MyHandler extends Handler {

        private WeakReference<TestActivity> mActivityWeakReference;

        MyHandler(TestActivity activityWeakReference) {
            super(Looper.getMainLooper());
            mActivityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void handleMessage(Message msg) {
            TestActivity testActivity = mActivityWeakReference.get();

            if (null != testActivity) {
                testActivity.handleMessage(msg);
            }
        }

    }

    private void handleMessage(Message msg) {
        //
    }

}
