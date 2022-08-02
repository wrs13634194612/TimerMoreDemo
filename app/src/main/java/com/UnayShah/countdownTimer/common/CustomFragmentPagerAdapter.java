package com.UnayShah.countdownTimer.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.UnayShah.countdownTimer.tutorialFragments.TutorialEmpty;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialHomeScreen;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialHomeScreenEmpty;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialHomeScreenPopupTimername;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialTimerScreen;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialTimerScreenEmpty;
import com.UnayShah.countdownTimer.tutorialFragments.TutorialTimerScreenPopupNumberPicker;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> containersFragment;

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        containersFragment = new ArrayList<>();
        if (DataHolder.getInstance().getThemeMode() != AppCompatDelegate.getDefaultNightMode())
            AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
        containersFragment.add(new TutorialHomeScreenEmpty());
        containersFragment.add(new TutorialHomeScreenPopupTimername());
        containersFragment.add(new TutorialHomeScreen());
        containersFragment.add(new TutorialTimerScreenEmpty());
        containersFragment.add(new TutorialTimerScreenPopupNumberPicker());
        containersFragment.add(new TutorialTimerScreen());
        containersFragment.add(new TutorialEmpty());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return containersFragment.get(position);
    }

    @Override
    public int getCount() {
        return containersFragment.size();
    }
}
