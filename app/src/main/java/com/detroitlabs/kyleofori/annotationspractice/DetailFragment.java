package com.detroitlabs.kyleofori.annotationspractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.detroitlabs.kyleofori.annotationspractice.utils.SharedPreference;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kyleofori on 11/13/14.
 */

@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment {

    private static final String ARG_PLAYLIST = "arg_khan_academy_playlist";

    public static DetailFragment newInstance(Lesson lesson) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYLIST, lesson);

        DetailFragment detailFragment = new DetailFragment_();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    private SharedPreference sharedPreference = new SharedPreference();
    private Lesson lesson;

    @ViewById (R.id.txt_title_result)
    TextView txtTitle;

    @ViewById (R.id.txt_lessonUrl_result)
    TextView txtUrl;

    @ViewById (R.id.txt_description_result)
    TextView txtDescription;

    @ViewById (R.id.img_detail_star)
    ImageView imgFavoritesStar;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lesson = getArguments().getParcelable(ARG_PLAYLIST);
    }

    @AfterViews
    void setViewsWithLessonInfo() {
        if (lesson != null) {
            txtTitle.setText(lesson.getTitle());
            txtUrl.setText(lesson.getLessonUrl());
            txtDescription.setText(lesson.getDescription());
        } else {
            throw new IllegalStateException("Must supply a KhanAcademyPlaylist to DetailFragment");
        }
    }

    @AfterViews
    void setImageDrawable() {
        imgFavoritesStar.setImageDrawable(lesson.getStar(this.getActivity()));
    }


    @LongClick (R.id.img_detail_star)
    void onLongClick() {
        Activity activity = this.getActivity();
        if (lesson.isFavorited()) {
            sharedPreference.removeFavorite(activity, lesson);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.removed_from_favorites),
                    Toast.LENGTH_SHORT).show();
            lesson.setFavorited(false);
            imgFavoritesStar.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_none));

        } else {
            sharedPreference.addFavorite(activity, lesson);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.added_to_favorites),
                    Toast.LENGTH_SHORT).show();
            lesson.setFavorited(true);
            imgFavoritesStar.setImageDrawable(activity.getResources().getDrawable(R.drawable.favestar));

        }
    }

}
