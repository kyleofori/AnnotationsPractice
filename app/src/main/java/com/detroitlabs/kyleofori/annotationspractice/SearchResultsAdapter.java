package com.detroitlabs.kyleofori.annotationspractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.detroitlabs.kyleofori.annotationspractice.Lesson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 11/13/14.
 */
public class SearchResultsAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Lesson> lessons = new ArrayList<>();
    public List<Lesson> filteredLessons = new ArrayList<>();


    public SearchResultsAdapter(Context context, List<Lesson> lessons) {
        super();
        this.context = context;
        this.lessons = lessons;
        filteredLessons = this.lessons;
    }

    public void clear() {
        lessons.clear();
        filteredLessons.clear();
    }

    @Override
    public int getCount() {
        return filteredLessons.size();
    }

    @Override
    public Lesson getItem(int i) {
        return filteredLessons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_lesson_plan, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Lesson lesson = getItem(position);

        viewHolder.txtTitleFav.setText(lesson.getTitle());
        viewHolder.txtLessonUrlFav.setText(lesson.getLessonUrl());
        viewHolder.txtDescriptionFav.setText(lesson.getDescription());
        viewHolder.imgStarFav.setImageDrawable(lesson.getStar(context));

        return convertView;
    }

    public void add(Lesson lesson) {
        lessons.add(lesson);
        notifyDataSetChanged();
    }

    private static class ViewHolder {

        private TextView txtTitleFav;
        private TextView txtLessonUrlFav;
        private TextView txtDescriptionFav;
        private ImageView imgStarFav;

        public ViewHolder(View rootView) {
            this.txtTitleFav = (TextView) rootView.findViewById(R.id.txt_title_result);
            this.txtLessonUrlFav = (TextView) rootView.findViewById(R.id.txt_lessonUrl_result);
            this.txtDescriptionFav = (TextView) rootView.findViewById(R.id.txt_description_result);
            this.imgStarFav = (ImageView) rootView.findViewById(R.id.img_star_result);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    results.values = lessons;
                    results.count = lessons.size();
                } else {
                    ArrayList<Lesson> filteredList = new ArrayList<Lesson>();

                    for (Lesson lesson : lessons) {
                        if (lesson.getTitle() != null && lesson.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filteredList.add(lesson);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filteredLessons = (ArrayList<Lesson>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
