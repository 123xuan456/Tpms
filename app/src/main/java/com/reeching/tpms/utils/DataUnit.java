package com.reeching.tpms.utils;

import android.bluetooth.BluetoothDevice;

import java.io.Serializable;

/**
 * Created by 绍轩 on 2017/7/21.
 */

public class DataUnit implements Serializable
{
    private static final long serialVersionUID = 1L;
    private byte[] data;
    private String mac;
    private String name;

    public DataUnit()
    {
    }

    public DataUnit(BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte)
    {
        this.name = paramBluetoothDevice.getName();
        this.mac = paramBluetoothDevice.getAddress();
        this.data = paramArrayOfByte;
    }

    public DataUnit(String paramString, byte[] paramArrayOfByte)
    {
        this.mac = paramString;
        this.data = paramArrayOfByte;
    }

    public byte[] getData()
    {
        return this.data;
    }

    public String getMac()
    {
        return this.mac;
    }

    public String getName()
    {
        return this.name;
    }

    public void setData(byte[] paramArrayOfByte)
    {
        this.data = paramArrayOfByte;
    }

    public void setMac(String paramString)
    {
        this.mac = paramString;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }
}