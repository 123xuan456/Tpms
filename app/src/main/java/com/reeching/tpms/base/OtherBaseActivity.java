package com.reeching.tpms.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.reeching.tpms.R;
import com.reeching.tpms.utils.NotificationExtend;

/**
 * Created by 齐绍轩1 on 2017/7/10.
 */

public abstract class OtherBaseActivity extends Activity
{
    private AlertDialog NoDevicedialog;
    private BluetoothAdapter adapter;
    protected NotificationExtend mNotification;
    BroadcastReceiver settingreceiver = new BroadcastReceiver()
    {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
            String str2;
            String str1 = paramAnonymousIntent.getAction();
            if (str1.equals("com.reeching.tpms.setting_change")) {
                OtherBaseActivity.this.onSettingChange();
            }
            do
            {
                do
                {
                    if (str1.equals("com.reeching.tpms.noconnection"))
                    {
                        OtherBaseActivity.this.showNoDialog();
                        return;
                    }
                }
                while (!"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(str1));
                str2 = paramAnonymousIntent.getStringExtra("reason");
            }
            while (str2 == null);
            if (str2.equals("homekey"))
            {
                OtherBaseActivity.this.mNotification = new NotificationExtend(OtherBaseActivity.this);
                OtherBaseActivity.this.mNotification.showNotification();
                OtherBaseActivity.this.moveTaskToBack(true);
                return;
            }
            str2.equals("recentapps");
        }
    };

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.reeching.tpms.setting_change");//设置改变参数时
        localIntentFilter.addAction("com.reeching.tpms.noconnection");//没有连接
        localIntentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        registerReceiver(this.settingreceiver, localIntentFilter);
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    protected void onPause()
    {
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();
        if (this.mNotification != null)
            this.mNotification.cancelNotification();
    }

    protected abstract void onSettingChange();

    protected void onStart()
    {
        super.onStart();
    }

    protected void onStop()
    {
        super.onStop();
    }

    protected void sendBroadcastNotify(String paramString)
    {
        sendBroadcast(new Intent(paramString));
    }

    protected void showNoDialog()
    {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage(R.string.dialog9);
        localBuilder.setTitle(R.string.dialog1);
        localBuilder.setPositiveButton(R.string.dialog2, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                OtherBaseActivity.this.NoDevicedialog.dismiss();
                OtherBaseActivity.this.NoDevicedialog = null;
            }
        });
        localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            public void onCancel(DialogInterface paramAnonymousDialogInterface)
            {
                OtherBaseActivity.this.NoDevicedialog.dismiss();
                OtherBaseActivity.this.NoDevicedialog = null;
            }
        });
        this.NoDevicedialog = localBuilder.create();
        this.NoDevicedialog.show();
    }

    public void viewexitClick(View paramView)
    {
        finish();
    }


}
