package cn.okayj.axutil.timer;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by Jack on 16/6/17.
 */
public class Clock {
    private static final int MSG = 1;

    private long mInterval;

    private boolean running = false;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            onTick(SystemClock.elapsedRealtime());
            if(running){
                sendEmptyMessageDelayed(MSG,mInterval);
            }
        }
    };

    public Clock(long mInterval) {
        this.mInterval = mInterval;
    }

    public synchronized Clock start(){
        if(running)
            return this;

        running = true;
        onStart(SystemClock.elapsedRealtime());
        mHandler.sendEmptyMessageDelayed(MSG,mInterval);
        return this;
    }

    public synchronized void stop(){
        if(!running)
            return;

        running = false;
        mHandler.removeMessages(MSG);
        onStop(SystemClock.elapsedRealtime());
    }

    public long getCurrentTime(){
        return SystemClock.elapsedRealtime();
    }

    protected void onStart(long currentTimeMillis){

    }

    protected void onTick(long currentTimeMillis){

    }

    protected void onStop(long currentTimeMillis){

    }
}
