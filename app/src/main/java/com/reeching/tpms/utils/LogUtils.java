package com.reeching.tpms.utils;

import android.util.Log;


public class LogUtils {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private LogUtils(){
        /* Protect from instantiations */
    }
   // 在发布release版本，这个bool值自动变为false
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append("="+log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    //（红色）输出错误信息
    public static void e(String message){
        if (!isDebuggable())
            return;
        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    //（绿色） 输出提示信息
    public static void i(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(className,createLog(message));
    }
    //（蓝色） 输出Debug信息
    public static void d(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }
    //（黑色） 任何消息都会输出，一般用这个进行程序运行状态的
    public static void v(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }
    //（黄色） 输出警告信息
    public static void w(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

    public static void wtf(String message){
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}
