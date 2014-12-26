package com.detroitlabs.kyleofori.annotationspractice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyleofori on 11/26/14.
 */
public class KhanAcademyJSONParser {

    public static List<Lesson> parseJSONObject(JSONArray jsonArray) {

        try {
            List<Lesson> lessons = new ArrayList<>();

            for (int index = 0; index < jsonArray.length(); index++) {

                JSONObject playlistObject = jsonArray.getJSONObject(index);
                String title = playlistObject.optString("title", "unknown title");
                String kaUrl = playlistObject.optString("ka_url", "unknown url");
                String description = playlistObject.optString("description", "unknown description");
                String lessonId = playlistObject.optString("content_id", "unknown ID");

//                title = escapeHTML(title);
//                kaUrl = escapeHTML(kaUrl);
//                description = escapeHTML(description);
//                lessonId = escapeHTML(lessonId);

                Lesson currentLesson = new Lesson(title, kaUrl, description, false, lessonId);

                lessons.add(currentLesson);
            }

            return lessons;
        } catch (JSONException e) {
            return new ArrayList<>();
        }
    }

//    public static String escapeHTML(String s) {
//        StringBuilder sb = new StringBuilder();
//        int n = s.length();
//        for (int i = 0; i < n; i++) {
//            char c = s.charAt(i);
//            switch (c) {
//                case 'é':
//                    sb.append("\u00E9");
//                    break;
//                case 'É':
//                    sb.append("\u00C9;");
//                    break;
//                case '\'':
//                    sb.append("\u0027");
//                    break;
//                case '’':
//                    sb.append("\u2019");
//                    break;
//                default:
//                    sb.append(c);
//                    break;
//            }
//        }
//        return sb.toString();
//    }

}
