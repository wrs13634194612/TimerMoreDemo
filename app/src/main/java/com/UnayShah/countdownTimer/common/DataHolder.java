package com.UnayShah.countdownTimer.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.timers.TimerGroup;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DataHolder {
    private static final DataHolder dataHolder = new DataHolder();
    private final Stack<String> stackNavigation;
    private final Map<String, Integer> mapTimerGroups = new HashMap<>();
    private List<TimerGroup> listTimerGroup;
    private List<TimerGroup> allTimerGroups;
    private Boolean disableButtonClick = false;
    private String themeMode;
    private Boolean showTutorial = true;
    private String ringtone;

    public DataHolder() {
        this.listTimerGroup = new ArrayList<>();
        this.stackNavigation = new Stack<>();
        this.disableButtonClick = false;
        this.themeMode = ConstantsClass.LIGHT;
    }

    public static DataHolder getInstance() {
        return dataHolder;
    }

    public Uri getRingtone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.RINGTONE, Context.MODE_PRIVATE);
        ringtone = sharedPreferences.getString(ConstantsClass.RINGTONE, RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI);
        return Uri.parse(ringtone);
    }

    public void setRingtone(String ringtone, Context context) {
        this.ringtone = ringtone;
        SharedPreferences.Editor editor = context.getSharedPreferences(ConstantsClass.RINGTONE, Context.MODE_PRIVATE).edit();
        editor.putString(ConstantsClass.RINGTONE, ringtone);
        editor.apply();
        editor.commit();
    }

    public Boolean getShowTutorial(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.SHOW_TUTORIAL, Context.MODE_PRIVATE);
        showTutorial = sharedPreferences.getBoolean(ConstantsClass.SHOW_TUTORIAL, true);
        return showTutorial;
    }

    public void setShowTutorial(Boolean showTutorial, Context context) {
        this.showTutorial = showTutorial;
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.SHOW_TUTORIAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ConstantsClass.SHOW_TUTORIAL, showTutorial);
        editor.apply();
        editor.commit();

    }

    public RecyclerView.EdgeEffectFactory recyclerViewEdgeEffectFactory(Context context) {
        EdgeEffect edge = new EdgeEffect(context);
        edge.setColor(DataHolder.getInstance().getAccentColorColor(context));
        return new RecyclerView.EdgeEffectFactory() {
            @NonNull
            @Override
            protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
                return edge;
            }
        };
    }

    public int getThemeMode() {
        switch (themeMode) {
            case ConstantsClass.LIGHT:
                return AppCompatDelegate.MODE_NIGHT_NO;
            case ConstantsClass.DARK:
                return AppCompatDelegate.MODE_NIGHT_YES;
            case ConstantsClass.DEFAULT:
                return AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        }
        return AppCompatDelegate.MODE_NIGHT_NO;
    }

    public void setThemeMode(Context context, String themeMode) {
        this.themeMode = themeMode;
        saveTheme(context);
    }

    public String getTheme() {
        return themeMode;
    }

    public String getTheme(Context context) {
        loadTheme(context);
        return themeMode;
    }

    private void loadTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.THEME, Context.MODE_PRIVATE);
        setThemeMode(context, sharedPreferences.getString(ConstantsClass.THEME, ConstantsClass.LIGHT));
    }

    private void saveTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantsClass.THEME, DataHolder.getInstance().getTheme());
        editor.apply();
        editor.commit();
    }

    public void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantsClass.HOME_LIST, new Gson().toJson(DataHolder.getInstance().getAllTimerGroups()));
        editor.apply();
        editor.commit();
        updateMap();
        setListTimerGroup();
    }

    public void loadData(Context context) {
        if (DataHolder.getInstance().getAllTimerGroups() != null && DataHolder.getInstance().getAllTimerGroups().size() > 0) {
            DataHolder.getInstance().setListTimerGroup(DataHolder.getInstance().getAllTimerGroups());
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
            ArrayList<TimerGroup> list = new Gson().fromJson(sharedPreferences.getString(ConstantsClass.HOME_LIST, new Gson().toJson(new ArrayList<TimerGroup>())), new TypeToken<List<TimerGroup>>() {
            }.getType());
            if (list != null && list.size() > 0) {
                DataHolder.getInstance().setAllTimerGroups(list);
            } else {
                DataHolder.getInstance().setAllTimerGroups(new ArrayList<>());
            }
        }
        loadTheme(context);
        updateMap();
        setListTimerGroup();
    }

    public ColorStateList getAccentColor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        return ColorStateList.valueOf(sharedPreferences.getInt(ConstantsClass.ACCENT_COLOR, ContextCompat.getColor(context, R.color.accent)));
    }

    public int getAccentColorColor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(ConstantsClass.ACCENT_COLOR, ContextCompat.getColor(context, R.color.accent));
    }

    public boolean getVibration(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(ConstantsClass.VIBRATION_BOOL, true);
    }

    public void setVibration(Context context, boolean bool) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ConstantsClass.VIBRATION_BOOL, bool);
        editor.apply();
        editor.commit();
    }

    public void setAccentColor(Context context, int color) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ConstantsClass.HOME_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ConstantsClass.ACCENT_COLOR, color);
        editor.apply();
        editor.commit();
    }

    public void updateName(int position, String newName) {
        for (TimerGroup timerGroup : allTimerGroups) {
            for (TimerGroup tg : timerGroup.getListTimerGroup()) {
                if (tg.getName().equals(allTimerGroups.get(position).getName())) {
                    tg.setName(newName);
                }
            }
        }
        allTimerGroups.get(position).setName(newName);
        updateMap();
    }

    private void setListTimerGroup() {
        if (stackNavigation.empty())
            listTimerGroup = allTimerGroups;
        else
            listTimerGroup = allTimerGroups.get(mapTimerGroups.get(stackNavigation.peek())).getListTimerGroup();
    }

    public List<TimerGroup> getAllTimerGroups() {
        if (allTimerGroups == null) allTimerGroups = new ArrayList<>();
        return allTimerGroups;
    }

    public void setAllTimerGroups(List<TimerGroup> allTimerGroups) {
        this.allTimerGroups = allTimerGroups;
    }

    public String printAllList() {
        return new Gson().toJson(DataHolder.getInstance().getAllTimerGroups());
    }

    public Map<String, Integer> getMapTimerGroups() {
        return mapTimerGroups;
    }

    public Boolean getDisableButtonClick() {
        return disableButtonClick;
    }

    public void setDisableButtonClick(Boolean disableButtonClick) {
        this.disableButtonClick = disableButtonClick;
    }

    public List<TimerGroup> getListTimerGroup() {
        return listTimerGroup;
    }

    public void setListTimerGroup(List<TimerGroup> listTimerGroup) {
        this.listTimerGroup = listTimerGroup;
    }

    public Stack<String> getStackNavigation() {
        return stackNavigation;
    }

    public void updateMap() {
        mapTimerGroups.clear();
        for (int i = 0; i < allTimerGroups.size(); i++) {
            mapTimerGroups.put(allTimerGroups.get(i).getName(), i);
        }
    }

    public ColorStateList iconTint(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != 0)
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTintDark));
        else
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTint));
    }

    public ColorStateList iconTintDark(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != 0)
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTint));
        else
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTintDark));
    }

    public ColorStateList backgroundTint(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != 0)
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.backgroundTint));
        else
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.backgroundTintDark));
    }


    public ColorStateList backgroundTintDark(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != 0)
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.backgroundTintDark));
        else
            return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.backgroundTint));
    }

    public ColorStateList iconTintAdvanced(Context context) {
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_NO) != 0) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTintDark));
            } else {
                return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTint));
            }
        } else if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_YES) != 0) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTintDark));
            } else {
                return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTint));
            }
        }
        return ColorStateList.valueOf(ContextCompat.getColor(context, R.color.iconTint));
    }
}
