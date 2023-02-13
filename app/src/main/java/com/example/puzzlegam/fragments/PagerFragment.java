package com.example.puzzlegam.fragments;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.FragmentPagerBinding;
import com.example.puzzlegam.db.models.Questions;
import com.example.puzzlegam.interfaces.CheckAnswersListener;



public class PagerFragment extends Fragment implements View.OnClickListener {

    private static final String KEYS_QUESTION ="key_question" ;
    private Questions questions;
    public CheckAnswersListener listener;
    private int trueCounter = 0;
    private int falseCounter=0;
    private FragmentPagerBinding  binding;
    private  String text;


    public PagerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CheckAnswersListener) {
            listener = (CheckAnswersListener) context;
        } else {
            throw new ClassCastException("your activity not implement in CheckAnswersListener ");
        }

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
          binding = FragmentPagerBinding.inflate(inflater,container,false);
          return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializeView();
    }


    private  void  initializeView(){
        setOnClickListener();
        setData();
    }
    private void  setOnClickListener(){
        binding.btnAnswers1.setOnClickListener(this);
        binding.btnAnswers2.setOnClickListener(this);
        binding.btnAnswers3.setOnClickListener(this);
        binding.btnAnswers4.setOnClickListener(this);
    }

    private  void  setData(){
        binding.tvQuestion.setText(questions.getTitle());
        binding.btnAnswers1.setText(questions.getAnswer1());
        binding.btnAnswers2.setText(questions.getAnswer2());
        binding.btnAnswers3.setText(questions.getAnswer3());
        binding.btnAnswers4.setText(questions.getAnswer4());
    }

    public void checkAnswers(String answers){
        if (answers.equals(questions.getTrueAnswer())){
            trueCounter++;
            listener.trueAnswers(trueCounter,questions.getPoints());
        }else {
            falseCounter++;
            listener.falseAnswers(questions.getHint(),falseCounter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_answers1:
                text = binding.btnAnswers1.getText().toString();
                checkAnswers(text);
                break;
            case  R.id.btn_answers2:
                text = binding.btnAnswers2.getText().toString();
                checkAnswers(text);
                break;
            case  R.id.btn_answers3:
                text = binding.btnAnswers3.getText().toString();
                checkAnswers(text);
                break;
            case  R.id.btn_answers4:
                text = binding.btnAnswers4.getText().toString();
                checkAnswers(text);
                break;

        }
    }
}