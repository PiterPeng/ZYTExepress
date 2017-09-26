package com.yuwubao.zytexpress.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
 * description: 仓库
 */

public class WarehouseActivity extends BaseActivity {
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
        map1.put("image", R.drawable.out);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("image", R.drawable.in);
        Map<String, Integer> map3 = new HashMap<>();
        map3.put("image", R.drawable.pancun);
        Map<String, Integer> map4 = new HashMap<>();
        map4.put("image", R.drawable.yiku);
        data.add(map1);
        data.add(map2);
        data.add(map3);
        data.add(map4);
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
                intent.putExtra(AppConfig.CURRENT_SCAN_TYPE, AppConfig.SCAN_TYPE_CODE_SN);
                switch (position) {
                    case 0://入库
//                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_IN);
//                        showDialog(intent);
                        Intent i = new Intent();
                        i.putExtra("TYPE_IN_OUT", 1);
                        JumpToActivity(NewInSaveActivity.class, i);

                        break;
                    case 1://出库
//                        intent.putExtra(AppConfig.ENTER_TYPE, AppConfig.ENTER_TYPE_OUT);
//                        if (AppConfig.isPDA) {
//                            JumpToActivity(PDAScanActivity.class, intent);
//                        } else {
//                            JumpToActivity(CaptureActivity.class, intent);
//                        }
                        Intent i1 = new Intent();
                        i1.putExtra("TYPE_IN_OUT", 0);
                        JumpToActivity(NewInSaveActivity.class, i1);
                        break;
                    case 2://盘存
                        JumpToActivity(PanCunActivity.class);
                        break;
                    case 3://移库
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

    private void showDialog(final Intent intent) {
        View layoutDialog = LayoutInflater.from(c).inflate(R.layout.dialog, null);
        TextView theory = (TextView) layoutDialog.findViewById(R.id.theory_in);
        TextView fact = (TextView) layoutDialog.findViewById(R.id.fact_in);
        final AlertDialog dialog = new AlertDialog.Builder(c).setTitle("选择入库方式").setView(layoutDialog).show();
        theory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(AppConfig.IN_TYPE, AppConfig.IN_TYPE_THOERY);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                dialog.dismiss();
            }
        });
        fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(AppConfig.IN_TYPE, AppConfig.IN_TYPE_FACT);
                if (AppConfig.isPDA) {
                    JumpToActivity(PDAScanActivity.class, intent);
                } else {
                    JumpToActivity(CaptureActivity.class, intent);
                }
                dialog.dismiss();
            }
        });
    }

    private void setTitle() {
        title.setTitle("仓库吞吐管理（WMS）");
    }
}
