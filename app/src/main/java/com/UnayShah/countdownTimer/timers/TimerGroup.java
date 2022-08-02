package com.UnayShah.countdownTimer.timers;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TimerGroup {
    private String name;
    private TimerGroupType timerGroupType;
    private Timer timer;
    private List<TimerGroup> listTimerGroup;
    private Boolean looped;
    private Integer internalUsageCount;
    private Integer reps;

    public TimerGroup() {
        setName("");
        reps = 1;
        timer = new Timer();
        looped = false;
        internalUsageCount = 0;
        listTimerGroup = new ArrayList<>();
    }

    public TimerGroup(Timer timer) {
        this();
        this.timer = timer;
        timerGroupType = TimerGroupType.TIMER;
    }

    public TimerGroup(String text, TimerGroupType timerGroupType) {
        this();
        this.timerGroupType = timerGroupType;
        if (timerGroupType == TimerGroupType.TIMER && text.matches("[0-9][0-9]:[0-5][0-9]:[0-5][0-9]")) {
            this.timer = new Timer(text);
        } else {
            this.name = text;
        }
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public void incrementReps() {
        setReps(getReps() + 1);
    }

    public void decrementReps() {
        setReps(Math.max(getReps() - 1, 1));
    }

    public Integer getInternalUsageCount() {
        return internalUsageCount;
    }

    public void incrementInternalUsageCount() {
        this.internalUsageCount++;
    }

    public void decrementInternalUsageCount() {
        this.internalUsageCount--;
    }

    public Boolean getLooped() {
        return looped;
    }

    public void setLooped(Boolean looped) {
        this.looped = looped;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TimerGroupType getTimerGroupType() {
        return timerGroupType;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public void setTimer(String timerString) {
        this.timer = new Timer(timerString);
    }

    public Queue<Timer> getTimersQueue() {
        return getTimersQueue(this);
    }

    public Queue<Timer> getTimersQueue(TimerGroup timerGroup) {
        Queue<Timer> queueTimer = new LinkedList<>();
        if (timerGroup.getListTimerGroup().size() == 0) return queueTimer;
        for (TimerGroup tg : timerGroup.getListTimerGroup()) {
            if (tg.getTimerGroupType().equals(TimerGroupType.TIMER))
                queueTimer.add(tg.getTimer());
            else {
                if (!tg.getName().equals(this.getName()))
                    queueTimer.addAll(getTimersQueue(tg));
            }
        }
        return queueTimer;
    }

    public Long totalTimerGroupDuration() {
        Long time = 0L;
        for (Timer timer : getTimersQueue()) {
            time += timer.getTimeInMilliseconds();
        }
        return time;
    }

    public Timer setTimer(Long timeInMillis) {
        return timer.setTimer(timeInMillis);
    }

    public List<TimerGroup> getListTimerGroup() {
        return listTimerGroup;
    }

    public void setListTimerGroup(List<TimerGroup> listTimerGroup) {
        this.listTimerGroup = new ArrayList<>(listTimerGroup);
    }

    public Long getTimeInMilliseconds() {
        switch (timerGroupType) {
            case TIMER:
                return timer.getTimeInMilliseconds();
            case TIMER_GROUP:
                Long time = 0L;
                for (TimerGroup timerGroup : listTimerGroup) {
                    time += timerGroup.getTimeInMilliseconds();
                }
                return time;
            default:
                return 0L;
        }
    }

    @NonNull
    @Override
    public String toString() {
        if (this.getTimerGroupType().equals(TimerGroupType.TIMER)) return timer.toString();
        else return name;
    }
}