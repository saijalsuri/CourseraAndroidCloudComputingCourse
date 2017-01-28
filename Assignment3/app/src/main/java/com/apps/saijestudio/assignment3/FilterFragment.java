package com.apps.saijestudio.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FilterFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGE_PATH = "param1";

    /**
     * image URL parameter
     */
    private String imagePath;

    /**
     * instance of Utils helper class
     */
    private Utils imageFilterUtil = new Utils();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DownloadFragment.
     */
    public static FilterFragment newInstance(String param1) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_PATH, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        if (getArguments() != null) {
            imagePath = getArguments().getString(ARG_IMAGE_PATH);
        }

        //create and execute AsyncTask to filter image
        FilterTask filterTask = new FilterTask();
        filterTask.execute(Uri.parse(imagePath));

    }

    public class FilterTask extends AsyncTask<Uri, Void, Uri> {

        @Override
        protected Uri doInBackground(Uri... pathToImage) {
            //get absolute path to new filtered gray scale Image using DownloadUtils method grayScaleFilter()
            Uri pathFilteredImage = imageFilterUtil.grayScaleFilter(getActivity().getApplicationContext(), pathToImage[0]);
            return pathFilteredImage;
        }

        /**
         * Factory method that returns an implicit Intent for viewing the
         * downloaded image in the Gallery app.
         */
        private Intent makeGalleryIntent(String pathToImageFile) {
            // Create an intent that will start the Gallery app to view
            // the image.
            // TODO -- you fill in here, replacing "null" with the proper
            // code.
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_VIEW);
            galleryIntent.setDataAndType(Uri.parse("file://" + pathToImageFile), "image/*");

            return galleryIntent;
        }

        @Override
        protected void onPostExecute(Uri pathFilteredImage) {
            //If grayScaleFilter returns an error show Error Toast
            if (pathFilteredImage == null) {
                imageFilterUtil.showToast(getActivity().getApplicationContext(), "Could not Filter Image");

            //Else launch gallery intent
            } else {
                Intent galleryIntent = makeGalleryIntent(pathFilteredImage.toString());
                // Start the Gallery Activity.
                startActivity(galleryIntent);
            }

            //finish FilterImageActivity
            getActivity().finish();
        }
    }
}