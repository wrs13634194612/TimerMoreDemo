package com.UnayShah.countdownTimer.model;

import android.content.Context;
import android.graphics.drawable.PaintDrawable;

public class ActiveItemBackgroundFactory {
    private static ActiveItemBackground activeItemBackground;

    public static PaintDrawable getInstance(Context context, int timePassed, int totalTime, int width, int height) {
        if (activeItemBackground == null)
            activeItemBackground = new ActiveItemBackground(context);
        activeItemBackground.resize(timePassed, totalTime, width, height);
        return activeItemBackground.getPd();
    }
}
