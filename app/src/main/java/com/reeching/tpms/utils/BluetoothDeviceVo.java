package com.reeching.tpms.utils;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * Created by 齐绍轩1 on 2017/7/18.
 */

public class BluetoothDeviceVo implements Serializable{
    private static final long serialVersionUID = 1L;
    private String address;
    private String name;

    public BluetoothDeviceVo()
    {
    }

    public BluetoothDeviceVo(BluetoothDevice paramBluetoothDevice)
    {
        this.name = paramBluetoothDevice.getName();
        this.address = paramBluetoothDevice.getAddress();
    }

    public BluetoothDeviceVo(BleDevice paramBleDevice)
    {
        this.name = paramBleDevice.getName();
        this.address = paramBleDevice.getMac();
    }

    public BluetoothDeviceVo(String paramString1, String paramString2)
    {
        this.name = paramString1;
        this.address = paramString2;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getName()
    {
        return this.name;
    }

    public void setAddress(String paramString)
    {
        this.address = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }
}