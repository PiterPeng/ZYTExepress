package com.yuwubao.zytexpress.helper;


import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

/**
 * Created by mhdt on 2016/11/21.
 * update
 */

public class SwipeToLoadLayoutHelper {
    public static void enableSpeed(SwipeToLoadLayout swipeToLoadLayout) {
        swipeToLoadLayout.setSwipingToRefreshToDefaultScrollingDuration(50);
        swipeToLoadLayout.setRefreshCompleteDelayDuration(75);
        swipeToLoadLayout.setRefreshCompleteToDefaultScrollingDuration(125);
        swipeToLoadLayout.setDefaultToRefreshingScrollingDuration(125);
        swipeToLoadLayout.setDefaultToLoadingMoreScrollingDuration(75);
        swipeToLoadLayout.setReleaseToLoadingMoreScrollingDuration(50);
        swipeToLoadLayout.setLoadMoreCompleteDelayDuration(75);
        swipeToLoadLayout.setLoadMoreCompleteToDefaultScrollingDuration(75);
        swipeToLoadLayout.setReleaseToRefreshingScrollingDuration(50);
        swipeToLoadLayout.setSwipingToLoadMoreToDefaultScrollingDuration(50);
    }
}
