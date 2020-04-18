package com.dreamliner.rvhelper.util;


import java.util.Arrays;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.dreamliner.rvhelper.util.LayoutManagerUtil.LAYOUT_MANAGER_TYPE.GRID;
import static com.dreamliner.rvhelper.util.LayoutManagerUtil.LAYOUT_MANAGER_TYPE.LINEAR;
import static com.dreamliner.rvhelper.util.LayoutManagerUtil.LAYOUT_MANAGER_TYPE.STAGGERED_GRID;

/**
 * @author chenzj
 * @Title: LayoutManagerUtil
 * @Description: 类的描述 -
 * @date 2016/10/9 10:36
 * @email admin@chenzhongjin.cn
 */
public class LayoutManagerUtil {

    public static int getFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int firstVisibleItemPosition = -1;

        switch (getLayoutManagerType(layoutManager)) {
            case LINEAR:
            case GRID:
                firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                firstVisibleItemPosition = caseStaggeredGrid(layoutManager, true);
                break;
        }
        return firstVisibleItemPosition;
    }

    public static int getLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int lastVisibleItemPosition = -1;
        switch (getLayoutManagerType(layoutManager)) {
            case LINEAR:
            case GRID:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                lastVisibleItemPosition = caseStaggeredGrid(layoutManager, false);
                break;
        }
        return lastVisibleItemPosition;
    }

    private static LAYOUT_MANAGER_TYPE getLayoutManagerType(RecyclerView.LayoutManager layoutManager) {
        LAYOUT_MANAGER_TYPE layoutManagerType;

        if (layoutManager instanceof GridLayoutManager) {
            layoutManagerType = GRID;
        } else if (layoutManager instanceof LinearLayoutManager) {
            layoutManagerType = LINEAR;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutManagerType = STAGGERED_GRID;
        } else {
            throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager " +
                    "and StaggeredGridLayoutManager");
        }
        return layoutManagerType;
    }

    private static int caseStaggeredGrid(RecyclerView.LayoutManager layoutManager, boolean findFirst) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];

        if (findFirst) {
            staggeredGridLayoutManager.findFirstVisibleItemPositions(positions);
            Arrays.sort(positions);
            return positions[0];
        } else {
            staggeredGridLayoutManager.findLastVisibleItemPositions(positions);
            Arrays.sort(positions);
            return positions[positions.length - 1];
        }
    }

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }
}
