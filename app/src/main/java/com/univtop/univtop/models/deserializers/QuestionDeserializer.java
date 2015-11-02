package com.univtop.univtop.models.deserializers;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.univtop.univtop.models.Question;

import java.lang.reflect.Type;

/**
 * Created by dungpham on 11/2/15.
 */
public class QuestionDeserializer implements JsonDeserializer<Question> {
    @Override
    public Question deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) {
        try {
            return new Gson().fromJson(json, Question.class);
        } catch (Exception e) {
            Crashlytics.logException(e);
            return null;
        }
    }
}
