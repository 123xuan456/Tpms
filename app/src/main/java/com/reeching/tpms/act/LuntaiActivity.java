package com.reeching.tpms.act;

import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.reeching.tpms.R;
import com.reeching.tpms.base.OtherBaseActivity;

/**
 * Created by 绍轩 on 2017/7/28.
 */

public class LuntaiActivity extends OtherBaseActivity{
    private int id=0;
    private ImageView imageView1;
    private EditText title;
    @Override
    protected void onSettingChange() {

    }

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunzik);
        this.id = getIntent().getIntExtra("id", 0);
        setview();

    }

    private void setview() {
        imageView1= (ImageView) findViewById(R.id.imageView1);
        title= (EditText) findViewById(R.id.title);
        if (id==1){
            title.setText(R.string.carlunzi1);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.left_top_tire_abnormal3x));
            return;
        }
        if (id==2){
            title.setText(R.string.carlunzi2);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.rig_top_tire_abnormal3x));
            return;
        }
        if (id==3){
            title.setText(R.string.carlunzi3);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.left_bottom_tire_abnormal3x));
            return;
        }
        if (id==4){
            title.setText(R.string.carlunzi4);
            imageView1.setImageDrawable(getResources().getDrawable(R.drawable.rig_bottom_tire_abnormal3x));
            return;
        }


    }
}
