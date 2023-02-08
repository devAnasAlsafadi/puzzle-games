package com.example.puzzlegam.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.puzzlegam.R;
import com.example.puzzlegam.controller.ControllerGames;
import com.example.puzzlegam.databinding.CustomeCompleteBinding;
import com.example.puzzlegam.databinding.CustomeOptionsBinding;
import com.example.puzzlegam.databinding.CustomeTrueFalseBinding;
import com.example.puzzlegam.databinding.FragmentPagerBinding;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.interfaces.CheckAnswersListener;
import com.example.puzzlegam.interfaces.TrueFalseValue;
import com.example.puzzlegam.interfaces.TypeQuestions;
import com.example.puzzlegam.views.prefs.AppSharedPreference;


public class PagerFragment extends Fragment {

    private static final String KEYS_QUESTION ="key_question" ;

    Questions questions;
    ControllerGames controllerGames;

    public PagerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        controllerGames = new ControllerGames(context);

    }



    public static PagerFragment newInstance(Questions questions) {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();

        args.putSerializable(KEYS_QUESTION,questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
             questions = (Questions) getArguments().getSerializable(KEYS_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = controllerGames.getLayoutInflaterFragment(inflater,container,questions);
        return view;
    }


    @Override
    public void onStop() {
        super.onStop();
        controllerGames.timerCancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        controllerGames.timerCancel();
    }
}