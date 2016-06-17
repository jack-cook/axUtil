package cn.okayj.axutil.timer;

/**
 * Created by Jack on 16/6/17.
 */
public interface ClockListener {
    public void onStart(long currentTimeMillis);
    public void onTick(long currentTimeMillis);
    public void onStop(long currentTimeMillis);
}
