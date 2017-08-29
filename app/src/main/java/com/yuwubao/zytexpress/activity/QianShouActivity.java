package com.yuwubao.zytexpress.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.karics.library.zxing.android.CaptureActivity;
import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.widget.HeaderBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Peng on 2017/8/17
 * e-mail: phlxplus@163.com
 * description:签收
 */

public class QianShouActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private CommonAdapter adapter;
    private List<Map<String, Integer>> data;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_qianshou;
    }

    @Override
    protected void init() {
        initData();
        setTitle();
        setRec();
    }

    private void initData() {
        data = new ArrayList<>();
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("image", R.mipmap.scanning);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("image", R.drawable.received);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("image", R.drawable.rejection);
        Map<String, Integer> map4 = new HashMap<>();
        map4.put("image", R.drawable.duanxin);
        Map<String, Integer> map5 = new HashMap<>();
        map5.put("image", R.drawable.daishou);
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
        data.add(map5);
    }

    private void setRec() {
        adapter = new CommonAdapter<Map<String, Integer>>(c, R.layout.item_qianshou, data) {
            @Override
            protected void convert(ViewHolder holder, Map<String, Integer> o, int position) {
                holder.setImageResource(R.id.image, o.get("image"));
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                switch (position) {
                    case 0://中转扫描
                        JumpToActivity(TransferScanActivity.class);
                        break;
                    case 1://签收
                        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SIGN);
                        if (AppConfig.isPDA) {
                            JumpToActivity(PDAScanActivity.class, intent);
                        } else {
                            JumpToActivity(CaptureActivity.class, intent);
                        }
                        break;
                    case 2://拒收
                        intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_REJECTION);
                        if (AppConfig.isPDA) {
                            JumpToActivity(PDAScanActivity.class, intent);
                        } else {
                            JumpToActivity(CaptureActivity.class, intent);
                        }
                        break;
                    case 3:
                        UIHelper.showMessage(c, "待开发");
                        break;
                    case 4:
                        UIHelper.showMessage(c, "待开发");
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(c));
        recyclerView.setAdapter(adapter);
    }

    private void setTitle() {
        title.setTitle("订单签收管理（SMS）");
    }
}
