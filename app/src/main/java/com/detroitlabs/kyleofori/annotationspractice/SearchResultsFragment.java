package com.detroitlabs.kyleofori.annotationspractice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 11/13/14.
 */

@EFragment (R.layout.fragment_search_results)
public class SearchResultsFragment extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private FragmentController fragmentController;
    private SearchResultsAdapter searchResultsAdapter;
//    private ParseDataset parseDataset = new ParseDataset();
//    private List<ParseObject> parseObjects = new ArrayList<>();
    private List<Lesson> lessons = new ArrayList<>();
    private List<Lesson> visibleLessons = new ArrayList<>();

//    private ParseObjectParser parseObjectParser;
    private EditText edtInputSearch;
    private int startingPosition;
    private int stopPosition;

    private static final int LOAD_NUMBER = 10;
    private static final List<Lesson> khanAcademyLessonModels = MainActivity_.khanAcademyLessons;

    @Bean
    SharedPreference sharedPreference;
    private List<Lesson> favorites;

    Activity activity;

    @ViewById (R.id.list_search_results)
    protected ListView listView;

    @AfterViews
    void setAdapterOnListView() {
        searchResultsAdapter = new SearchResultsAdapter(getActivity(), visibleLessons);
        listView.setAdapter(searchResultsAdapter);


        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    if (stopPosition < khanAcademyLessonModels.size()) {
                        Log.i(this.getClass().getSimpleName(), "stop position " + String.valueOf(stopPosition));
                        startingPosition = stopPosition;
                        if ((stopPosition + LOAD_NUMBER) > khanAcademyLessonModels.size()) {
                            stopPosition = stopPosition + (khanAcademyLessonModels.size() - stopPosition);
                        } else {
                            stopPosition = stopPosition + LOAD_NUMBER;
                        }

                        for (int i = startingPosition; i < stopPosition; i++) {
                            searchResultsAdapter.add(khanAcademyLessonModels.get(i));
                        }
                        Log.i(this.getClass().getSimpleName(), "just added " + String.valueOf(startingPosition) + "thru " + String.valueOf(stopPosition));


                        searchResultsAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof FragmentController) {
            fragmentController = (FragmentController) activity;
        } else {
            throw new IllegalArgumentException(getString(R.string.fragment_controller_interface_error));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

        if (favorites == null) {
            showAlert(getResources().getString(R.string.no_favorites_items),
                    getResources().getString(R.string.no_favorites_msg));
        } else {

            if (favorites.size() == 0) {
                showAlert(
                        getResources().getString(R.string.no_favorites_items),
                        getResources().getString(R.string.no_favorites_msg));
            }

        }


        startingPosition = 0;
        stopPosition = 10;

        visibleLessons.clear();
        for(int i=startingPosition; i<stopPosition; i++) {
            visibleLessons.add(khanAcademyLessonModels.get(i));

            Lesson currentLesson = visibleLessons.get(i);
            String currentLessonId = currentLesson.getLessonId();

            if(favorites != null) {
                for (Lesson x : favorites) {
                    if (currentLessonId.equals(x.getLessonId())) {
                        currentLesson.setFavorited(true);
                    }
                }
            }
        }

        Log.i(this.getClass().getSimpleName(), "See some id " + visibleLessons.get(0).getLessonId());
        searchResultsAdapter = new SearchResultsAdapter(getActivity(), visibleLessons);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //retrieveParseObjectsFromCloud();

        edtInputSearch = (EditText) view.findViewById(R.id.edt_input_search);
        edtInputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                searchResultsAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.list_search_results:
                /*Lesson lesson = (Lesson) adapterView.getAdapter().getItem(i);
                DetailFragment detailFragment = DetailFragment.newInstance(lesson);
                fragmentController.changeFragment(detailFragment, true);*/
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {

        List<Lesson> filteredLessons = searchResultsAdapter.filteredLessons;

        if (filteredLessons.get(position).isFavorited()) {
//            sharedPreference.removeFavorite(activity, filteredLessons.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.removed_from_favorites),
                    Toast.LENGTH_SHORT).show();
            filteredLessons.get(position).setFavorited(false);
            searchResultsAdapter.notifyDataSetChanged();
        } else {
//            sharedPreference.addFavorite(activity, filteredLessons.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.added_to_favorites),
                    Toast.LENGTH_SHORT).show();
            filteredLessons.get(position).setFavorited(true);
            searchResultsAdapter.notifyDataSetChanged();
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.favorites);
//        getActivity().getActionBar().setTitle(R.string.favorites);
    }



    public void showAlert(String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(activity)
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getFragmentManager().popBackStackImmediate();
                        }
                    });
            alertDialog.show();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void notifyUser() {
        Notification.Builder builder =
                new Notification.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("New lesson plans added")
                        .setContentText("x more n-subject lesson plans are available in TeacherTools.");
        Intent resultIntent = new Intent(getActivity(), ResultsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(ResultsActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(getId(), builder.build());
    }
}
