package com.UnayShah.countdownTimer.countdowntimer;

import com.UnayShah.countdownTimer.TimerActivity;

public class CountdownTimerFactory {

    public static CountdownTimer getInstance(TimerActivity timerActivity) {
        return new CountdownTimer(timerActivity);
    }
}
