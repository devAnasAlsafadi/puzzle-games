package com.example.puzzlegam.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.FragmentWinBinding;
import com.example.puzzlegam.interfaces.CheckAnswersListener;
import com.example.puzzlegam.interfaces.NextPagerListener;

import java.util.Timer;
import java.util.TimerTask;


public class WinFragment extends DialogFragment {


    public WinFragment() {
        // Required empty public constructor
    }



    public static WinFragment newInstance() {
        WinFragment fragment = new WinFragment();
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        FragmentWinBinding binding = FragmentWinBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(binding.getRoot());


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dismiss();
            }
        },1000);


        return dialog.create();
    }
}