package com.reeching.tpms.utils;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * Created by 齐绍轩1 on 2017/7/18.
 */

public class BleDevice implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean connect;
    private String mac;
    private String name;
    private String uuid;

    public BleDevice(){
    }

    public BleDevice(String name, String address) {
        this.name=name;
        this.mac=address;
    }

    public BleDevice(BluetoothDevice paramBluetoothDevice)
    {
        this.mac = paramBluetoothDevice.getAddress();
        this.name = paramBluetoothDevice.getName();
    }

    public BleDevice(BluetoothDeviceVo paramBluetoothDeviceVo)
    {
        this.mac = paramBluetoothDeviceVo.getAddress();
        this.name = paramBluetoothDeviceVo.getName();
    }

    public String getMac()
    {
        return this.mac;
    }

    public String getName()
    {
        return this.name;
    }

    public String getUuid()
    {
        return this.uuid;
    }

    public boolean isConnect()
    {
        return this.connect;
    }

    public void setConnect(boolean paramBoolean)
    {
        this.connect = paramBoolean;
    }

    public void setMac(String paramString)
    {
        this.mac = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setUuid(String paramString)
    {
        this.uuid = paramString;
    }

    public void setDetail(String name, String address) {
        this.name=name;
        this.mac=address;

    }
}
