package com.reeching.tpms.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 齐绍轩1 on 2017/7/19.
 */

public class DatabaseDevicesConnectHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "devices.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseDevicesConnectHelper(Context paramContext)
    {
        super(paramContext, "devices.db", null, 1);
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
        paramSQLiteDatabase.execSQL("CREATE TABLE devices (_id INTEGER PRIMARY KEY,name TEXT,nick TEXT,mac TEXT,version TEXT,img BLOB,online INTEGER,type INTEGER,id1 TEXT,ir1 INTEGER,ty1 INTEGER,tw1 INTEGER,dl1 INTEGER,distype1 INTEGER,id2 TEXT,ir2 INTEGER,ty2 INTEGER,tw2 INTEGER,dl2 INTEGER,distype2 INTEGER,id3 TEXT,ir3 INTEGER,ty3 INTEGER,tw3 INTEGER,dl3 INTEGER,distype3 INTEGER,id4 TEXT,ir4 INTEGER,ty4 INTEGER,tw4 INTEGER,dl4 INTEGER,distype4 INTEGER,lasttime TEXT);");
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS devices");
        onCreate(paramSQLiteDatabase);
    }
}