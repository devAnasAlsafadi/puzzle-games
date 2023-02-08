package com.example.puzzlegam.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.puzzlegam.R;
import com.example.puzzlegam.controller.SoundController;
import com.example.puzzlegam.databinding.EditCreateUserBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    private EditCreateUserBinding binding;
    private String gender;
    private MyViewModel model;
    private String spinnerName;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EditCreateUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        model = new ViewModelProvider(this).get(MyViewModel.class);
        controlGenderSelection();
        binding.btnCreate.setVisibility(View.GONE);
        binding.ivEdit.setVisibility(View.GONE);

        controlSpinnerSelection();

        model.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                User user = users.get(0);
                binding.ivEditDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkData()){
                            model.updateUser(getUser(user.getUserId()));
                            onBackPressed();
                        }else {
                            Snackbar.make(binding.getRoot(), "Please Enter a date : ", Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });


        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();

            }
        });

    }


    private User getUser(int id){
        String userName = binding.etUserName.getText().toString();
        String email = binding.etEmail.getText().toString();
        User user = new User(id,userName,email,calendar.getTime(),gender,spinnerName);
        return user;
    }

       private void controlSpinnerSelection(){
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerName = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDatePicker(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                binding.etDate.setText(year+"/"+monthOfYear+"/"+dayOfMonth);
                calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);


            }
            },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show(getSupportFragmentManager(),"");
    }
    private void controlGenderSelection(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                gender = i == R.id.male ? "Male":"Female";
            }
        });
    }
    private boolean checkData(){
        if (!binding.etUserName.getText().toString().isEmpty()  &&
                !gender.isEmpty()&&
                !binding.etEmail.getText().toString().isEmpty()&&
                !binding.etDate.getText().toString().isEmpty()){
            return true;
        }
        else
            return false;
    }


}