package com.reeching.tpms.utils;

import com.reeching.tpms.MessageManager;
import com.reeching.tpms.act.HomeActivity;

/**
 * Created by 绍轩 on 2017/7/21.
 */

public class ExampleApplication extends ConfigApplication{

    private static ExampleApplication instance;

    public static ExampleApplication getInstance()
    {
        return instance;
    }

    public void onCreate()
    {
        super.onCreate();
        instance = this;
        if (!hasKey("INITVALUE"))
        {
            setValue("highpa", 4);
            setValue("lowpa", 3);
            setValue("hightw", 20);
            setValue("irpowervoice", true);
            setValue("irpowerring", true);
            setValue("INITVALUE", "init");
            setValue("LANGUAGE", 3);
        }
        MessageManager.getIntance().initContext(this);
        enableActivityLifeCallback(HomeActivity.class.getCanonicalName());
    }

    public void writeMessage(byte[] paramArrayOfByte)
    {
        MessageManager.getIntance().writeMessage(paramArrayOfByte);
    }
}
