package com.example.puzzlegam.dialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.example.puzzlegam.databinding.FragmentExitBinding;
import com.example.puzzlegam.interfaces.YesNoListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeleteDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeleteDataFragment extends DialogFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    YesNoListener listener;

    public DeleteDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof YesNoListener ){
            listener = (YesNoListener) context;
        }else {
            throw new ClassCastException("your activity not implement in YesNoListener ");
        }
    }

    public static DeleteDataFragment newInstance(String param1, String param2) {
        DeleteDataFragment fragment = new DeleteDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        FragmentExitBinding binding = FragmentExitBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());
        binding.tvExit.setText(mParam1);
        binding.tvDescription.setText(mParam2);
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chickYesListener();
            }
        });


        return builder.create();
    }
}