package com.example.vegantranslations.data.converters;

import androidx.room.TypeConverter;

import com.example.vegantranslations.data.model.db.Purpose;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class PurposeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Purpose> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Purpose>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Purpose> someObjects) {
        return gson.toJson(someObjects);
    }
}
