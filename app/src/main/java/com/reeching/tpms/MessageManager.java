package com.reeching.tpms;

import android.content.Context;
import android.content.Intent;

import com.reeching.tpms.act.DeviceListActivity;
import com.reeching.tpms.utils.BleDevice;

/**
 * Created by 绍轩 on 2017/7/20.
 */

public class MessageManager {
    public static final String LOGIN = "com.reeching.tpms.login";
    public static final String SENDDATA = "com.reeching.tpms.sendmessage";
    private static MessageManager manager;
    private static Context context;

    public static final MessageManager getIntance()
    {
        if (manager == null)
            manager = new MessageManager();
        return manager;
    }
    public void Login()
    {
        if (this.context == null)
            return;
        Intent localIntent = new Intent(LOGIN);
        this.context.sendBroadcast(localIntent);
    }

    public void connectDevice(BleDevice paramBleDevice)
    {
        if (this.context == null)
            return;
        //发送连接设备广播
        Intent localIntent = new Intent("com.reeching.tpms.actionconnect");
        localIntent.putExtra("device", paramBleDevice);
        this.context.sendBroadcast(localIntent);
    }

    public void disconnectDevice(BleDevice paramBleDevice)
    {
        if (this.context == null)
            return;
        //发送断开连接设备广播
        Intent localIntent = new Intent("com.reeching.tpms.actiondisconnect");
        localIntent.putExtra("device", paramBleDevice);
        this.context.sendBroadcast(localIntent);
    }

    public void initContext(Context paramContext)
    {
        this.context = paramContext;
    }

    public void writeMessage(byte[] paramArrayOfByte)
    {
        if (this.context == null)
            return;
        Intent localIntent = new Intent("com.reeching.tpms.sendmessage");
        localIntent.putExtra("data", paramArrayOfByte);
        this.context.sendBroadcast(localIntent);
    }

    public static MessageManager getIntance(DeviceListActivity deviceListActivity) {
        if (manager == null) {
            manager = new MessageManager();
        }
        context=deviceListActivity;
        return manager;
    }
}