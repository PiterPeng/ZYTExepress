package com.yuwubao.zytexpress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.InTableBean;

import java.util.List;

/**
 * Created by Peng on 2017/6/26
 * e-mail: phlxplus@163.com
 * description:
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context c;

    private List<InTableBean.ResultBean> inTableBeen;


    public SpinnerAdapter(Context c, List<InTableBean.ResultBean> inTableBeen) {
        this.c = c;
        this.inTableBeen = inTableBeen;
    }

    @Override
    public int getCount() {
        return inTableBeen == null ? 0 : inTableBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return inTableBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(c).inflate(R.layout.item_spinner, null);

        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.content);
            textView.setText(inTableBeen.get(position).getName() + "");
        }

        return convertView;
    }
}
