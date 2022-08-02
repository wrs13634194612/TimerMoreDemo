package com.UnayShah.countdownTimer.model;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.UnayShah.countdownTimer.common.ConstantsClass;

public class CustomAnimations {

    public void initTransitionAnimations(View... view) {
        for (View v : view) slideUp(v);
    }

    public void endTransitionAnimations(View... view) {
        for (View v : view) slideDown(v);
    }

    public ViewPropertyAnimator slideUp(View view) {
        return view.animate().translationY(Resources.getSystem().getDisplayMetrics().heightPixels / 4.0f).alpha(0).setDuration(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().translationY(0).alpha(1f).setDuration(ConstantsClass.VIBRATE_MEDIUM_LONG);
                return;
            }
        });
    }

    public ViewPropertyAnimator slideDown(View view) {
        return view.animate().translationY(0).alpha(1f).setDuration(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().translationY(Resources.getSystem().getDisplayMetrics().heightPixels / 4.0f).alpha(0).setDuration(ConstantsClass.VIBRATE_MEDIUM_LONG);
                return;
            }
        });
    }

}
