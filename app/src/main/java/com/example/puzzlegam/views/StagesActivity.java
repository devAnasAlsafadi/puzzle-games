package com.example.puzzlegam.views;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.puzzlegam.R;
import com.example.puzzlegam.adapter.adapterRecyler.StageAdapter;
import com.example.puzzlegam.databinding.ActivityStagesBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.interfaces.ClickedItemListener;

import java.util.ArrayList;
import java.util.List;

public class StagesActivity extends AppCompatActivity  {

    ActivityStagesBinding binding;
    List<Stage> listStage = new ArrayList<>();
    static MyViewModel viewModel ;
    StageAdapter adapter;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mp = MediaPlayer.create(this, R.raw.click_item);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        viewModel.getAllStage().observe(this, new Observer<List<Stage>>() {
            @Override
            public void onChanged(List<Stage> stages) {
                Log.d("TAG", "onChanged: "+stages.size());
                adapter.setList(stages);
                listStage.addAll(stages);
            }
        });


        binding.ivBackStages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();
        initializeAdapter();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initializeAdapter(){
        adapter = new StageAdapter(listStage, new ClickedItemListener() {
            @Override
            public void clickedItem(int number , int allPoint) {
                mp.start();
                Intent intent = new Intent(getApplicationContext(),DetailsStage.class);
                intent.putExtra("numberId",number);
                intent.putExtra("allPoint",allPoint);
                startActivity(intent);
            }
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
    }









}