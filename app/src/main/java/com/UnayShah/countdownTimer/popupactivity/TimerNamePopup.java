package com.UnayShah.countdownTimer.popupactivity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.UnayShah.countdownTimer.HomeActivity;
import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.RecyclerAdapter;
import com.UnayShah.countdownTimer.common.DataHolder;
import com.UnayShah.countdownTimer.timers.TimerGroup;
import com.UnayShah.countdownTimer.timers.TimerGroupType;
import com.google.android.material.textfield.TextInputEditText;

public class TimerNamePopup extends PopupWindow implements View.OnClickListener, View.OnFocusChangeListener, PopupWindow.OnDismissListener, TextWatcher {
    TextInputEditText editTextTimerName;
    Button setTimerNameButton;
    Button cancelSetTimerNameButton;
    View view;
    Integer position;
    RecyclerAdapter recyclerAdapter;

    public TimerNamePopup(View view, RecyclerAdapter recyclerAdapter) {
        this(view, recyclerAdapter, null);
    }

    public TimerNamePopup(View view, RecyclerAdapter recyclerAdapter, Integer position) {
        super(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.position = position;
        setOutsideTouchable(true);
        setTouchable(true);
        setFocusable(true);
        this.view = view;
        this.recyclerAdapter = recyclerAdapter;
        init();
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

    private void init() {
        editTextTimerName = view.findViewById(R.id.editTextTimerName);
        setTimerNameButton = view.findViewById(R.id.setTimerNameButton);
        cancelSetTimerNameButton = view.findViewById(R.id.cancelSetTimerNameButton);
        editTextTimerName.setText(position == null ? "" : DataHolder.getInstance().getListTimerGroup().get(position).getName());
        setTimerNameButton.setEnabled(true);

        setOnDismissListener(this);
        editTextTimerName.addTextChangedListener(this);
        editTextTimerName.setOnFocusChangeListener(this);
        setTimerNameButton.setOnClickListener(this);
        cancelSetTimerNameButton.setOnClickListener(this);
        DataHolder.getInstance().setDisableButtonClick(false);
    }

    private void cancelSetTimerName() {
        dismiss();
    }

    private void setTimerName() {
        if (DataHolder.getInstance().getMapTimerGroups().containsKey(editTextTimerName.getText().toString())) {
            setTimerInRecyclerView(DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(editTextTimerName.getText().toString())));
        } else
            setTimerInRecyclerView(new TimerGroup(editTextTimerName.getText().toString(), TimerGroupType.TIMER_GROUP));
    }

    private void setTimerInRecyclerView(TimerGroup timerGroup) {
        if (position == null) {
            DataHolder.getInstance().getAllTimerGroups().add(timerGroup);
        } else {
            if (DataHolder.getInstance().getStackNavigation().empty()) {
                DataHolder.getInstance().updateName(position, timerGroup.getName());
            } else {
                DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getListTimerGroup().set(position, timerGroup);
            }
        }
        DataHolder.getInstance().saveData(getContentView().getContext());
        recyclerAdapter.notifyDataSetChanged();
        dismiss();
        HomeActivity.autoScroll(DataHolder.getInstance().getListTimerGroup().size()-1);
    }

    @Override
    public void onClick(View view) {
        DataHolder.getInstance().setDisableButtonClick(true);
        if (view.getId() == setTimerNameButton.getId() && !editTextTimerName.getText().toString().equals(""))
            setTimerName();
        else if (view.getId() == cancelSetTimerNameButton.getId()) cancelSetTimerName();
        else DataHolder.getInstance().setDisableButtonClick(false);

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            Drawable drawable = ContextCompat.getDrawable(getContentView().getContext(), R.drawable.edit_text_selected);
            drawable.setTint(DataHolder.getInstance().getAccentColorColor(getContentView().getContext()));
            view.setBackground(drawable);
        } else {
            view.setBackgroundResource(R.drawable.edit_text);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        int length = s.length();
        if (length > 0 && !text.matches("([\\w\\d]+ ?)+[A-zZ-z0-9]*")) {
            s.delete(length - 1, length);
        }
        setTimerNameButton.setEnabled(length > 0 && !DataHolder.getInstance().getMapTimerGroups().containsKey(s.toString()));
    }

    @Override
    public void onDismiss() {
        DataHolder.getInstance().setDisableButtonClick(false);
        super.dismiss();
    }
}
