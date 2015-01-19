package com.detroitlabs.kyleofori.annotationspractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by kyleofori on 11/13/14.
 */
public class DetailFragment extends Fragment implements View.OnLongClickListener {

    private static final String ARG_PLAYLIST = "arg_khan_academy_playlist";

    public static DetailFragment newInstance(Lesson lesson) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYLIST, lesson);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);

        return detailFragment;
    }

    private TextView txtTitle, txtKaUrl, txtDescription;
    private OldSharedPreference oldSharedPreference = new OldSharedPreference();
    private Lesson lesson;
    private ImageView imgFavoritesStar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        txtTitle = (TextView) view.findViewById(R.id.txt_title_result);
        txtKaUrl = (TextView) view.findViewById(R.id.txt_lessonUrl_result);
        txtDescription = (TextView) view.findViewById(R.id.txt_description_result);
        imgFavoritesStar = (ImageView) view.findViewById(R.id.img_detail_star);
        imgFavoritesStar.setOnLongClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lesson = getArguments().getParcelable(ARG_PLAYLIST);

        if (lesson != null) {

            txtTitle.setText(lesson.getTitle());
            txtKaUrl.setText(lesson.getLessonUrl());
            txtDescription.setText(lesson.getDescription());

        } else {
            throw new IllegalStateException("Must supply a KhanAcademyPlaylist to DetailFragment");
        }

        imgFavoritesStar.setImageDrawable(lesson.getStar(this.getActivity()));

    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.img_detail_star:
                Activity activity = this.getActivity();
                if (lesson.isFavorited()) {
                    Toast.makeText(activity,
                            activity.getResources().getString(R.string.removed_from_favorites),
                            Toast.LENGTH_SHORT).show();
                    lesson.setFavorited(false);
                    imgFavoritesStar.setImageDrawable(activity.getResources().getDrawable(R.drawable.star_none));

                } else {
                    Toast.makeText(activity,
                            activity.getResources().getString(R.string.added_to_favorites),
                            Toast.LENGTH_SHORT).show();
                    lesson.setFavorited(true);
                    imgFavoritesStar.setImageDrawable(activity.getResources().getDrawable(R.drawable.favestar));

                }
        }
        return false;
    }
}
