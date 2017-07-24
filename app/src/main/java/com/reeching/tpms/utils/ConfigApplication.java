package com.reeching.tpms.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 齐绍轩1 on 2017/7/10.
 */

public class ConfigApplication extends Application implements Application.ActivityLifecycleCallbacks {
    public static final int ACTIVITY_ONCREATE = 1;
    public static final int ACTIVITY_ONDESTROY = 7;
    public static final int ACTIVITY_ONPAUSE = 4;
    public static final int ACTIVITY_ONRESUME = 3;
    public static final int ACTIVITY_ONSAVEINSTANCESTATE = 6;
    public static final int ACTIVITY_ONSTART = 2;
    public static final int ACTIVITY_ONSTOP = 5;
    private HashMap<String, Integer> activityHashMap;
    private ConfigParamsKeeper configkeeper;
    private String mainactivity;
    private String topactivity;

    private void setLife(Activity paramActivity, int paramInt)
    {
        this.activityHashMap.put(paramActivity.getClass().getCanonicalName(), Integer.valueOf(paramInt));
        onActivityLifeChange(this.activityHashMap);
        if (paramInt == ACTIVITY_ONRESUME)
            this.topactivity = paramActivity.getClass().getCanonicalName();
        do
        {
            do
            {
                if (paramInt != ACTIVITY_ONSTOP)
                    break;
                switch (((Integer)this.activityHashMap.get(this.topactivity)).intValue())
                {
                    case ACTIVITY_ONSTOP:
                    case ACTIVITY_ONSAVEINSTANCESTATE:
                    case ACTIVITY_ONDESTROY:
                }
            }
            while (isRunningForeground());
            onApplicationGoback();
        }
        while ((paramInt != ACTIVITY_ONDESTROY) || (isRunningForeground()));
        onApplicationExit();
    }

    protected void disenableActivityLifeCallback()
    {
        unregisterActivityLifecycleCallbacks(this);
    }

    protected void enableActivityLifeCallback(String paramString)
    {
        this.mainactivity = paramString;
        this.activityHashMap = new HashMap();
        registerActivityLifecycleCallbacks(this);
    }

    public boolean getBooleanValue(String paramString)
    {
        return this.configkeeper.getBooleanValue(paramString);
    }

    public float getFloatValue(String paramString)
    {
        return this.configkeeper.getFloatValue(paramString);
    }

    public int getIntValue(String paramString)
    {
        return this.configkeeper.getIntValue(paramString);
    }

    public long getLongValue(String paramString)
    {
        return this.configkeeper.getLongValue(paramString);
    }

    public String getPackageName(Context paramContext)
    {
        return paramContext.getPackageName();
    }

    public String getStringValue(String paramString)
    {
        return this.configkeeper.getStringValue(paramString);
    }

    public String getTopActivityName(Context paramContext)
    {
        List localList = ((ActivityManager)paramContext.getSystemService(ACTIVITY_SERVICE)).getRunningTasks(ACTIVITY_ONCREATE);
        String str = null;
        if (localList != null)
            str = ((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getClassName();
        return str;
    }

    protected boolean hasKey(String paramString)
    {
        return this.configkeeper.hasKey(paramString);
    }

    public boolean isRunningForeground()
    {
        String str1 = getPackageName(this);
        String str2 = getTopActivityName(this);
        System.out.println("packageName=" + str1 + ",topActivityClassName=" + str2);
        if ((str1 != null) && (str2 != null) && (str2.startsWith(str1)))
        {
            System.out.println("---> isRunningForeGround");
            return true;
        }
        System.out.println("---> isRunningBackGround");
        return false;
    }

    public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
    {
        Log.v("onActivityCreated", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONCREATE);
    }

    public void onActivityDestroyed(Activity paramActivity)
    {
        Log.v("onActivityDestroyed", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONDESTROY);
    }

    protected void onActivityLifeChange(HashMap<String, Integer> paramHashMap)
    {
    }

    public void onActivityPaused(Activity paramActivity)
    {
        Log.v("onActivityPaused", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONPAUSE);
    }

    public void onActivityResumed(Activity paramActivity)
    {
        Log.v("onActivityResumed", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONRESUME);
    }

    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
    {
        setLife(paramActivity, ACTIVITY_ONSAVEINSTANCESTATE);
    }

    public void onActivityStarted(Activity paramActivity)
    {
        Log.v("onActivityStarted", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONSTART);
    }

    public void onActivityStopped(Activity paramActivity)
    {
        Log.v("onActivityStopped", paramActivity.getClass().getCanonicalName());
        setLife(paramActivity, ACTIVITY_ONSTOP);
    }

    protected void onApplicationExit()
    {
    }

    protected void onApplicationGoback()
    {
    }

    public void onCreate()
    {
        super.onCreate();
        this.configkeeper = new ConfigParamsKeeper(getApplicationContext());
    }

    public void setValue(String paramString, float paramFloat)
    {
        this.configkeeper.setValue(paramString, paramFloat);
    }

    public void setValue(String paramString, int paramInt)
    {
        this.configkeeper.setValue(paramString, paramInt);
    }

    public void setValue(String paramString, long paramLong)
    {
        this.configkeeper.setValue(paramString, paramLong);
    }

    public void setValue(String paramString1, String paramString2)
    {
        this.configkeeper.setValue(paramString1, paramString2);
    }

    public void setValue(String paramString, boolean paramBoolean)
    {
        this.configkeeper.setValue(paramString, paramBoolean);
    }
}