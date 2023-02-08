package com.example.puzzlegam.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.example.puzzlegam.R;
import com.example.puzzlegam.controller.SoundController;
import com.example.puzzlegam.databinding.ActivityProfileBinding;
import com.example.puzzlegam.databinding.EditCreateUserBinding;
import com.example.puzzlegam.db.MyViewModel;
import com.example.puzzlegam.db.database_interface.ListenerDetailsQuestion;
import com.example.puzzlegam.db.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences ;
    private SharedPreferences.Editor editor;
    private MyViewModel model;
    private Calendar calendar;
    private String gender;
    int yearCheck = 0;
    int monthCheck = 0;
    int dayCheck = 0;
    int id;
    private String spinnerName;
    private EditCreateUserBinding binding;
    private ActivityProfileBinding bindingProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MyViewModel.class);
        sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean profileValue = sharedPreferences.getBoolean("profile",false);
        if (profileValue){
            bindingProfile = ActivityProfileBinding.inflate(getLayoutInflater());
            setContentView(bindingProfile.getRoot());

            bindingProfile.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            bindingProfile.ivEditDone.setVisibility(View.GONE);
            model.getUser().observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    User user = users.get(0);
                    fillUserData(user);
                }
            });
            bindingProfile.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),EditActivity.class);
                    startActivity(intent);
                }
            });

            model.getAllTrueAnswers(true, new ListenerDetailsQuestion() {
                @Override
                public void onChangeValue(int value) {
                    bindingProfile.trueValue.setText(String.valueOf(value));

                }
            });
            model.getAllFalseAnswers(false, new ListenerDetailsQuestion() {
                @Override
                public void onChangeValue(int value) {
                    bindingProfile.falseValue.setText(String.valueOf(value));
                }
            });
            model.getAllPoint(new ListenerDetailsQuestion() {
                @Override
                public void onChangeValue(int value) {
                    bindingProfile.pointValue.setText(String.valueOf(value));
                }
            });

        }
        else {
            binding = EditCreateUserBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            binding.ivEdit.setVisibility(View.GONE);
            binding.ivEditDone.setVisibility(View.GONE);
            controlSpinnerSelection();


            binding.etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDatePicker();
                }
            });

            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            controlGenderSelection();
            binding.btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkData()) {
                        model.insertUser(getUser());
                        editor.putBoolean("profile",true);
                        editor.apply();
                        onBackPressed();
                    }else {
                        Snackbar.make(binding.getRoot(),"please requried data",Snackbar.LENGTH_LONG).show();
                    }
                }

            });
        }
    }


    private void controlGenderSelection(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                gender = i == R.id.male ? "Male":"Female";
            }
        });
    }
    private void controlSpinnerSelection(){
       binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               spinnerName = parent.getItemAtPosition(position).toString();
               Log.d("TAG", "onItemSelected: "+spinnerName);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
    private User getUser(){
        String userName = binding.etUserName.getText().toString();
        String email = binding.etEmail.getText().toString();
        User user = new User(userName,email,calendar.getTime(),gender,spinnerName);
        return user;
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
                yearCheck = year;
                monthCheck = monthOfYear;
                dayCheck = dayOfMonth;

            }
            },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show(getSupportFragmentManager(),"");
    }

    private void fillUserData(User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        String date = simpleDateFormat.format(user.getDate());
        bindingProfile.showUserName.setText(user.getUserName());
        bindingProfile.showEmail.setText(user.getEmail());
        bindingProfile.showDate.setText(date);
        bindingProfile.showGender.setText(user.getGender());
        bindingProfile.showCountry.setText(user.getCountry());
    }
    private boolean checkData(){
        if (!binding.etUserName.getText().toString().isEmpty()  &&
               !binding.etEmail.getText().toString().isEmpty()&&
                 !gender.isEmpty() &&
                  !binding.etDate.getText().toString().isEmpty()
                   ){
            return true;
        }
        else
            return false;
    }


}