package com.UnayShah.countdownTimer.tutorialFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.common.DataHolder;

public class TutorialEmpty extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (DataHolder.getInstance().getThemeMode() != AppCompatDelegate.getDefaultNightMode())
            AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
        return inflater.inflate(R.layout.tutorial_empty, container, false);
    }
}
