package com.reeching.tpms.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 齐绍轩1 on 2017/7/10.
 */

public class ConfigParamsKeeper {
    public static final String KEEPERNAME = "configparams";
    private Context context;

    public ConfigParamsKeeper(Context paramContext)
    {
        this.context = paramContext;
    }

    public boolean getBooleanValue(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).getBoolean(paramString, false);
    }

    public float getFloatValue(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).getFloat(paramString, 0.0F);
    }

    public int getIntValue(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).getInt(paramString, 0);
    }

    public long getLongValue(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).getLong(paramString, 0L);
    }

    public String getStringValue(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).getString(paramString, null);
    }

    public boolean hasKey(String paramString)
    {
        return this.context.getSharedPreferences("configparams", 0).contains(paramString);
    }

    public void setValue(String paramString, float paramFloat)
    {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("configparams", 0).edit();
        localEditor.putFloat(paramString, paramFloat);
        localEditor.commit();
    }

    public void setValue(String paramString, int paramInt)
    {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("configparams", 0).edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public void setValue(String paramString, long paramLong)
    {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("configparams", 0).edit();
        localEditor.putLong(paramString, paramLong);
        localEditor.commit();
    }

    public void setValue(String paramString1, String paramString2)
    {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("configparams", 0).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public void setValue(String paramString, boolean paramBoolean)
    {
        SharedPreferences.Editor localEditor = this.context.getSharedPreferences("configparams", 0).edit();
        localEditor.putBoolean(paramString, paramBoolean);
        localEditor.commit();
    }
}
