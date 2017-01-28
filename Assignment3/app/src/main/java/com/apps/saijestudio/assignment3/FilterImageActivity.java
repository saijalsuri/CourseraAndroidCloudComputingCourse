package com.apps.saijestudio.assignment3;

import android.app.Activity;
import android.os.Bundle;


public class FilterImageActivity extends Activity {

    /**
     * instance of Utils helper class
     */
    private Utils imageFilterUtil = new Utils();

    /**
     * FilterFragment TAG
     */
    private static final String TAG_FILTER_FRAGMENT = "filter_fragment";

    /**
     * FilterFragment Instantiation
     */
    private FilterFragment filterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_filter);

        // Get the URL associated with the Intent data.
        final String absPath = getIntent().getStringExtra("Uri");
        android.app.FragmentManager fm = getFragmentManager();
        filterFragment = (FilterFragment) fm.findFragmentByTag(TAG_FILTER_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (filterFragment == null) {
            //imageFilterUtil.showToast(getApplicationContext(), "Filtering Image...");
            filterFragment = FilterFragment.newInstance(absPath);
            fm.beginTransaction().add(R.id.fragment_container, filterFragment, TAG_FILTER_FRAGMENT).commit();
        }
        else {

            //else retain existing fragment
            fm.beginTransaction().replace(R.id.fragment_container, filterFragment);
        }
    }


}
