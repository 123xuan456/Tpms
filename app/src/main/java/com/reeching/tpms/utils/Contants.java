package com.reeching.tpms.utils;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by 绍轩 on 2017/7/21.
 */

public class Contants {
    public static final byte BARUNIT = 0x01;
    public static final UUID BLUETOOTH_READUUID = null;
    public static final UUID BLUETOOTH_UUID = null;
    public static final UUID BLUETOOTH_WRITEUUID = null;
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static final String CONNECTSTATE = "state";
    public static final int CONNECTSTATE_CONNECTED = 0x1;
    public static final int CONNECTSTATE_CONNECTING = 0x2;
    public static final int CONNECTSTATE_DISCONNECTED = 0x4;
    public static final int CONNECTSTATE_DISCONNECTING = 0x3;
    public static final String DATAUNIT = "dataunit";
    public static final String DEVICENAME = "TPMS";
    public static final String DEVICES = "devices";
    public static String HEART_RATE_MEASUREMENT = "00001101-0000-1000-8000-00805f9b34fb";//蓝牙串口服务
    public static final String PHONENUBER = "phonenumber";
    public static final String QUERYID = "FC02D52B";
    public static final String QUERYSET = "FC02C53B";
    private static HashMap<String, String> attributes = new HashMap();
    static {
        // Sample Services.
        attributes.put("0000fff00000-1000-8000-00805f9b34fb", "心率服务");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "设备信息服务");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "厂商名称");
    }
    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }

}
