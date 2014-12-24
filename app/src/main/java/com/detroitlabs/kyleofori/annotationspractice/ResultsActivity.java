package com.detroitlabs.kyleofori.annotationspractice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by kyleofori on 11/30/14.
 */
public class ResultsActivity extends Activity implements FragmentController, View.OnClickListener {

/*
    private FavoritesFragment favoritesFragment = new FavoritesFragment();
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Button btnSeeFavorites = (Button) findViewById(R.id.btn_see_favorites);
        btnSeeFavorites.setOnClickListener(this);

/*
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SearchResultsFragment())
                .commit();
*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_see_favorites:
/*
                if(!favoritesFragment.isAdded()) {
                    changeFragment(favoritesFragment, true);
                }
*/
                break;
        }
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
