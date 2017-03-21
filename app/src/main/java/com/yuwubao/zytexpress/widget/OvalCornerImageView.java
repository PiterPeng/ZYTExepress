package com.yuwubao.zytexpress.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by Peng on 2016/11/25
 * e-mail: phlxplus@163.com
 * description:
 */
public class OvalCornerImageView extends android.support.v7.widget.AppCompatImageView {
    public OvalCornerImageView(Context context) {
        super(context);
        init();
    }

    public OvalCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OvalCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final RectF roundRect = new RectF();
    private float rect_adius = 66;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    private void init() {
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        //
        float density = getResources().getDisplayMetrics().density;
        rect_adius = rect_adius * density;
    }

    public void setRectAdius(float adius) {
        rect_adius = adius;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawOval(roundRect, zonePaint);

        //
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }
}
