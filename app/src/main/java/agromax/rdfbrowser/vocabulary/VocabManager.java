package agromax.rdfbrowser.vocabulary;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import agromax.rdfbrowser.R;
import agromax.rdfbrowser.util.Downloader;
import agromax.rdfbrowser.util.SimpleAlertDialog;

/**
 * Created by Dell on 11-06-2016.
 */
public class VocabManager {

    private final Context context;

    public VocabManager(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Tries to load the vocab file from the FileSystem and returns the FileInputStream object
     * if the file is present, otherwise returns null
     */
    private FileInputStream tryLoad() {
        try {
            return context.openFileInput(context.getString(R.string.vocabulary_file_name));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vocabulary getVocabulary() {
        FileInputStream vocabStream = tryLoad();
        if (vocabStream != null) {
            return new Vocabulary(context, vocabStream);
        }
        return null;
    }

    public static void downloadVocabulary(@NonNull final Context context, final String url) {
        Activity a = null;
        try {
            a = (Activity) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
            a = null;
        }

        final Activity activity = a;

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                try {
                    new URI(url);
                    return Downloader.download(url);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return Downloader.download(context.getString(R.string.vocabulary_download_url));
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s != null) {
                    try {
                        FileOutputStream fout = context.openFileOutput(context.getString(R.string.vocabulary_file_name), 0);
                        fout.write(s.getBytes());
                        fout.close();
                        if (activity != null) {
                            SimpleAlertDialog.launchAlertBox(
                                    context.getString(R.string.vocabulary_download_complete_msg),
                                    activity.getFragmentManager()
                            );
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (activity != null) {
                        SimpleAlertDialog.launchAlertBox(
                                context.getString(R.string.vocabulary_download_failed),
                                activity.getFragmentManager()
                        );
                    }
                }
            }
        }.execute();
    }
}
