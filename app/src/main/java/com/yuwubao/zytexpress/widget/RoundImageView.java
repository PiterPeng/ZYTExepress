package com.yuwubao.zytexpress.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.yuwubao.zytexpress.R;


/**
 * 圆角矩形
 *
 * @author mhdt
 * @version 1.0
 * @created 2017/3/4
 */
public class RoundImageView extends android.support.v7.widget.AppCompatImageView {
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFAULT = 5;
    /**
     * 圆角的大小
     */
    private int mBorderRadius;

    public RoundImageView(Context context) {
        super(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);

        mBorderRadius = a.getDimensionPixelSize(
                R.styleable.RoundImageView_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                BODER_RADIUS_DEFAULT, getResources()
                                        .getDisplayMetrics()));// 默认为10dp
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        clipPath.addRoundRect(new RectF(0, 0, w, h), 10.0f, 10.0f, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
