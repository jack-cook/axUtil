package cn.okayj.axutil.timer;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Jack on 16/6/2.
 */
public abstract class ContinueCountDownTimer {
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "ContinueCountDownTimer";

    private CountDownTimer mCountDownTimer;

    private long mMillisInFuture;

    private long mCountdownInterval;

    private long mStopTimeInFuture;

    private boolean running = false;

    public ContinueCountDownTimer(long mMillisInFuture, long mCountdownInterval) {
        this.mMillisInFuture = mMillisInFuture;
        this.mCountdownInterval = mCountdownInterval;
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;//直接在此初始化!!!将延迟启动的时间也计算在倒计时内
    }

    public synchronized ContinueCountDownTimer start(){
        if(running)
            return this;

        running = true;

        if(mCountDownTimer != null){
            //重新启动
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mMillisInFuture = mStopTimeInFuture - SystemClock.elapsedRealtime();

        if(mMillisInFuture > 0) {
            onStart(mMillisInFuture);
        }

        if(DEBUG){
            Log.d(LOG_TAG,"start : mMillisInFuture: "+mMillisInFuture);
        }

        mCountDownTimer = new CountDownTimer(mMillisInFuture,mCountdownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                ContinueCountDownTimer.this.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                ContinueCountDownTimer.this.onFinish();
            }
        };

        mCountDownTimer.start();

        return this;
    }

    public synchronized void stop(){
        if(!running)
            return;

        running = false;

        if(DEBUG){
            Log.d(LOG_TAG,"stop");
        }

        mCountDownTimer.cancel();
    }

    public long getTimeLeft(){
        return mStopTimeInFuture - SystemClock.elapsedRealtime();
    }

    public abstract void onStart(long millisUntilFinished);

    public abstract void onTick(long millisUntilFinished);

    public abstract void onFinish();
}
