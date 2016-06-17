package cn.okayj.axutil.timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 16/6/17.
 */
public class AutoTimeNotifier {
    private long mInterval;

    private ArrayList<ClockListener> mListeners = new ArrayList<>();

    private ArrayList<ClockListener> mSecondaryListeners = new ArrayList<>();

    private Clock mClock;

    private boolean mTurnedOn = false;

    public AutoTimeNotifier(long mInterval) {
        this.mInterval = mInterval;
        mClock = new Clock(mInterval){
            @Override
            protected void onStart(long currentTimeMillis) {
                List<ClockListener> listeners = (List<ClockListener>) mListeners.clone();
                List<ClockListener> secondaryListeners = (List<ClockListener>) mSecondaryListeners.clone();
                for (ClockListener listener : listeners){
                    listener.onStart(currentTimeMillis);
                }
                for (ClockListener listener : secondaryListeners){
                    listener.onStart(currentTimeMillis);
                }
            }

            @Override
            protected void onTick(long currentTimeMillis) {
                List<ClockListener> listeners = (List<ClockListener>) mListeners.clone();
                List<ClockListener> secondaryListeners = (List<ClockListener>) mSecondaryListeners.clone();
                for (ClockListener listener : listeners){
                    listener.onTick(currentTimeMillis);
                }
                for (ClockListener listener : secondaryListeners){
                    listener.onTick(currentTimeMillis);
                }
            }

            @Override
            protected void onStop(long currentTimeMillis) {
                List<ClockListener> listeners = (List<ClockListener>) mListeners.clone();
                List<ClockListener> secondaryListeners = (List<ClockListener>) mSecondaryListeners.clone();
                for (ClockListener listener : listeners){
                    listener.onStop(currentTimeMillis);
                }
                for (ClockListener listener : secondaryListeners){
                    listener.onStop(currentTimeMillis);
                }
            }
        };
    }

    public AutoTimeNotifier turnOn(){
        if(mTurnedOn)
            return this;

        mTurnedOn = true;
        if(mListeners.size() > 0){
            mClock.start();
        }

        return this;
    }

    public void turnOff(){
        if(!mTurnedOn)
            return;

        mTurnedOn = false;
        mClock.stop();
    }

    public final void autoStart(){
        if(mTurnedOn && mListeners.size() > 0){
            mClock.start();
        }
    }

    public final void autoStop(){
        if(mTurnedOn && mListeners.size() == 0){
            mClock.stop();
        }
    }

    public long getCurrentTime(){
        return mClock.getCurrentTime();
    }

    public void addListener(ClockListener l){
        if(!mListeners.contains(l)){
            mListeners.add(l);
            autoStart();
        }
    }

    public void removeListener(ClockListener l){
        if(mListeners.remove(l))
            autoStop();
    }

    public void addSecondaryListener(ClockListener l){
        if(!mSecondaryListeners.contains(l)){
            mSecondaryListeners.add(l);
        }
    }

    public void removeSecondaryListener(ClockListener l){
        mSecondaryListeners.remove(l);
    }

}
