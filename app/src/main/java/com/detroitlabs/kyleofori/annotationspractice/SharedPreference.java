package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.detroitlabs.kyleofori.annotationspractice.Lesson;
import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kyleofori on 12/10/14.
 */

@EBean
public class SharedPreference {

    public static final String PREFS_NAME = "TEACHER_TOOLS_APP";
    public static final String FAVORITES = "list_of_lesson_plan_favorites_in_String_form";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.

    //Converts List<LessonModel> favorites to JSON string
    public void saveFavorites(Context context, List<Lesson> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    //Converts JSON string to List<LessonModel> favorites
    public ArrayList<Lesson> getFavorites(Context context) {
        SharedPreferences settings;
        List<Lesson> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Lesson[] favoriteItems = gson.fromJson(jsonFavorites,
                   Lesson[].class);

            if(favoriteItems != null) {
                favorites = Arrays.asList(favoriteItems);
                favorites = new ArrayList<Lesson>(favorites);
                return (ArrayList<Lesson>) favorites;

            }
        }
        return null;
    }

    public void addFavorite(Context context, Lesson lesson) {
        List<Lesson> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Lesson>();
        boolean isGoingIn = true;
        for (Lesson x: favorites) {
            if (x.getLessonId().equals(lesson.getLessonId())) {
                isGoingIn = false;
            }
        }
        if(isGoingIn) {
            favorites.add(lesson);
        }
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Lesson lesson) {
        List<Lesson> favorites = getFavorites(context);
        if (favorites != null) {
            for(Lesson x: favorites) {
                if (lesson.getLessonId().equals(x.getLessonId())) {
                    favorites.remove(x);
                }
            }
            saveFavorites(context, favorites);
        }
    }

    public void clearSharedPreferencesWhichClearsAdapterIHope(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Editor editor;
        editor = settings.edit();

        if(settings.contains(FAVORITES)) {
            editor.putString(FAVORITES, null);
            editor.commit();
        }
    }
}