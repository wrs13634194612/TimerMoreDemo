package com.UnayShah.countdownTimer.popupactivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.common.DataHolder;
import com.UnayShah.countdownTimer.model.OnSubmitListener;
import com.google.android.material.button.MaterialButton;

public class ConfirmationPopup extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    OnSubmitListener onSubmitListener;
    TextView textView;
    MaterialButton yesButton;
    MaterialButton noButton;

    public ConfirmationPopup(View view, OnSubmitListener onSubmitListener, String textViewValue) {
        super(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        yesButton = view.findViewById(R.id.yes_button);
        noButton = view.findViewById(R.id.no_button);
        textView = view.findViewById(R.id.confirmation_textView);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        textView.setText(textViewValue);
        this.onSubmitListener = onSubmitListener;
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true);
        setOnDismissListener(this);
        DataHolder.getInstance().setDisableButtonClick(false);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        if (DataHolder.getInstance().getThemeMode() != AppCompatDelegate.getDefaultNightMode())
            AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
        super.showAtLocation(parent, gravity, x, y);
        View container = (View) getContentView().getParent();
        WindowManager wm = (WindowManager) getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.5f;
        wm.updateViewLayout(container, p);
        DataHolder.getInstance().setDisableButtonClick(false);
    }

    @Override
    public void onClick(View v) {
        if (!DataHolder.getInstance().getDisableButtonClick()) {
            DataHolder.getInstance().setDisableButtonClick(true);
            if (v.getId() == yesButton.getId()) {
                onSubmitListener.confirmation(true);
                dismiss();
            } else if (v.getId() == noButton.getId()) {
                onSubmitListener.confirmation(false);
                dismiss();
            } else DataHolder.getInstance().setDisableButtonClick(false);
        }
    }

    @Override
    public void onDismiss() {
        onSubmitListener.confirmation(false);
        super.dismiss();
    }
}
