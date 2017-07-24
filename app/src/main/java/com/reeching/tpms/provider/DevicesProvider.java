package com.reeching.tpms.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 齐绍轩1 on 2017/7/19.
 */

public class DevicesProvider {
    public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.reeching.tpms.provider";
    public static String CONTENT_TYPE;
    public static String DEVICE_AUTHORITY = "changhe.app.ble.device.com.reeching.tpms";

    static
    {
        CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.reeching.tpms.provider";
    }

    public static void setUriString(String paramString)
    {
        DEVICE_AUTHORITY = paramString;
    }

    public static final class DevicesColumns
            implements BaseColumns
    {
        public static Uri CONTENT_URI = Uri.parse("content://" + DevicesProvider.DEVICE_AUTHORITY + "/devices");
        public static final String DEFAULT_SORT_ORDER = "lasttime desc";
        public static final String DEVICES_DISTYPE1 = "distype1";
        public static final String DEVICES_DISTYPE2 = "distype2";
        public static final String DEVICES_DISTYPE3 = "distype3";
        public static final String DEVICES_DISTYPE4 = "distype4";
        public static final String DEVICES_DL1 = "dl1";
        public static final String DEVICES_DL2 = "dl2";
        public static final String DEVICES_DL3 = "dl3";
        public static final String DEVICES_DL4 = "dl4";
        public static final String DEVICES_ID1 = "id1";
        public static final String DEVICES_ID2 = "id2";
        public static final String DEVICES_ID3 = "id3";
        public static final String DEVICES_ID4 = "id4";
        public static final String DEVICES_IMG = "img";
        public static final String DEVICES_IR1 = "ir1";
        public static final String DEVICES_IR2 = "ir2";
        public static final String DEVICES_IR3 = "ir3";
        public static final String DEVICES_IR4 = "ir4";
        public static final String DEVICES_LASTTIME = "lasttime";
        public static final String DEVICES_NAME = "name";
        public static final String DEVICES_NICK = "nick";
        public static final String DEVICES_ONLINE = "online";
        public static final String DEVICES_SYSTEMID = "mac";
        public static final String DEVICES_TW1 = "tw1";
        public static final String DEVICES_TW2 = "tw2";
        public static final String DEVICES_TW3 = "tw3";
        public static final String DEVICES_TW4 = "tw4";
        public static final String DEVICES_TY1 = "ty1";
        public static final String DEVICES_TY2 = "ty2";
        public static final String DEVICES_TY3 = "ty3";
        public static final String DEVICES_TY4 = "ty4";
        public static final String DEVICES_TYPE = "type";
        public static final String DEVICES_VERSION = "version";
        public static final String TABLE_NAME = "devices";
    }
}