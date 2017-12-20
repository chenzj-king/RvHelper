package com.dreamliner.lib.rvhelper.sample.view.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.dreamliner.lib.rvhelper.sample.utils.PixelUtil;

import java.util.List;


/**
 * 有分类title的 ItemDecoration
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class TitleItemDecoration<T extends ItemDecorationTag> extends RecyclerView.ItemDecoration {

    private String mTopTag;
    private List<T> mDatas;
    private Paint mPaint;
    private Rect mBounds;//用于存放测量文字Rect

    private int mTitleHeight;//title的高
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF000000");


    public TitleItemDecoration(Context context, List<T> datas, String topTag) {
        super();

        mDatas = datas;
        mTopTag = topTag;
        mPaint = new Paint();
        mBounds = new Rect();
        mTitleHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        int titleFontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                context.getResources().getDisplayMetrics());
        mPaint.setTextSize(titleFontSize);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = params.getViewLayoutPosition();

            if (position != RecyclerView.NO_POSITION) {

                String currentTag = mDatas.get(position).getTag();
                if (!currentTag.equals(mTopTag) && (position == 0 || !currentTag.equals(mDatas.get(position - 1).getTag()))) {
                    //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                    drawTitleArea(c, left, right, child, params, position);
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {
        //最先调用，绘制在最下层
        mPaint.setColor(COLOR_TITLE_BG);
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        mPaint.setColor(COLOR_TITLE_FONT);
        /*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        */
        mPaint.getTextBounds(mDatas.get(position).getTag(), 0, mDatas.get(position).getTag().length(), mBounds);

        int textPaddingLeft = PixelUtil.dp2px(12);
        c.drawText(mDatas.get(position).getTag(), textPaddingLeft, child.getTop() - params.topMargin - (mTitleHeight / 2 -
                mBounds.height() / 2), mPaint);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //最后调用 绘制在最上层
//        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
//
//        String tag = mDatas.get(pos).getTag();
//        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
//        mPaint.setColor(COLOR_TITLE_BG);
//        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent
//                .getPaddingTop() + mTitleHeight, mPaint);
//        mPaint.setColor(COLOR_TITLE_FONT);
//        mPaint.getTextBounds(tag, 0, tag.length(), mBounds);
//        c.drawText(tag, child.getPaddingLeft(),
//                parent.getPaddingTop() + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2),
//                mPaint);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if (position != RecyclerView.NO_POSITION) {

            String currentTag = mDatas.get(position).getTag();
            if (currentTag.equals(mTopTag)) {
                outRect.set(0, 0, 0, 0);
            } else if (position == 0) {
                outRect.set(0, mTitleHeight, 0, 0);
            } else {
                String previewTag = mDatas.get(position - 1).getTag();
                if (!currentTag.equals(previewTag)) {
                    outRect.set(0, mTitleHeight, 0, 0);
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }
        }
    }

}
