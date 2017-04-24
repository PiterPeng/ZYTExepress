package com.yuwubao.zytexpress.frag;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuwubao.zytexpress.AppManager;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.activity.LoginActivity;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Peng on 2017/3/8
 * e-mail: phlxplus@163.com
 * description: 我的
 */

public class MineFragment extends BaseFragement {
    @BindView(R.id.headerImg)
    ImageView headerImg;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.account)
    TextView tvAccount;
    @BindView(R.id.companyName)
    TextView companyName;
    String name, account, company;
    Unbinder unbinder;

    @Override
    protected int getContentResourseId() {
        return R.layout.frag_mine;
    }

    @Override
    protected void init() {
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            account = user.getAccount();
            company = user.getCompanyName();
            name = user.getName();
            tvAccount.setText(account);
            companyName.setText(company);
            userName.setText(name);
        }
    }

    @OnClick(R.id.switch_Account)
    public void onViewClicked() {
        UIHelper.showMyCustomDialog(c, "确定切换账号？", "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishAllActivity();
                UserDao.getInstance().delete();
                JumpToActivity(LoginActivity.class);
            }
        }, null);

    }
}
