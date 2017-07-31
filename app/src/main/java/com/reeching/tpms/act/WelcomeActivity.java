package com.reeching.tpms.act;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.reeching.tpms.R;

public class WelcomeActivity extends Activity implements ViewSwitcher.ViewFactory, View.OnTouchListener{
    private int currentPosition = 0;
    private float downX;
    private ImageView imageView1;
    private int[] imgIds;
    private ImageSwitcher mImageSwitcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        this.imgIds = new int[] { R.drawable.iphone5_13x, R.drawable.iphone5_23x, R.drawable.iphone5_33x, R.drawable.iphone5_43x };
        this.imageView1 = ((ImageView)findViewById(R.id.imageView1));
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor localEditor = WelcomeActivity.this.getSharedPreferences("config", 0).edit();
                localEditor.putBoolean("value", true);
                localEditor.commit();
                Intent localIntent = new Intent(WelcomeActivity.this, HomeActivity.class);
                WelcomeActivity.this.startActivity(localIntent);
                WelcomeActivity.this.finish();
            }
        });
        this.imageView1.setVisibility(View.GONE);
        this.mImageSwitcher = ((ImageSwitcher)findViewById(R.id.imageSwitcher1));
        this.mImageSwitcher.setFactory(this);
        this.mImageSwitcher.setOnTouchListener(this);
        this.mImageSwitcher.setImageResource(this.imgIds[this.currentPosition]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float lastX = event.getX();
                //抬起的时候的X坐标大于按下的时候就显示上一张图片
                if (lastX > downX) {//左划
                    if (currentPosition > 0) {
                        //设置动画
                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                        currentPosition--;
                        mImageSwitcher.setImageResource(imgIds[currentPosition % imgIds.length]);
                        imageView1.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(getApplication(), "已经是第一张", Toast.LENGTH_SHORT).show();
                        imageView1.setVisibility(View.GONE);
                    }
                }

                if (lastX < downX) {//右划
                    if (currentPosition < imgIds.length - 1) {
                        mImageSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_in));
                        mImageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_out));
                        currentPosition++;
                        mImageSwitcher.setImageResource(imgIds[currentPosition]);
                        if (currentPosition==imgIds.length-1){
                            imageView1.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Toast.makeText(getApplication(), "到了最后一张", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

        }
        return true;
    }

    @Override
    public View makeView() {
        ImageView localImageView = new ImageView(this);
        localImageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        localImageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        return localImageView;
    }
}
