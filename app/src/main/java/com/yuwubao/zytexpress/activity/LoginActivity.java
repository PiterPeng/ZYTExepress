package com.yuwubao.zytexpress.activity;

import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.yuwubao.zytexpress.AppConfig;
import com.yuwubao.zytexpress.R;
import com.yuwubao.zytexpress.bean.RequestModel;
import com.yuwubao.zytexpress.bean.User;
import com.yuwubao.zytexpress.bean.UserBack;
import com.yuwubao.zytexpress.db.dao.UserDao;
import com.yuwubao.zytexpress.helper.UIHelper;
import com.yuwubao.zytexpress.net.AppGsonCallback;
import com.yuwubao.zytexpress.net.Urls;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peng on 2017/3/7
 * e-mail: phlxplus@163.com
 * description: 登录
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.login_text)
    ImageView loginText;

    @Override
    protected int getContentResourseId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        setTop();
        User user = UserDao.getInstance().getLastUser();
        if (user != null) {
            etUsername.setText(user.getName());
        }
    }

    private void setTop() {
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
    }

    @OnClick(R.id.bt_login)
    public void onClick() {
        String name = etUsername.getText().toString().trim();
        String pwd = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            UIHelper.showMessage(c, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            UIHelper.showMessage(c, "请输入密码");
            return;
        }
        OkHttpUtils//
                .post()//
                .tag(this)//
                .url(Urls.LOGIN)//
                .addParams("name", name)//
                .addParams("pwd", pwd)//
                .build()//
                .execute(new AppGsonCallback<UserBack>(new RequestModel(c)) {
                    @Override
                    public void onResponseOK(UserBack response, int id) {
                        super.onResponseOK(response, id);
                        User user = response.getResult();
                        UserDao.getInstance().delete();
                        UserDao.getInstance().updateUser(user);
                        AppConfig.userId = String.valueOf(UserDao.getInstance().getLastUser().getId());
                        JumpToActivity(MainActivity.class);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(this);
        super.onDestroy();
    }


    @OnClick(R.id.test)
    public void onTestClick() {
        JumpToActivity(MainActivity.class);
        finish();
    }
}
