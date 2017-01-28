package com.apps.saijestudio.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DownloadFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_URL = "param1";

    /**
     * web URL parameter
     */
    private String webUrl;

    /**
     * instance of Utils helper class
     */
    private Utils imageDownloadUtil = new Utils();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DownloadFragment.
     */
    public static DownloadFragment newInstance(String param1) {
        DownloadFragment fragment = new DownloadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DownloadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        if (getArguments() != null) {
            webUrl = getArguments().getString(ARG_URL);
        }

        //create and execute AsyncTask to download image
        DownloaderTask downloaderTask = new DownloaderTask();
        downloaderTask.execute(Uri.parse(webUrl));

    }

    public class DownloaderTask extends AsyncTask<Uri, Integer, Uri> {

        @Override
        protected Uri doInBackground(Uri... webUrl) {

            //get absolute path to downloaded Image using DownloadUtils method downloadImage()
            Uri absPath = imageDownloadUtil.downloadImage(getActivity().getApplicationContext(), webUrl[0]);
            return absPath;
        }

        @Override
        protected void onPostExecute(Uri absPath) {
            Intent imageResult = new Intent();

            //if Utils.downloadImage returns null absPath then set RESULT_CANCELED due to downloading error
            if (absPath == null) {
                getActivity().setResult(Activity.RESULT_CANCELED, imageResult);

            //Else set to RESULT_OK and relay the absolute path to the image file back to MainActivity
            } else {
                imageResult.putExtra("absolute_path", absPath.toString());
                getActivity().setResult(Activity.RESULT_OK, imageResult);
            }

            //Finish DownloadImageActivity
            getActivity().finish();
        }
    }
}


