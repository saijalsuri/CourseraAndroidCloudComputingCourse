package com.apps.saijestudio.assignment3;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.EditText;



public class MainActivity extends LifecycleLoggingActivity {
    /**
     * A value that uniquely identifies the request to download an
     * image.
     */
    private static final int DOWNLOAD_IMAGE_REQUEST = 1;

    /**
     * EditText field for entering the desired URL to an image.
     */
    private EditText mUrlEditText;

    /**
     * URL for the image that's downloaded by default if the user
     * doesn't specify otherwise.
     */
    private Uri mDefaultUrl =
            Uri.parse("http://www.dre.vanderbilt.edu/~schmidt/robot.png");


    /**
     * intent to launch DownloadImageActivity
     */
    private Intent downloadImageIntent = null;

    /**
     * instance of Utils helper class
     */
    private Utils imageFilterUtil = new Utils();

    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Cache the EditText that holds the urls entered by the user
        // (if any).
        // @@ TODO -- you fill in here.
        mUrlEditText = (EditText) findViewById(R.id.url);


    }

    /**
     * Called by the Android Activity framework when the user clicks
     * the "Find Address" button.
     *
     * @param view The view.
     */
    public void downloadImage(View view) {
        try {
            // Hide the keyboard.
            hideKeyboard(this,
                    mUrlEditText.getWindowToken());

            // Call the makeDownloadImageIntent() factory method to
            // create a new Intent to an Activity that can download an
            // image from the URL given by the user.  In this case
            // it's an Intent that's implemented by the
            // DownloadImageActivity.
            Uri userUri = getUrl();
            //call makeDownloadImageIntent only if User text is a valid Url
            if (getUrl() != null) {
                downloadImageIntent = makeDownloadImageIntent(userUri);
                // Start the Activity associated with the Intent
                startActivityForResult(downloadImageIntent, DOWNLOAD_IMAGE_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Hook method called back by the Android Activity framework when
     * an Activity that's been launched exits, giving the requestCode
     * it was started with, the resultCode it returned, and any
     * additional data from it.
     */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        if (resultCode == RESULT_OK) {
           if (requestCode == DOWNLOAD_IMAGE_REQUEST) {

                //retrieve absolute path to downloaded image from intent data
                String absPath = data.getStringExtra("absolute_path");

               // Call the makeFilterImageIntent() factory method to
               // create an Intent that will launch the FilterImageActivity
               Intent filterImageIntent = makeFilterImageIntent(Uri.parse(absPath));
               startActivity(filterImageIntent);
           }
        }
        // Check if the started Activity did not complete successfully
        // and inform the user a problem occurred when trying to
        // download contents at the given URL.
        else if (resultCode == RESULT_CANCELED) {
            imageFilterUtil.showToast(getApplicationContext(), "Could Not Download Image!");
        }
    }

    /**
     * Factory method that returns an explicit Intent for downloading
     * an image.
     */
    private Intent makeDownloadImageIntent(Uri url) {
        // Create an intent that will download the image from the web.
        Intent downloadImage = new Intent(MainActivity.this, DownloadImageActivity.class);
        downloadImage.putExtra("Uri", url.toString());
        return downloadImage;
    }

    /**
     * Factory method that returns an explicit Intent for filtering
     * an image.
     */
    private Intent makeFilterImageIntent(Uri url) {
        // Create an intent that will filter the downloaded image
        Intent downloadImage = new Intent(MainActivity.this, FilterImageActivity.class);
        downloadImage.putExtra("Uri", url.toString());
        return downloadImage;
    }

    /**
     * Get the URL to download based on user input.
     */
    protected Uri getUrl() {
        Uri url = null;

        // Get the text the user typed in the edit text (if anything).
        url = Uri.parse(mUrlEditText.getText().toString());
        // Do a sanity check to ensure the URL is valid, popping up a
        // toast if the URL is invalid.

        String uri = url.toString();
        // If the user didn't provide a URL then use the default.
        if (uri == null || uri.equals("")) {
            url = mDefaultUrl;
            return url;
        } else {
            if (!URLUtil.isValidUrl(url.toString())) {
                Utils.showToast(getApplicationContext(), "Invalid URL");
                return null;
            }
            return url;
        }
    }

    /**
     * This method is used to hide a keyboard after a user has
     * finished typing the url.
     */
    public void hideKeyboard(Activity activity,
                             IBinder windowToken) {
        InputMethodManager mgr =
                (InputMethodManager) activity.getSystemService
                        (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken,
                0);
    }


}