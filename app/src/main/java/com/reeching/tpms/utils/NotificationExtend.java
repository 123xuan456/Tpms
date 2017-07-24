package com.reeching.tpms.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.reeching.tpms.R;

/**
 * Created by 齐绍轩1 on 2017/7/10.
 */

public class NotificationExtend {
    private Activity context;

    public NotificationExtend(Activity paramActivity)
    {
        this.context = paramActivity;
    }

    public void cancelNotification()
    {
        ((NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(0);
    }

    public void showNotification()
    {
        NotificationManager localNotificationManager = (NotificationManager)this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);//新建Notification.Builder对象
        Intent localIntent = new Intent(this.context, this.context.getClass());
        localIntent.setAction("android.intent.action.MAIN");
        localIntent.addCategory("android.intent.category.LAUNCHER");
        PendingIntent localPendingIntent = PendingIntent.getActivity(this.context, 0, localIntent,PendingIntent.FLAG_CANCEL_CURRENT );
        builder.setContentText("TPMS");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(localPendingIntent);//执行intent
        Notification notification = builder.getNotification();//将builder对象转换为普通的notification
        notification.flags |= Notification.FLAG_ONGOING_EVENT;  // 将此通知放到通知栏的"Ongoing"即"正在运行"组中
        notification.flags |= Notification.FLAG_AUTO_CANCEL;//点击通知后通知消失
        localNotificationManager.notify(1,notification);
    }



}
