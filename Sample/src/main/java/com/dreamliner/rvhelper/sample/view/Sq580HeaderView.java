package com.dreamliner.rvhelper.sample.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dreamliner.loadingdrawable.LoadingView;
import com.dreamliner.ptrlib.PtrFrameLayout;
import com.dreamliner.ptrlib.PtrUIHandler;
import com.dreamliner.ptrlib.indicator.PtrIndicator;
import com.dreamliner.rvhelper.sample.R;

/**
 * @author chenzj
 * @Title: Sq580HeaderView
 * @Description: 类的描述 -
 * @date 2016/9/9 14:39
 * @email admin@chenzhongjin.cn
 */
public class Sq580HeaderView extends FrameLayout implements PtrUIHandler {

    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;

    private ImageView mLogoIv;
    private LoadingView mOnRefreshView;

    public Sq580HeaderView(Context context) {
        this(context, null);
    }

    public Sq580HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Sq580HeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupViews();
    }


    private void setupViews() {
        buildAnimation();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_sq580_header, this);
        mLogoIv = (ImageView) findViewById(R.id.before_refresh_iv);
        mOnRefreshView = (LoadingView) findViewById(R.id.onfresh_view);
        resetView();
    }

    private void buildAnimation() {
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation
                .RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        hideRotateView();
        mOnRefreshView.setVisibility(INVISIBLE);
    }

    private void hideRotateView() {
        mLogoIv.clearAnimation();
        mLogoIv.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mOnRefreshView.setVisibility(INVISIBLE);
        mLogoIv.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        hideRotateView();
        mOnRefreshView.setVisibility(VISIBLE);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        //hideRotateView();
        mLogoIv.setVisibility(VISIBLE);
        mOnRefreshView.setVisibility(INVISIBLE);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        /*
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
                if (mLogoIv != null) {
                    mLogoIv.clearAnimation();
                    mLogoIv.startAnimation(mReverseFlipAnimation);
                }
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
                if (mLogoIv != null) {
                    mLogoIv.clearAnimation();
                    mLogoIv.startAnimation(mFlipAnimation);
                }
            }
        }*/
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
//            mTitleTextView.setVisibility(VISIBLE);
//            mTitleTextView.setText(com.dreamliner.ptrlib.R.string.cube_ptr_release_to_refresh);
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
//        mTitleTextView.setVisibility(VISIBLE);
//        if (frame.isPullToRefresh()) {
//            mTitleTextView.setText(getResources().getString(com.dreamliner.ptrlib.R.string.cube_ptr_pull_down_to_refresh));
//        } else {
//            mTitleTextView.setText(getResources().getString(com.dreamliner.ptrlib.R.string.cube_ptr_pull_down));
//        }
    }
}
