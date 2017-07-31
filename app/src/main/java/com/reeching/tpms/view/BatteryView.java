package com.reeching.tpms.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 绍轩 on 2017/7/28.
 */

public class BatteryView extends ImageView{
    private Drawable compass;
    private Context context;
    private int defaultcolor = Color.GREEN;
    private int mDirection;
    Paint paint;
    RectF rect = new RectF();
    public BatteryView(Context context) {
        super(context);
        this.context = context;
        init();
    }
    public BatteryView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        this.context = paramContext;
        init();
    }

    public BatteryView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
        this.context = paramContext;
        init();
    }

    private void init() {
        this.mDirection = 100;
        this.compass = null;
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setColor(this.defaultcolor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (this.compass == null)
        {
            this.compass = getDrawable();
            this.compass.setBounds(0, 0, getWidth(), getHeight());
        }
        canvas.save();
        this.compass.draw(canvas);
        canvas.restore();
        canvas.save();
        this.rect.set((int)(getWidth() / 15.0D), (int)(getWidth() / 10.0D), (int)(getWidth() / 15.0D + 5.0D * (getWidth() / 15.0D) * this.mDirection / 100.0D), (int)(getHeight() - getWidth() / 10.0D));
        canvas.drawRoundRect(this.rect, 5.0F, 5.0F, this.paint);
        canvas.restore();
    }

    public void updateDirection(int paramInt)
    {
        this.mDirection = paramInt;
        invalidate();
    }
}
