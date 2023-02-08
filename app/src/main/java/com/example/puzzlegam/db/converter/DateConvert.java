package com.example.puzzlegam.db.converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConvert {


    @TypeConverter
    public  Long fromDate(Date date){
        if (date!=null){
            return date.getTime();
        }
        return null;
    }

    @TypeConverter
    public  Date fromLong(Long millesSecond){
        if (millesSecond!=null){
            return new Date(millesSecond);
        }
        return null;
    }
}
