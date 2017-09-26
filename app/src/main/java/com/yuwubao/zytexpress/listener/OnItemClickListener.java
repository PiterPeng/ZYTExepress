package com.yuwubao.zytexpress.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Peng on 2017/9/23
 * e-mail: phlxplus@163.com
 * description:
 */

public interface OnItemClickListener {
    public void onItemClickListener(View view, RecyclerView.ViewHolder holder, int position);
}
