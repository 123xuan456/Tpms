package com.reeching.tpms.message;

import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 绍轩 on 2017/7/21.
 */

public class CacheMessageManager {
    public static final int SENDMESSAGE = 101;
    public static final int WAITTIME = 90;
    private ExecutorService executor = Executors.newScheduledThreadPool(1);
    Handler handeHandler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            if (paramAnonymousMessage.what == 101)
            {
                CacheMessageManager.this.lasttime = System.currentTimeMillis();
                CacheMessageManager.this.listener.NextMessage(paramAnonymousMessage.obj);
            }
        }
    };
    private long lasttime = System.currentTimeMillis();
    private CacheMessageListener listener;
    private ConcurrentLinkedQueue<Object> msgQueue = new ConcurrentLinkedQueue();
    private boolean msgService = false;

    public CacheMessageManager(CacheMessageListener paramCacheMessageListener)
    {
        this.listener = paramCacheMessageListener;
    }

    private void sendWaitingMessage(boolean paramBoolean)
    {
        if (this.msgService);
        while (this.executor.isTerminated())
            return;
        this.executor.execute(new MessageRunnable(this.handeHandler, paramBoolean));
    }

    public void readyMessage(Object paramObject)
    {
        this.msgQueue.add(paramObject);
        if (this.msgService)
            return;
        sendWaitingMessage(false);
    }

    public void removeAllMessage()
    {
        this.msgQueue.clear();
    }

    public static abstract interface CacheMessageListener
    {
        public abstract void NextMessage(Object paramObject);
    }

    class MessageRunnable
            implements Runnable
    {
        private Handler handler;
        private boolean wait;

        public MessageRunnable(Handler paramBoolean, boolean bool)
        {
            this.handler = paramBoolean;
            this.wait = bool;
        }

        public void run()
        {
            CacheMessageManager.this.msgService = true;
            while (true)
            {
                if (CacheMessageManager.this.msgQueue.peek() == null)
                {
                    CacheMessageManager.this.msgService = false;
                    return;
                }
                Object localObject = CacheMessageManager.this.msgQueue.poll();
                Message localMessage = this.handler.obtainMessage();
                localMessage.what = 101;
                localMessage.obj = localObject;
                this.handler.sendMessage(localMessage);
                try
                {
                    Thread.sleep(90L);
                }
                catch (InterruptedException localInterruptedException)
                {
                    localInterruptedException.printStackTrace();
                }
            }
        }
    }
}
