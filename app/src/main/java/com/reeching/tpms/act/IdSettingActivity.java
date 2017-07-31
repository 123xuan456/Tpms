package com.reeching.tpms.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.reeching.tpms.R;
import com.reeching.tpms.base.OtherBaseActivity;

/**
 * Created by 绍轩 on 2017/7/28.
 * id设置页面
 */

public class IdSettingActivity extends OtherBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_idset);
    }

    @Override
    protected void onSettingChange() {

    }

}
