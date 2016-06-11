package agromax.rdfbrowser.util;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dell on 11-06-2016.
 */
public class Downloader {
    private static final OkHttpClient httpClient = new OkHttpClient();

    private Downloader() {
    }

    public static String download(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            return new String(readIt(response), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readIt(Response response) throws IOException {
        return response.body().bytes();
    }
}
