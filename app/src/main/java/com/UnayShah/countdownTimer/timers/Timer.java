package com.UnayShah.countdownTimer.timers;

import androidx.annotation.NonNull;

import com.UnayShah.countdownTimer.common.ConstantsClass;

public class Timer {
    Integer hours;
    Integer minutes;
    Integer seconds;

    public Timer(Integer hours, Integer minutes, Integer seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public Timer() {
        this(ConstantsClass.NUMBER_PICKER_HOURS_START, ConstantsClass.NUMBER_PICKER_MINUTES_START, ConstantsClass.NUMBER_PICKER_SECONDS_START);
    }

    public Timer(String timer) {
        this(Integer.valueOf(timer.split(":")[0]), Integer.valueOf(timer.split(":")[1]), Integer.valueOf(timer.split(":")[2]));
    }

    public Timer(Long timeInMillis) {
        this((int) ((timeInMillis + ConstantsClass.ONE_SECOND_IN_MILLIS) / ConstantsClass.ONE_HOUR_IN_MILLIS), (int) ((timeInMillis + ConstantsClass.ONE_SECOND_IN_MILLIS) / ConstantsClass.ONE_MINUTE_IN_MILLIS) % ConstantsClass.MINUTES_IN_ONE_HOUR, ((int) Math.ceil(timeInMillis.doubleValue() / ConstantsClass.ONE_SECOND_IN_MILLIS)) % ConstantsClass.SECONDS_IN_ONE_MINUTE);
    }

    public Timer setTimer(Long timeInMillis) {
        hours = (int) ((timeInMillis + ConstantsClass.ONE_SECOND_IN_MILLIS) / ConstantsClass.ONE_HOUR_IN_MILLIS);
        minutes = (int) ((timeInMillis + ConstantsClass.ONE_SECOND_IN_MILLIS) / ConstantsClass.ONE_MINUTE_IN_MILLIS) % ConstantsClass.MINUTES_IN_ONE_HOUR;
        seconds = ((int) Math.ceil(timeInMillis.doubleValue() / ConstantsClass.ONE_SECOND_IN_MILLIS)) % ConstantsClass.SECONDS_IN_ONE_MINUTE;
        return this;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Long getTimeInMilliseconds() {
        return (long) (((getHours() * 3600) + (getMinutes() * 60) + getSeconds()) * 1000);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%" + 2 + "s", this.getHours()).replace(' ', '0') + ":" + String.format("%" + 2 + "s", this.getMinutes()).replace(' ', '0') + ":" + String.format("%" + 2 + "s", this.getSeconds()).replace(' ', '0');
    }
}
