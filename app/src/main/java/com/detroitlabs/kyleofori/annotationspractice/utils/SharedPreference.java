package com.detroitlabs.kyleofori.annotationspractice.utils;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;

        import com.detroitlabs.kyleofori.annotationspractice.Lesson;
        import com.google.gson.Gson;
/**
 * Created by kyleofori on 1/19/15.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Lesson_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
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

    public void addFavorite(Context context, Lesson lesson) {
        List<Lesson> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Lesson>();
        favorites.add(lesson);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Lesson lesson) {
        ArrayList<Lesson> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(lesson);
            saveFavorites(context, favorites);
        }
    }

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

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Lesson>(favorites);
        } else
            return null;

        return (ArrayList<Lesson>) favorites;
    }
}
