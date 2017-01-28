package vandy.mooc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * An Activity that downloads an image, stores it in a local file on
 * the local device, and returns a Uri to the image file.
 */
public class DownloadImageActivity extends Activity {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG = getClass().getSimpleName();
    //private Uri webUrl = null;
    private DownloadUtils imageDownloadUtil = new DownloadUtils();
    private Thread mThread = null;
    private Intent imageResult = new Intent();
    /**
     * Hook method called when a new instance of Activity is created.
     * One time initialization code goes here, e.g., UI layout and
     * some class scope variable initialization.
     *
     * @param savedInstanceState object that contains saved state information.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Always call super class for necessary
        // initialization/implementation.
        // @@ TODO -- you fill in here.
        super.onCreate(savedInstanceState);

        // Get the URL associated with the Intent data.
        // @@ TODO -- you fill in here.
        final Uri webUrl = Uri.parse(getIntent().getStringExtra("Uri"));


        // Download the image in the background, create an Intent that
        // contains the path to the image file, and set this as the
        // result of the Activity.


        // @@ TODO -- you fill in here using the Android "HaMeR"
        // concurrency framework.  Note that the finish() method
        // should be called in the UI thread, whereas the other
        // methods should be called in the background thread.  See
        // http://stackoverflow.com/questions/20412871/is-it-safe-to-finish-an-android-activity-from-a-background-thread
        // for more discussion about this topic.

        //Create runnable to be run in background thread
        Runnable downloadRunnable = new Runnable() {
            @Override
            public void run() {
                //get absolute path to downloaded Image using DownloadUtils method downloadImage()
                Uri absPath = imageDownloadUtil.downloadImage(getApplicationContext(), webUrl);

                //If downloadImage() returns an error setResult to RESULT_CANCEL
                if (absPath == null) {
                    setResult(Activity.RESULT_CANCELED, imageResult);

                    //Else set to RESULT_OK and relay the absolute path to the image file back to MainActivity
                } else {
                    imageResult.putExtra("absolute_path", absPath.toString());
                    setResult(Activity.RESULT_OK, imageResult);

                }

                //finish() activity in main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }

                });
            }
        };


        mThread = new

                Thread(downloadRunnable);

        mThread.start();



    }


}