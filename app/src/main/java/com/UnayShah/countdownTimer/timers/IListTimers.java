package com.UnayShah.countdownTimer.timers;

import java.util.List;

public interface IListTimers {

    List<Timer> getListTimers();

    void setListTimers(List<Timer> IListTimers);

    void add(Timer timer);

    void remove(int index);

    void remove(Timer timer);

    void set(Integer position, Timer timer);

    void swap(IListTimers listTimers, Integer fromPosition, Integer toPosition);

    Integer size();
}
