package com.reeching.tpms.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.reeching.tpms.R;

/**
 * Created by 绍轩 on 2017/7/31.
 */

public class SeekView extends View {
    private int height1 = 0;
    private Bitmap imagesrc;
    private Context mContext;
    private Paint paint;
    private boolean postInvalidate = false;
    private int progress = 50;
    private Bitmap progressButton;
    private Bitmap progressImg;
    private String text;
    private int textColor = Color.WHITE;
    private float textSize = 25.0F;
    private float textWidth = 0.0F;
    private int width1 = 0;

    public SeekView(Context context) {
        super(context);
        this.mContext=context;
        initRect();
    }


    public SeekView(Context context,AttributeSet attrs) {
        super(context, attrs);
        initRect();
    }

    private void initRect() {
        this.paint = new Paint();
        this.paint.setColor(this.textColor);
        this.paint.setTextSize(15);
        this.progressImg = BitmapFactory.decodeResource(getResources(), R.drawable.pointer_abnormal3x);
        this.progressButton = BitmapFactory.decodeResource(getResources(), R.drawable.pointer_normal3x);
        this.imagesrc = BitmapFactory.decodeResource(getResources(), R.drawable.line_3x_num);
        int width = imagesrc.getWidth();
        int height = imagesrc.getHeight();
        int newWidth = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int newHeight = height;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        imagesrc = Bitmap.createBitmap(imagesrc, 0, 0, width,
                height, matrix, true);
        String str = getIntString(this.progress);
        this.textWidth = this.paint.measureText(str);
        this.height1 = this.progressImg.getHeight();
        this.width1 = this.progressImg.getWidth();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap localBitmap = getDrawingCache();
        if (localBitmap != null)
            height1=localBitmap.getHeight();
        if (!this.postInvalidate)
            this.text = getIntString(this.progress);
        int i = this.progress;
        float f = this.paint.measureText(this.text);
        canvas.drawText(this.text, (float)(1.0D * i / 100.0D * getWidth() - 1.0D * f / 2.0D), f / 2.0F, this.paint);
        canvas.drawBitmap(this.progressImg, (float)(1.0D * i / 100.0D * getWidth() - 1.0D * this.width1 / 2.0D), (float)(0.6D * f), null);
        canvas.drawBitmap(this.imagesrc, 0.0F, (float)(0.6D * f) + this.progressImg.getHeight(), null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,(int)(this.imagesrc.getHeight() + this.height1 + this.textWidth));
    }

    private String getIntString(int paramInt)
    {
        if (isInEditMode())
            return "Normal";
        if (paramInt < 40)
            return getContext().getResources().getString(R.string.taiyaexceptioin);
        if (paramInt > 72)
            return getContext().getResources().getString(R.string.taiyaexceptioin);
        return getContext().getResources().getString(R.string.taiyanormal);
    }


}
