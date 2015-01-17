package com.detroitlabs.kyleofori.annotationspractice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.detroitlabs.kyleofori.annotationspractice.FragmentController;
import com.detroitlabs.kyleofori.annotationspractice.R;
import com.detroitlabs.kyleofori.annotationspractice.SearchResultsFragment;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by kyleofori on 11/30/14.
 */
@EActivity(R.layout.activity_results)
public class ResultsActivity extends Activity implements FragmentController {

/*
    private FavoritesFragment favoritesFragment = new FavoritesFragment();
*/

    @ViewById(R.id.btn_see_favorites)
    Button btnSeeFavorites;

    @Click (R.id.btn_see_favorites)
    void goToFavorites() {
        /*
                if(!favoritesFragment.isAdded()) {
                    changeFragment(favoritesFragment, true);
                }
*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchResultsFragment_())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
