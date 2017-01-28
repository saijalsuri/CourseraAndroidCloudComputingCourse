package com.apps.saijestudio.assignment3;

import android.app.Activity;
import android.os.Bundle;

public class DownloadImageActivity extends Activity {

    /**
     * instance of Utils helper class
     */
    private Utils imageDownloadUtil = new Utils();

    /**
     * DownloadFragment TAG
     */
    private static final String TAG_DOWNLOAD_FRAGMENT = "download_fragment";

    /**
     * DownloadFragment Instantiation
     */
    private DownloadFragment downloadFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_filter);

        // Get the URL associated with the Intent data.
        final String webUrl = getIntent().getStringExtra("Uri");
        android.app.FragmentManager fm = getFragmentManager();
        downloadFragment = (DownloadFragment) fm.findFragmentByTag(TAG_DOWNLOAD_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (downloadFragment == null) {
            //imageDownloadUtil.showToast(getApplicationContext(), "Downloading Image...");
            downloadFragment = DownloadFragment.newInstance(webUrl);
            fm.beginTransaction().add(R.id.fragment_container, downloadFragment, TAG_DOWNLOAD_FRAGMENT).commit();
        }
        else {

            //else retain existing fragment
            fm.beginTransaction().replace(R.id.fragment_container, downloadFragment);
        }

    }
}