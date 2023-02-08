package com.example.puzzlegam.adapter.adapterRecyler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.puzzlegam.R;
import com.example.puzzlegam.databinding.CustomeStageBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.models.Stage;
import com.example.puzzlegam.interfaces.ClickedItemListener;

import java.util.List;


public class StageAdapter  extends RecyclerView.Adapter<StageAdapter.StageHolder> {

    List<Stage> list;
    ClickedItemListener listener;
    int values;
    int countAllQuestion , countTrueQuestion;

    public StageAdapter( List<Stage> list, ClickedItemListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public List<Stage> getList() {
        return list;
    }
    public void setList(List<Stage> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomeStageBinding binding = CustomeStageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new StageHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull StageHolder holder, int position) {
        Stage s = list.get(position);
        int id = list.get(position).getLevelNo();
        int allPoint = list.get(position).getUnlockPoints();

        MyViewModel.getPointByLevel( id, new ListenerDetailsQuestion() {
            @Override
            public void onChangeValue(int value) {
                values = value;
            }
        });

        holder.binding.tvLevel.setText("level : "+s.getLevelNo());
        holder.binding.tvPointsCustom.setText(String.valueOf(s.getUnlockPoints()));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             listener.clickedItem(id, allPoint);
            }
        });





        MyViewModel.getAllCountQuestionByLevel(id, new ListenerDetailsQuestion() {
            @Override
            public void onChangeValue(int value) {
                countAllQuestion = value;
            }
        });

        MyViewModel.getAllTrueAnswersByLevel(true, id, new ListenerDetailsQuestion() {
            @Override
            public void onChangeValue(int value) {
                countTrueQuestion = value;
            }
        });

        float result = countAllQuestion / 5;
        float res = countTrueQuestion /result;


        if (values>=allPoint){
            holder.binding.ivLock.setImageResource(R.drawable.ic_un_lock);
            holder.binding.getRoot().setClickable(true);
            holder.binding.ratingBar.setRating(res);
        }else{
            holder.binding.ivLock.setImageResource(R.drawable.ic_lock);
            holder.binding.getRoot().setClickable(false);
            holder.binding.ratingBar.setVisibility(View.GONE);

        }



    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class StageHolder extends RecyclerView.ViewHolder{
        CustomeStageBinding binding;
        public StageHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomeStageBinding.bind(itemView);
        }
    }
}
