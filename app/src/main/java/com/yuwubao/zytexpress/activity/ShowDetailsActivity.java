package com.yuwubao.zytexpress.activity;

import android.widget.TextView;

import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.QueryBean;
import com.yuwubao.zytexpress.widget.HeaderBar;

import butterknife.BindView;

/**
 * Created by Peng on 2017/3/29
 * e-mail: phlxplus@163.com
 * description: 展示商品详情页面
 */

public class ShowDetailsActivity extends BaseActivity {
    @BindView(R.id.title)
    HeaderBar title;
    @BindView(R.id.orderNo)
    TextView orderNo;
    @BindView(R.id.subFaceOrderNo)
    TextView subFaceOrderNo;
    @BindView(R.id.itemCode)
    TextView itemCode;
    @BindView(R.id.itemName)
    TextView itemName;
    @BindView(R.id.oneCode)
    TextView oneCode;
    @BindView(R.id.productCode)
    TextView productCode;
    @BindView(R.id.carNo)
    TextView carNo;
    @BindView(R.id.statusName)
    TextView statusName;
    @BindView(R.id.groosWeight)
    TextView groosWeight;
    @BindView(R.id.volume)
    TextView volume;
    @BindView(R.id.length)
    TextView length;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.height)
    TextView height;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.quantity)
    TextView quantity;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_show_details;
    }

    @Override
    protected void init() {
        setHeader();
        initData();
    }

    private void initData() {
        QueryBean queryBean = (QueryBean) getIntent().getExtras().getSerializable("querybean");
        if (queryBean == null) {
            return;
        }
        QueryBean.ResultBean bean = queryBean.getResult();
        if (bean == null) {
            return;
        }
        try {
            orderNo.setText(bean.getOrderNo());
            subFaceOrderNo.setText(bean.getSubFaceOrderNo());
            itemCode.setText(bean.getItemCode());
            itemName.setText(bean.getItemName());
            oneCode.setText(String.valueOf(bean.getOneCode()));
            productCode.setText(bean.getProductCode());
            carNo.setText(bean.getCarNo());
            statusName.setText(bean.getStatusName());
            groosWeight.setText(String.valueOf(bean.getGroosWeight()));
            volume.setText(String.valueOf(bean.getVolume()));
            length.setText(String.valueOf(bean.getLength()));
            weight.setText(String.valueOf(bean.getWeight()));
            height.setText(String.valueOf(bean.getHeight()));
            color.setText(String.valueOf(bean.getColor()));
            price.setText(String.valueOf(bean.getPrice()));
            quantity.setText(String.valueOf(bean.getQuantity()));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setHeader() {
        title.setTitle("查询结果");
    }
}
