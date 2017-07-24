package com.reeching.tpms.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.reeching.tpms.R;
import com.reeching.tpms.message.CacheMessageManager;
import com.reeching.tpms.utils.BleDevice;
import com.reeching.tpms.utils.BluetoothDeviceVo;
import com.reeching.tpms.utils.DataUnit;

/**
 * Created by 绍轩 on 2017/7/21.
 */

public class BluetoothMultiService extends Service implements CacheMessageManager.CacheMessageListener{


    private static BluetoothMultiService instance;
    private CacheMessageManager manager;
    private BluetoothAdapter adapter;
    private BluetoothGatt mBluetoothGatt;
    private Toast toast;
    private boolean mScanning=false;
    private android.bluetooth.BluetoothGattCallback mGattCallback;
    private IBinder mBinder=new HomeBinder();
    private BluetoothAdapter.LeScanCallback mLeScanCallback=new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {

        }
        public void onCharacteristicChanged(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic) {
            byte[] arrayOfByte = paramAnonymousBluetoothGattCharacteristic.getValue();
            if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
                BluetoothMultiService.this.handler.obtainMessage(103, new DataUnit(paramAnonymousBluetoothGatt.getDevice().getAddress(), arrayOfByte)).sendToTarget();
                return;
            }
        }
    };

    Handler handler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            switch (paramAnonymousMessage.what)
            {
                case 0:
                case 104:
                default:
                    return;
                case 101:
                    BluetoothMultiService.this.stopFindDevices();
                    return;
                case 102:
                    String str2 = (String)paramAnonymousMessage.obj;
                    BluetoothMultiService.this.connectDevices(new BluetoothDeviceVo(null, str2), false);
                    return;
                case 103:
                    DataUnit localDataUnit = (DataUnit)paramAnonymousMessage.obj;
                  //  BluetoothMultiService.this.receiveData(localDataUnit);
                    return;
                case 106:
                    BluetoothDeviceVo localBluetoothDeviceVo = (BluetoothDeviceVo)paramAnonymousMessage.obj;
                  //  BluetoothMultiService.this.onConnectDevice(localBluetoothDeviceVo);
                    return;
                case 105:
                    String str1 = (String)paramAnonymousMessage.obj;
                   // BluetoothMultiService.this.onMessage(str1);
                    return;
                case 107:
            }
           // BluetoothMultiService.this.DelayMessage(paramAnonymousMessage.arg1);
        }
    };


    public static BluetoothMultiService getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        this.manager = new CacheMessageManager(this);
        this.adapter = BluetoothAdapter.getDefaultAdapter();
        //this.ioHandler = new IOHandler(this);
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.reeching.tpms.actionconnect");
        localIntentFilter.addAction("com.reeching.tpms.actiondisconnect");
        localIntentFilter.addAction("com.reeching.tpms.actionwrite");
        localIntentFilter.addAction("com.reeching.tpms.actionstopsearch");
        localIntentFilter.addAction("com.reeching.tpms.actionstartsearch");
        localIntentFilter.addAction("com.reeching.tpms.sendmessage");
        localIntentFilter.addAction("android.bluetooth.adapter.action.SCAN_MODE_CHANGED");
        localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_STARTED");
        registerReceiver(this.receiver, localIntentFilter);
    }
    BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str=intent.getAction();
            if (str.equals("com.reeching.tpms.sendmessage"))
            {
                byte[] arrayOfByte = intent.getByteArrayExtra("data");
                BluetoothMultiService.this.writeBytes(arrayOfByte);
            }
            do
            {
                if (str.equals("com.reeching.tpms.actionconnect"))
                {
                    BleDevice localBleDevice2 = (BleDevice)intent.getSerializableExtra("device");
                    if (localBleDevice2 == null)
                    {
                        BluetoothMultiService.this.connectDevices(false);
                        return;
                    }
                    //连接设备操作
                    BluetoothMultiService.this.connectDevices(new BluetoothDeviceVo(localBleDevice2), false);
                    return;
                }
                if (str.equals("com.reeching.tpms.actiondisconnect"))
                {
                    BleDevice localBleDevice1 = (BleDevice)intent.getSerializableExtra("device");
                //    BluetoothMultiService.this.disconnectDevices(new BluetoothDeviceVo(localBleDevice1));
                    return;
                }
                if (str.equals("com.reeching.tpms.actionstopsearch"))
                {
                    BluetoothMultiService.this.stopFindDevices();
                    return;
                }
            }
            while (!str.equals("com.reeching.tpms.actionstartsearch"));
            BluetoothMultiService.this.findDevices(true);
        }
    };

    public void connectDevices(boolean b) {
//        Cursor localCursor = getContentResolver().query(DevicesProvider.DevicesColumns.CONTENT_URI, null, null, null, null);
//        if (localCursor.moveToNext())
//        {
//            BleDevice localBleDevice = new BleDevice();
//            localBleDevice.setName(localCursor.getString(localCursor.getColumnIndex("name")));
//            localBleDevice.setMac(localCursor.getString(localCursor.getColumnIndex("mac")));
//            localBleDevice.setUuid(localCursor.getString(localCursor.getColumnIndex("mac")));
//            localBleDevice.setConnect(true);
//            connectDevices(new BluetoothDeviceVo(localBleDevice), b);
//        }
//        while (true)
//        {
//            localCursor.close();
//            findDevices(true);
//            return;
//        }
    }

    private void findDevices(boolean b) {
        if (mScanning)
            return;
        mScanning = true;
        if (b)
        {
            this.adapter.startLeScan(this.mLeScanCallback);
            return;
        }
        this.handler.postDelayed(new Runnable()
                                 {
                                     public void run()
                                     {
                                         BluetoothMultiService.this.stopFindDevices();
                                     }
                                 }
                , 10000L);
        this.adapter.startLeScan(this.mLeScanCallback);
    }

    public void stopFindDevices() {
        this.adapter.stopLeScan(this.mLeScanCallback);
        mScanning = false;
    }

    public void connectDevices(BluetoothDeviceVo paramBluetoothDeviceVo, boolean paramBoolean)
    {
        BluetoothDevice localBluetoothDevice = this.adapter.getRemoteDevice(paramBluetoothDeviceVo.getAddress());
        if (this.mBluetoothGatt != null)
        {
            this.mBluetoothGatt.connect();
            this.mBluetoothGatt.disconnect();
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
        do
            this.mBluetoothGatt = localBluetoothDevice.connectGatt(this, false, this.mGattCallback);
        while (!paramBluetoothDeviceVo.getAddress().equals(this.mBluetoothGatt.getDevice().getAddress()));
        while (paramBoolean);
        onToast(R.string.dialog7);
    }

    public void onToast(int paramInt)
    {
        onToast(getResources().getString(paramInt));
    }

    public void onToast(String paramString)
    {
        if (this.toast != null)
        {
            this.toast.cancel();
            this.toast = null;
        }
        this.toast = Toast.makeText(this, paramString, Toast.LENGTH_SHORT);
        this.toast.setGravity(17, 0, 0);
        this.toast.show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override
    public void NextMessage(Object paramObject) {

    }
    public void writeBytes(byte[] paramArrayOfByte)
    {
        this.manager.readyMessage(paramArrayOfByte);
    }
    public class HomeBinder extends Binder
    {
        public HomeBinder()
        {
        }

        public BluetoothMultiService getService()
        {
            return BluetoothMultiService.this;
        }
    }
}
