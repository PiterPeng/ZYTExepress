package com.yuwubao.zytexpress.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.InTableBean;
import com.yuwubao.zytexpress.listener.OnItemClickListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Peng on 2017/9/22
 * e-mail: phlxplus@163.com
 * description:
 */

public class DialogHelper {

    /**
     * 带选择的对话框，默认显示控件底部
     *
     * @param c        上下文
     * @param data     填充的内容，默认一行文字
     * @param mView    显示在这个控件底部
     */
    public static void showPopupwindow(Context c, final List<InTableBean.ResultBean> data, TextView mView,
                                       final OnItemClickListener onItemClickListener) {
        final PopupWindow window = new PopupWindow(c);
        View view = LayoutInflater.from(c).inflate(R.layout.item_popupwindow, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.addItemDecoration(new DividerItemDecoration(c, DividerItemDecoration.VERTICAL));
        CommonAdapter adapter = new CommonAdapter<InTableBean.ResultBean>(c, R.layout.simple_list, data) {
            @Override
            protected void convert(ViewHolder holder, InTableBean.ResultBean bean, int position) {
                holder.setText(R.id.content, bean.getName());
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                onItemClickListener.onItemClickListener(view, holder, position);
                window.dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        //设置SelectPicPopupWindow的View
        window.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        window.setWidth(mView.getWidth());
        //设置SelectPicPopupWindow弹出窗体的高
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        window.setFocusable(true);
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        window.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#FFFFFF"));
        //设置SelectPicPopupWindow弹出窗体的背景
        window.setBackgroundDrawable(dw);
        window.showAsDropDown(mView, 0, 0);
    }
}
