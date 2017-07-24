package com.reeching.tpms.act;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;

import com.reeching.tpms.R;
import com.reeching.tpms.base.OtherBaseActivity;
import com.reeching.tpms.service.BluetoothMultiService;

public class HomeActivity extends OtherBaseActivity {


    private ServiceConnection HomeConnection = new ServiceConnection()
    {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
        {
            HomeActivity.this.homeBinder = ((BluetoothMultiService.HomeBinder)paramAnonymousIBinder).getService();
            HomeActivity.this.homeBinder.connectDevices(false);
        }

        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
        {
            HomeActivity.this.homeBinder = null;
        }
    };
    private BluetoothMultiService homeBinder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bindService(new Intent(this, BluetoothMultiService.class), this.HomeConnection, Context.BIND_AUTO_CREATE);

    }
    public void tvClick(View view){
        Intent i=new Intent(this,SettingActivity.class);
        startActivity(i);
    }
    @Override
    protected void onSettingChange() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.homeBinder != null)
            this.homeBinder.connectDevices(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.homeBinder != null)
        {
            this.homeBinder.stopFindDevices();
            unbindService(this.HomeConnection);
        }
    }
}
