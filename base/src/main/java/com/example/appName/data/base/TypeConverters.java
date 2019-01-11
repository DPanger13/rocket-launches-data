package com.example.appName.data.base;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class TypeConverters {

    @TypeConverter
    @NonNull
    public static String fromTimestampList(List<Long> timestamps) {
        StringBuilder builder = new StringBuilder();

        if (timestamps != null) {
            for (Long timestamp : timestamps) {
                if (timestamp != null) {
                    if (builder.length() > 0) builder.append(',');
                    builder.append(timestamp);
                }
            }
        }

        return builder.toString();
    }

}
