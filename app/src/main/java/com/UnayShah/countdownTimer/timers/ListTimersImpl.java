package com.UnayShah.countdownTimer.timers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTimersImpl implements IListTimers {
    List<Timer> listTimers;

    public ListTimersImpl() {
        this(new ArrayList<>());
    }

    public ListTimersImpl(List<Timer> listTimers) {
        this.listTimers = listTimers;
    }

    @Override
    public List<Timer> getListTimers() {
        return listTimers;
    }

    @Override
    public void setListTimers(List<Timer> listTimers) {
        this.listTimers = listTimers;
    }

    @Override
    public void add(Timer timer) {
        listTimers.add(timer);
    }

    @Override
    public void remove(int index) {
        listTimers.remove(index).toString();
    }

    @Override
    public void remove(Timer timer) {
        listTimers.remove(timer);
    }

    @Override
    public void set(Integer position, Timer timer) {
        listTimers.set(position, timer);
    }

    @Override
    public void swap(IListTimers listTimers, Integer fromPosition, Integer toPosition) {
        Collections.swap(listTimers.getListTimers(), fromPosition, toPosition);
    }

    @Override
    public Integer size() {
        return listTimers.size();
    }
}
