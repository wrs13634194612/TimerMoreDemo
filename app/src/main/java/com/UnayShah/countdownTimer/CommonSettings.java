package com.UnayShah.countdownTimer;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.UnayShah.countdownTimer.common.ConstantsClass;
import com.UnayShah.countdownTimer.common.DataHolder;
import com.UnayShah.countdownTimer.model.OnSubmitListener;
import com.UnayShah.countdownTimer.popupactivity.ConfirmationPopup;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.Arrays;
import java.util.List;

public class CommonSettings extends AppCompatActivity implements View.OnClickListener, MaterialButtonToggleGroup.OnButtonCheckedListener, TextWatcher, OnSubmitListener {

    private final int RINGTONE_REQUEST_CODE = 1;
    private MaterialTextView vibrationTextView;
    private MaterialTextView accentColourTextView;
    private MaterialTextView resetTextView;
    private MaterialTextView themeTextView;
    private MaterialTextView resetAllDataTextView;
    private MaterialTextView ringtoneTextView;
    private MaterialAutoCompleteTextView themeAutoCompleteTextView;
    private TextInputLayout themeTextInputLayout;
    private ImageView accentColourImageView;
    private LinearLayout accentColourLayout;
    private LinearLayout vibrationLayout;
    private LinearLayout themeMenuLayout;
    private MaterialButtonToggleGroup vibrationButtonGroup;
    private MaterialButton vibrationOffButton;
    private MaterialButton vibrationOnButton;
    private boolean confirmation = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (DataHolder.getInstance().getThemeMode() != AppCompatDelegate.getDefaultNightMode())
            AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_settings);
        init();
    }

    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    private void init() {
        accentColourLayout = findViewById(R.id.accent_color_view);
        accentColourTextView = findViewById(R.id.accent_color_textView);
        resetTextView = findViewById(R.id.reset_all_settings_textView);
        vibrationTextView = findViewById(R.id.vibration_textView);
        vibrationLayout = findViewById(R.id.vibration_view);
        accentColourImageView = findViewById(R.id.accent_color_preview);
        vibrationButtonGroup = findViewById(R.id.vibration_switch);
        vibrationOffButton = findViewById(R.id.vibration_switch_off);
        vibrationOnButton = findViewById(R.id.vibration_switch_on);
        themeAutoCompleteTextView = findViewById(R.id.theme_menu_autocompleteTextView);
        themeMenuLayout = findViewById(R.id.theme_layout);
        themeTextInputLayout = findViewById(R.id.theme_menu_textInputLayout);
        themeTextView = findViewById(R.id.theme_textView);
        resetAllDataTextView = findViewById(R.id.reset_all_data_textView);
        ringtoneTextView = findViewById(R.id.ringtone_textView);
        MaterialToolbar timerToolbar = findViewById(R.id.timer_toolbar);
        setSupportActionBar(timerToolbar);
        vibrationButtonGroup.setSingleSelection(true);

        accentColourTextView.setFocusable(true);
        accentColourTextView.setClickable(true);
        vibrationTextView.setClickable(true);
        vibrationTextView.setFocusable(true);
        themeTextView.setClickable(true);
        themeTextView.setFocusable(true);
        themeTextInputLayout.setClickable(true);
        themeTextInputLayout.setFocusable(true);

        themeTextView.setOnClickListener(this);
        themeAutoCompleteTextView.setOnClickListener(this);
        themeMenuLayout.setOnClickListener(this);
        themeTextInputLayout.setOnClickListener(this);
        vibrationLayout.setOnClickListener(this);
        accentColourLayout.setOnClickListener(this);
        accentColourTextView.setOnClickListener(this);
        resetTextView.setOnClickListener(this);
        vibrationTextView.setOnClickListener(this);
        accentColourImageView.setOnClickListener(this);
        resetAllDataTextView.setOnClickListener(this);
        ringtoneTextView.setOnClickListener(this);
        vibrationButtonGroup.addOnButtonCheckedListener(this);
        timerToolbar.setNavigationOnClickListener(v -> onBackPressed());


        GradientDrawable accentDrawable = new GradientDrawable();
        accentDrawable.setShape(GradientDrawable.OVAL);
        accentDrawable.setColor(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        accentDrawable.setSize(200, 200);
        accentColourImageView.setImageDrawable(accentDrawable);

        initVibrationGroup();
        initThemeMenu();
    }

    private void initThemeMenu() {
        List<String> themeMenuList;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            themeMenuList = Arrays.asList(ConstantsClass.LIGHT, ConstantsClass.DARK, ConstantsClass.DEFAULT);
        else
            themeMenuList = Arrays.asList(ConstantsClass.LIGHT, ConstantsClass.DARK);
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this, R.layout.theme_menu, themeMenuList);
        themeAutoCompleteTextView.setText(DataHolder.getInstance().getTheme(), false);
        themeAutoCompleteTextView.setAdapter(themeAdapter);
        themeAutoCompleteTextView.setSelection(themeAdapter.getPosition(DataHolder.getInstance().getTheme(getApplicationContext())));
        themeAutoCompleteTextView.addTextChangedListener(this);
    }

    private void setVibrationGroupColours() {
        themeAutoCompleteTextView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        themeTextInputLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        if (getApplicationContext().getResources().getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_YES + 1) {
            if (DataHolder.getInstance().getThemeMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                vibrationOnButton.setTextColor(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOffButton.setTextColor(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOffButton.setBackgroundTintList(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOnButton.setBackgroundTintList(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                themeAutoCompleteTextView.setBackgroundColor(DataHolder.getInstance().iconTint(getApplicationContext()).getDefaultColor());
                themeTextInputLayout.setBackgroundColor(DataHolder.getInstance().iconTint(getApplicationContext()).getDefaultColor());
            } else {
                vibrationOnButton.setTextColor(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOffButton.setTextColor(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOffButton.setBackgroundTintList(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOnButton.setBackgroundTintList(DataHolder.getInstance().iconTint(getApplicationContext()));
                themeAutoCompleteTextView.setBackgroundColor(DataHolder.getInstance().iconTintDark(getApplicationContext()).getDefaultColor());
                themeTextInputLayout.setBackgroundColor(DataHolder.getInstance().iconTintDark(getApplicationContext()).getDefaultColor());
            }
        } else if (getApplicationContext().getResources().getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_NO + 1) {
            if (DataHolder.getInstance().getThemeMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                vibrationOnButton.setTextColor(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOffButton.setTextColor(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOffButton.setBackgroundTintList(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOnButton.setBackgroundTintList(DataHolder.getInstance().iconTintDark(getApplicationContext()));
            } else {
                vibrationOnButton.setTextColor(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOffButton.setTextColor(DataHolder.getInstance().iconTintDark(getApplicationContext()));
                vibrationOffButton.setBackgroundTintList(DataHolder.getInstance().iconTint(getApplicationContext()));
                vibrationOnButton.setBackgroundTintList(DataHolder.getInstance().iconTint(getApplicationContext()));
            }
        }
    }

    private void initVibrationGroup() {
        vibrationOffButton.setStrokeColor(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        vibrationOffButton.setStrokeWidth((int) getResources().getDimension(R.dimen.padding_vvsmall));
        vibrationOffButton.setCornerRadius((int) getResources().getDimension(R.dimen.padding_huge));

        vibrationOnButton.setStrokeColor(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        vibrationOnButton.setStrokeWidth((int) getResources().getDimension(R.dimen.padding_vvsmall));
        vibrationOnButton.setCornerRadius((int) getResources().getDimension(R.dimen.padding_huge));

        vibrationOnButton.setRippleColor(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        vibrationOffButton.setRippleColor(DataHolder.getInstance().getAccentColor(getApplicationContext()));
        setVibrationGroupColours();
        if (DataHolder.getInstance().getVibration(getApplicationContext())) {
            vibrationOnButton.setBackgroundTintList(DataHolder.getInstance().getAccentColor(getApplicationContext()));
            vibrationButtonGroup.check(vibrationOnButton.getId());
        } else {
            vibrationOffButton.setBackgroundTintList(DataHolder.getInstance().getAccentColor(getApplicationContext()));
            vibrationButtonGroup.check(vibrationOffButton.getId());
        }
        getWindow().setStatusBarColor(DataHolder.getInstance().getAccentColorColor(getApplicationContext()));
        DataHolder.getInstance().setDisableButtonClick(false);
    }

    @Override
    public void onClick(View v) {
        if (!DataHolder.getInstance().getDisableButtonClick()) {
            DataHolder.getInstance().setDisableButtonClick(true);
            if (v.getId() == accentColourLayout.getId() || v.getId() == accentColourTextView.getId() || v.getId() == accentColourImageView.getId()) {
                colorPicker();
            } else if (v.getId() == resetTextView.getId()) {
                reset();
            } else if (v.getId() == vibrationTextView.getId() || v.getId() == vibrationLayout.getId()) {
                toggleVibrationGroup();
            } else if (v.getId() == themeTextView.getId() || v.getId() == themeMenuLayout.getId() || v.getId() == themeTextInputLayout.getId() || v.getId() == themeAutoCompleteTextView.getId()) {
                themeAutoCompleteTextView.showDropDown();
                DataHolder.getInstance().setDisableButtonClick(false);
            } else if (v.getId() == resetAllDataTextView.getId()) {
                resetAllData();
            } else if (v.getId() == ringtoneTextView.getId()) {
                Intent ringtoneIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                ringtoneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, DataHolder.getInstance().getRingtone(getApplicationContext()));
                ringtoneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                startActivityForResult(ringtoneIntent, RINGTONE_REQUEST_CODE);
                DataHolder.getInstance().setDisableButtonClick(false);
            } else DataHolder.getInstance().setDisableButtonClick(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RINGTONE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Uri toneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                DataHolder.getInstance().setRingtone(toneUri.toString(), getApplicationContext());
            }
        }
    }

    private void resetAllData() {
        View confirmationPopupView = getLayoutInflater().inflate(R.layout.confirmation_popup, (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), false);
        PopupWindow confirmationPopup = new ConfirmationPopup(confirmationPopupView, this, getString(R.string.clear_all_data));
        confirmationPopup.setOnDismissListener(() -> {
            if (confirmation) {
                reset();
                DataHolder.getInstance().getAllTimerGroups().clear();
                DataHolder.getInstance().getMapTimerGroups().clear();
                DataHolder.getInstance().getListTimerGroup().clear();
                DataHolder.getInstance().getStackNavigation().clear();
                DataHolder.getInstance().saveData(getApplicationContext());
            }
            DataHolder.getInstance().setDisableButtonClick(false);
            confirmation = false;
        });
        confirmationPopup.showAtLocation(findViewById(R.id.common_settings), Gravity.CENTER, 0, 0);
    }

    private void toggleVibrationGroup() {
        if (vibrationButtonGroup.getCheckedButtonId() == vibrationOnButton.getId())
            vibrationButtonGroup.check(vibrationOffButton.getId());
        else if (vibrationButtonGroup.getCheckedButtonId() == vibrationOffButton.getId())
            vibrationButtonGroup.check(vibrationOnButton.getId());
    }

    private void reset() {
        View confirmationPopupView = getLayoutInflater().inflate(R.layout.confirmation_popup, (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0), false);
        PopupWindow confirmationPopup = new ConfirmationPopup(confirmationPopupView, this, getString(R.string.reset_all_settings));
        confirmationPopup.setOnDismissListener(() -> {
            if (confirmation) {
                DataHolder.getInstance().setAccentColor(getApplicationContext(), ContextCompat.getColor(getApplicationContext(), R.color.accent));
                accentColourImageView.setImageTintList(DataHolder.getInstance().getAccentColor(getApplicationContext()));
                vibrationButtonGroup.check(vibrationOnButton.getId());
                DataHolder.getInstance().setVibration(getApplicationContext(), true);
                initVibrationGroup();
                DataHolder.getInstance().setShowTutorial(true, getApplicationContext());
            }
            confirmation = false;
            DataHolder.getInstance().setDisableButtonClick(false);
        });
        confirmationPopup.showAtLocation(findViewById(R.id.common_settings), Gravity.CENTER, 0, 0);
    }

    private void colorPicker() {
        DataHolder.getInstance().setDisableButtonClick(false);
        new ColorPickerDialog.Builder(this)
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton(R.string.set_colour,
                        (ColorEnvelopeListener) (envelope, fromUser) -> {
                            DataHolder.getInstance().setAccentColor(getApplicationContext(), envelope.getColor());
                            accentColourImageView.setImageTintList(DataHolder.getInstance().getAccentColor(getApplicationContext()));
                            initVibrationGroup();
                        })
                .setNegativeButton(getString(R.string.cancel),
                        (dialogInterface, i) -> dialogInterface.dismiss())
                .attachAlphaSlideBar(false)
                .attachBrightnessSlideBar(false)
                .setBottomSpace(12)
                .show();
    }

    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        DataHolder.getInstance().setDisableButtonClick(true);
        DataHolder.getInstance().setVibration(getApplicationContext(), checkedId == vibrationOnButton.getId());
        initVibrationGroup();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        DataHolder.getInstance().setThemeMode(getApplicationContext(), s.toString());
        if (DataHolder.getInstance().getThemeMode() != AppCompatDelegate.getDefaultNightMode())
            AppCompatDelegate.setDefaultNightMode(DataHolder.getInstance().getThemeMode());
    }

    @Override
    public void confirmation(boolean bool) {
        this.confirmation = bool;
    }
}

