package com.UnayShah.countdownTimer.model;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.common.DataHolder;

public class ActiveItemBackground extends ShapeDrawable.ShaderFactory {
    private final int[] gradientColors;
    private final float[] gradientPositions;
    Context context;
    int totalTime;
    int timePassed;
    PaintDrawable pd;

    public ActiveItemBackground(Context context) {
        this.context = context;
        gradientColors = new int[]{DataHolder.getInstance().getAccentColorColor(context), DataHolder.getInstance().getAccentColorColor(context), DataHolder.getInstance().iconTintAdvanced(context).getDefaultColor(), DataHolder.getInstance().iconTintAdvanced(context).getDefaultColor()};
        gradientPositions = new float[]{0, 0.5f, 0.5f, 1};
        pd = new PaintDrawable();
    }

    public PaintDrawable getPd() {
        pd.setShape(new RectShape());
        pd.setCornerRadius(context.getResources().getDimension(R.dimen.padding_small_medium));
        pd.setShaderFactory(this);
        return pd;
    }

    private void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    private void setTimePassed(int timePassed) {
        this.timePassed = timePassed;
    }

    public Shader resize(int timePassed, int totalTime, int width, int height) {
        setTimePassed(timePassed);
        setTotalTime(totalTime);
        gradientPositions[1] = ((float) timePassed / totalTime);
        gradientPositions[2] = ((float) timePassed / totalTime);
        gradientColors[0] = DataHolder.getInstance().getAccentColorColor(context);
        gradientColors[1] = DataHolder.getInstance().getAccentColorColor(context);
        gradientColors[2] = DataHolder.getInstance().iconTintAdvanced(context).getDefaultColor();
        gradientColors[3] = DataHolder.getInstance().iconTintAdvanced(context).getDefaultColor();
        return resize(width, height);
    }


    @Override
    public Shader resize(int width, int height) {
        return new LinearGradient(0, height, width, height, gradientColors, gradientPositions, Shader.TileMode.CLAMP);
    }
}
