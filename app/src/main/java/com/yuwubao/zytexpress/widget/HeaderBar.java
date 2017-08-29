package com.yuwubao.zytexpress.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuwubao.zytexpress.R;


/**
 * 标题栏
 *
 * @author mhdt
 * @version 1.0
 * @created 2016-7-26 下午3:43:13
 * @update
 */
public class HeaderBar extends RelativeLayout {
    private Context context;

    private ImageView back;
    private TextView top_title;
    private TextView top_leftTitle;
    private ImageButton top_right_btn;
    private TextView rightTitle;
    private RelativeLayout rlMessage;
    private ImageView ivRedDot;


    public HeaderBar(Context context) {
        super(context);
        initView(context);
    }

    public HeaderBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        View v = LayoutInflater.from(context).inflate(R.layout.headerbar, null);
        back = (ImageView) v.findViewById(R.id.btn_top_back);
        top_title = (TextView) v.findViewById(R.id.btn_top_title);
        top_right_btn = (ImageButton) v.findViewById(R.id.img_top_right);
        rightTitle = (TextView) v.findViewById(R.id.right_title);
        top_leftTitle = (TextView) v.findViewById(R.id.tv_top_back);
        rlMessage = (RelativeLayout) v.findViewById(R.id.message);
        ivRedDot = (ImageView) v.findViewById(R.id.red_dot);

        back.setOnClickListener(onClickListener);
        top_leftTitle.setOnClickListener(onClickListener);
        top_right_btn.setOnClickListener(onClickListener);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        setBackgroundResource(R.color.white);
        this.addView(v, lp);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_top_back:
                case R.id.btn_top_back:
                    if (onLeftBtnClickListener != null) {
                        onLeftBtnClickListener.customClick(v);
                    } else {
                        ((Activity) context).finish();
                        ((Activity) context).overridePendingTransition(0, 0);
                    }
                    break;
                case R.id.img_top_right:
                    if (onRightBtnClickListener != null) {
                        onRightBtnClickListener.customClick(v);
                    }
                    break;
                case R.id.right_title:
                    if (onRightTextClickListsner != null) {
                        onRightTextClickListsner.customClick(v);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private OnCustonClickListener onLeftBtnClickListener, onRightBtnClickListener, onRightTextClickListsner;

    public interface OnCustonClickListener {
        public void customClick(View v);
    }

    public void setOnLeftBtnClickListener(OnCustonClickListener onLeftBtnClickListener) {
        this.onLeftBtnClickListener = onLeftBtnClickListener;
    }

    public void setOnRightBtnClickListener(OnCustonClickListener onRightBtnClickListener) {
        this.onRightBtnClickListener = onRightBtnClickListener;
    }

    public void setOnRightTextClickListsner(OnCustonClickListener onRightTextClickListsner) {
        this.onRightTextClickListsner = onRightTextClickListsner;
    }

    /**
     * 设置标题
     *
     * @param title 标题内容
     */
    public void setTitle(String title) {
        if (title != null) {
            top_title.setText(title);
        }
    }

    public void setRightBtnSrc(int srcId) {
        if (top_right_btn != null) {
            top_right_btn.setImageResource(srcId);
            top_right_btn.setVisibility(View.VISIBLE);
        }
    }

    public void setLeftBtnSrc(int srcId) {
        if (back != null) {
            back.setImageResource(srcId);
            back.setVisibility(View.VISIBLE);
        }
    }

    public TextView getTop_leftTitle() {
        return top_leftTitle;
    }


    /**
     * 设置是否隐藏左边按钮
     *
     * @param isHiden
     */
    public void hiddenLeft(boolean isHiden) {
        if (isHiden) {
            back.setVisibility(View.GONE);
        } else {
            back.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否隐藏左边按钮并显示左边文字
     *
     * @param isHiden
     */
    public void hiddenLeftAndShowLeftText(boolean isHiden, String leftText) {
        if (isHiden) {
            back.setVisibility(View.GONE);
            top_leftTitle.setText(leftText);
            top_leftTitle.setVisibility(VISIBLE);
        } else {
            back.setVisibility(View.VISIBLE);
            top_leftTitle.setVisibility(GONE);
        }
    }

    public void showLeft2(boolean isShow) {
        if (isShow) {
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.GONE);
        }
    }

    /**
     * 设置是否隐藏标题
     *
     * @param isHiden
     */
    public void hiddenTitle(boolean isHiden) {
        if (isHiden) {
            top_title.setVisibility(View.GONE);
        } else {
            top_title.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否隐藏右边按钮
     *
     * @param isHiden 按钮内容
     */
    public void hiddenRightBtn(boolean isHiden) {
        if (isHiden) {
            top_right_btn.setVisibility(View.GONE);
        } else {
            top_right_btn.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示并设置右侧标题
     *
     * @param rTitle
     */
    public void setAndShowRightTitle(String rTitle) {
        rightTitle.setText(rTitle);
        rightTitle.setVisibility(View.VISIBLE);
    }

    public TextView getRightTitle() {
        return rightTitle;
    }

    /**
     * 设置右侧标题字体大小
     *
     * @param rTitle 标题名称
     * @param size   字体大小
     */
    public void setRightTextSize(String rTitle, float size) {
        rightTitle.setText(rTitle);
        rightTitle.setVisibility(View.VISIBLE);
        rightTitle.setTextSize(size);
    }

    public RelativeLayout getRlMessage() {
        return rlMessage;
    }

    public void showRedDot(boolean show) {
        ivRedDot.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public ImageButton getTop_right_btn() {
        return top_right_btn;
    }
}
