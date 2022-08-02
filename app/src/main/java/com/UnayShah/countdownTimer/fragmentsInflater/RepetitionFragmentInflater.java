package com.UnayShah.countdownTimer.fragmentsInflater;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.UnayShah.countdownTimer.R;
import com.UnayShah.countdownTimer.common.DataHolder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class RepetitionFragmentInflater extends Fragment implements View.OnClickListener {

    MaterialButton loopButton;
    ColorStateList accentColorStateList;
    MaterialTextView repsTextView;
    MaterialButton increaseReps;
    MaterialButton decreaseReps;
    LinearLayout repsLinearLayout;
    TextView repsTitleTextView;
    LayerDrawable layerDrawable;
    GradientDrawable border;
    GradientDrawable shape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repetition_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        loopButton = view.findViewById(R.id.loop_button);
        loopButton.setOnClickListener(this);
        loopButton.setCheckable(true);
        loopButton.setIconTintResource(R.color.iconTint);
        loopButton.setChecked(true);

        repsLinearLayout = view.findViewById(R.id.reps_linear_layout);
        repsTitleTextView = view.findViewById(R.id.reps_title_textView);
        repsTextView = view.findViewById(R.id.reps_textView);
        increaseReps = view.findViewById(R.id.increase_reps);
        decreaseReps = view.findViewById(R.id.decrease_reps);

        increaseReps.setOnClickListener(this);
        decreaseReps.setOnClickListener(this);

        if (getContext() != null)
            accentColorStateList = DataHolder.getInstance().getAccentColor(getContext());


        shape = new GradientDrawable();
        border = new GradientDrawable();
        shape.setTintList(DataHolder.getInstance().iconTintDark(getContext()));
        shape.setCornerRadius(getResources().getDimension(R.dimen.padding_huge));
        border.setTint(DataHolder.getInstance().getAccentColorColor(getContext()));
        border.setCornerRadius(getResources().getDimension(R.dimen.padding_huge));

        setRepsLinearLayout();
        setRepsTextView();
        setTint();
    }

    private void setRepsLinearLayout() {
        layerDrawable = new LayerDrawable(new Drawable[]{border, shape});
        layerDrawable.setLayerInset(1, (int) getResources().getDimension(R.dimen.padding_vvsmall), (int) getResources().getDimension(R.dimen.padding_vvsmall), (int) getResources().getDimension(R.dimen.padding_vvsmall), (int) getResources().getDimension(R.dimen.padding_vvsmall));
        repsLinearLayout.setBackground(layerDrawable);
    }

    @Override
    public void onClick(View v) {
        if (!DataHolder.getInstance().getDisableButtonClick()) {
            if (v.getId() == loopButton.getId()) {
                DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).setLooped(!DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getLooped());
                setTint();
            } else if (DataHolder.getInstance().getMapTimerGroups().containsKey(DataHolder.getInstance().getStackNavigation().peek()) && !DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getLooped()) {
                if (v.getId() == increaseReps.getId()) increaseReps();
                else if (v.getId() == decreaseReps.getId()) decreaseReps();
            } else if (DataHolder.getInstance().getMapTimerGroups().containsKey(DataHolder.getInstance().getStackNavigation().peek()) && DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getLooped()) {
                Toast.makeText(getContext(), "Cannot change reps when looped", Toast.LENGTH_SHORT).show();
            } else DataHolder.getInstance().setDisableButtonClick(false);
        }
        DataHolder.getInstance().setDisableButtonClick(false);
    }


    private void increaseReps() {
        DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).incrementReps();
        setRepsTextView();
    }

    private void decreaseReps() {
        DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).decrementReps();
        setRepsTextView();
    }

    private void setRepsTextView() {
        repsTextView.setText(String.format("%" + 2 + "s", (DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getReps().toString())));
        DataHolder.getInstance().setDisableButtonClick(false);
        DataHolder.getInstance().saveData(getContext());
    }

    private void setTint() {
        if (DataHolder.getInstance().getMapTimerGroups().containsKey(DataHolder.getInstance().getStackNavigation().peek()) && !DataHolder.getInstance().getAllTimerGroups().get(DataHolder.getInstance().getMapTimerGroups().get(DataHolder.getInstance().getStackNavigation().peek())).getLooped()) {
            loopButton.setIconTint(DataHolder.getInstance().iconTintDark(getContext()));
            loopButton.setRippleColor(accentColorStateList);
            if (getContext() != null)
                loopButton.setBackgroundTintList(DataHolder.getInstance().iconTint(getContext()));
            border.setTint(DataHolder.getInstance().getAccentColorColor(getContext()));
            shape.setTintList(DataHolder.getInstance().iconTint(getContext()));
            increaseReps.setIconTint(DataHolder.getInstance().iconTintDark(getContext()));
            increaseReps.setRippleColor(DataHolder.getInstance().iconTint(getContext()));
            increaseReps.setBackgroundTintList(DataHolder.getInstance().backgroundTint(getContext()));
            decreaseReps.setIconTint(DataHolder.getInstance().iconTintDark(getContext()));
            decreaseReps.setRippleColor(DataHolder.getInstance().iconTint(getContext()));
            decreaseReps.setBackgroundTintList(DataHolder.getInstance().backgroundTint(getContext()));
            repsTitleTextView.setTextColor(DataHolder.getInstance().iconTintDark(getContext()));
            repsTextView.setTextColor(DataHolder.getInstance().iconTintDark(getContext()));
        } else {
            loopButton.setIconTintResource(R.color.iconTintDark);
            loopButton.setRippleColorResource(R.color.iconTint);
            loopButton.setBackgroundTintList(accentColorStateList);
            border.setTint(ContextCompat.getColor(getContext(), R.color.material_on_primary_disabled));
            shape.setTintList(DataHolder.getInstance().backgroundTint(getContext()));
            increaseReps.setIconTint(DataHolder.getInstance().iconTint(getContext()));
            increaseReps.setRippleColor(DataHolder.getInstance().backgroundTint(getContext()));
            decreaseReps.setIconTint(DataHolder.getInstance().iconTint(getContext()));
            decreaseReps.setRippleColor(DataHolder.getInstance().backgroundTint(getContext()));
            repsTitleTextView.setTextColor(DataHolder.getInstance().iconTint(getContext()));
            repsTextView.setTextColor(DataHolder.getInstance().iconTint(getContext()));
        }
    }
}
