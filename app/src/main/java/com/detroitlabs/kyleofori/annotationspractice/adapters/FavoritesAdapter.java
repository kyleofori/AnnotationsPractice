package com.detroitlabs.kyleofori.annotationspractice.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.kyleofori.annotationspractice.Lesson;
import com.detroitlabs.kyleofori.annotationspractice.R;
import com.detroitlabs.kyleofori.annotationspractice.utils.SharedPreference;

/**
 * Created by kyleofori on 1/19/15.
 */
public class FavoritesAdapter extends ArrayAdapter<Lesson> {

        private Context context;
        List<Lesson> favorites;
        SharedPreference sharedPreference;

        public FavoritesAdapter(Context context, List<Lesson> favorites) {
            super(context, R.layout.list_item_lesson_plan, favorites);
            this.context = context;
            this.favorites = favorites;
            sharedPreference = new SharedPreference();
        }

        private class ViewHolder {
            TextView lessonTitleTxt;
            TextView lessonDescTxt;
            TextView lessonUrlTxt;
            ImageView favoriteImg;
        }

        @Override
        public int getCount() {
            return favorites.size();
        }

        @Override
        public Lesson getItem(int position) {
            return favorites.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_lesson_plan, null);
                holder = new ViewHolder();
                holder.lessonTitleTxt = (TextView) convertView
                        .findViewById(R.id.txt_title_result);
                holder.lessonDescTxt = (TextView) convertView
                        .findViewById(R.id.txt_description_result);
                holder.lessonUrlTxt = (TextView) convertView
                        .findViewById(R.id.txt_lessonUrl_result);
                holder.favoriteImg = (ImageView) convertView
                        .findViewById(R.id.img_star_result);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Lesson lesson = (Lesson) getItem(position);
            holder.lessonTitleTxt.setText(lesson.getTitle());
            holder.lessonDescTxt.setText(lesson.getDescription());
            holder.lessonUrlTxt.setText(lesson.getLessonUrl() + "");
		
		/*If a lesson exists in shared preferences then set heart_red drawable
		 * and set a tag*/
            if (checkFavoriteItem(lesson)) {
                holder.favoriteImg.setImageResource(R.drawable.favestar);
                holder.favoriteImg.setTag("red");
            } else {
                holder.favoriteImg.setImageResource(R.drawable.star_none);
                holder.favoriteImg.setTag("grey");
            }

            return convertView;
        }

        /*Checks whether a particular lesson exists in SharedPreferences*/
        public boolean checkFavoriteItem(Lesson checkLesson) {
            boolean check = false;
            if (favorites != null) {
                for (Lesson lesson : favorites) {
                    if (lesson.equals(checkLesson)) {
                        check = true;
                        break;
                    }
                }
            }
            return check;
        }

        @Override
        public void add(Lesson lesson) {
            super.add(lesson);
            favorites.add(lesson);
            notifyDataSetChanged();
        }

        @Override
        public void remove(Lesson lesson) {
            super.remove(lesson);
            favorites.remove(lesson);
            notifyDataSetChanged();
        }
    }
