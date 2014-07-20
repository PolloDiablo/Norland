package norland.game.main.stats.export;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import norland.game.main.stats.StatsBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Used for sending up logged stats bundles.
 * 
 * @author Tony
 */
public final class ExportHandler {

    /**
     * For sending uploads.
     */
    private static final String URL = "http://192.168.0.18:8888/norlandserver";

    /**
     * If true, then always attempt upload no matter what connection the phone
     * is on.
     */
    private final boolean forceUpload;

    /**
     * The context to use.
     */
    private final Context context;

    /**
     * @param context
     *            used for determining connectivity state of device.
     * @param forceUpload
     *            if true, will send out over mobile or wifi.
     */
    public ExportHandler(final Context context, final boolean forceUpload) {
        this.context = context;
        this.forceUpload = forceUpload;
    }

    /**
     * Attempt to upload the stats bundle.
     * 
     * @param statsBundle
     * @return
     */
    public boolean attemptUpload(final StatsBundle statsBundle) {
        if (!isConnectivityValid()) {
            Log.w("Export Handler", "Upload not attempted, because of invalid connection state.");
            return false;
        }
        final String jsonBundle = convertForExport(statsBundle);
        Log.d("Export Handler", "JSON: " + jsonBundle);
        AsyncTask<StatsBundle, Void, Boolean> uploadAsync = new AsyncTask<StatsBundle, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(StatsBundle... params) {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(URL);
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("id", "norland"));
                nvps.add(new BasicNameValuePair("token", "lolwhat?!"));
                nvps.add(new BasicNameValuePair("data", jsonBundle));
                return hitIt(client, post, nvps);
            }

            /**
             * Actually execute the http post request on the http client
             * provided. If anything but 200 is returned from the server, then
             * it returns false.
             * 
             * @param httpClient
             * @param httpPost
             * @param nameValuePairs
             * @return
             */
            private boolean hitIt(HttpClient httpClient, HttpPost httpPost,
                    List<? extends NameValuePair> nameValuePairs) {
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity responseEntity = response.getEntity();

                    if (responseEntity != null) {
                        final int statusCode = response.getStatusLine().getStatusCode();
                        if (statusCode == 200) {
                            // int length = (int)
                            // responseEntity.getContentLength();
                            InputStream is = responseEntity.getContent();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            String line;
                            StringBuilder sb = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                                sb.append("\n");
                            }
                            Log.d("Export Handler Result:", sb.toString());
                            return true;
                        } else {
                            Log.e("Export Handler Result:", "Status Code = " + statusCode + "!");
                        }
                    }
                } catch (IOException ioe) {
                }
                return false;
            }

        };
        uploadAsync.execute(statsBundle);
        try {
            return uploadAsync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 1st checks for any connectivity at all. Then, it checks if the force
     * upload is set, if not set then it only returns true if on wifi.
     * 
     * @return true if it makes sense to upload right now.
     */
    private boolean isConnectivityValid() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo().isConnected()) {
            int type = cm.getActiveNetworkInfo().getType();
            return ((forceUpload) || (type == ConnectivityManager.TYPE_WIFI));
        } else {
            return false;
        }
    }

    /**
     * Use GSON to convert to export json.
     * 
     * @param statsBundle
     *            to convert.
     * @return json string.
     */
    private String convertForExport(StatsBundle statsBundle) {
        return new Gson().toJson(statsBundle);
    }
}
