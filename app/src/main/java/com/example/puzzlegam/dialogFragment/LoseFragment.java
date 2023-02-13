package com.example.puzzlegam.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.puzzlegam.databinding.FragmentLoseBinding;
import com.example.puzzlegam.interfaces.YesNoListener;

import java.util.Timer;
import java.util.TimerTask;


public class LoseFragment extends DialogFragment {


    private static final String ARG_HINT = "hint";

    private String hint;

    YesNoListener listener;

    public LoseFragment() {
        // Required empty public constructor
    }


    public static LoseFragment newInstance(String hint) {
        LoseFragment fragment = new LoseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HINT, hint);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hint = getArguments().getString(ARG_HINT);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentLoseBinding binding = FragmentLoseBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        binding.tvTrueValue.setText("("+hint+")");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        },1000);
        return builder.create();
    }
}