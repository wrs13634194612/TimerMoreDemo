package com.UnayShah.countdownTimer.model;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.widget.NumberPicker;

import com.UnayShah.countdownTimer.R;

public class ThemedNumberPickerDark extends NumberPicker {
    public ThemedNumberPickerDark(Context context) {
        super(context);
    }

    public ThemedNumberPickerDark(Context context, AttributeSet attrs) {
        super(new ContextThemeWrapper(context, R.style.ThemeOverlay_MaterialComponents_PopupNumberPicker_Dark), attrs);
    }
}
