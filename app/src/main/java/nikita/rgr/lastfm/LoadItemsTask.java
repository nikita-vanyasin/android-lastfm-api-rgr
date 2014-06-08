package nikita.rgr.lastfm;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;


/**
 * Created by Nikita on 25.05.14.
 */
public class LoadItemsTask extends AsyncTask<Integer, Integer, Integer> {

    private LastFmApiResponseParser responseParser;
    private String requestUrl;

    public LoadItemsTask(LastFmApiResponseParser parser, String url) {
        responseParser = parser;
        requestUrl = url;
    }

    @Override
    protected final void onPreExecute() {
        // clearall
        // show preloader
        super.onPreExecute();
/*        progressDialog = new ProgressDialog(DataLoader.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
  */  }

    @Override
    protected final void onProgressUpdate(final Integer... progress) {
        // update progress ( or just do nothing)
    }

    @Override
    protected final void onPostExecute(final Integer result) {
        // insert items to ent of the list


    }

    protected final Integer doInBackground(final Integer... page) {
        // use repository pattern?? for cache system
        // HttpURLConnection urlConnection = getHttpURLConnection(url);
        // load and parse

        HttpURLConnection urlConnection = getHttpURLConnection();

        InputStream inputStream = getInputStreamFromConnection(urlConnection);

        try
        {
            responseParser.parse(inputStream);
        }
        finally {
            closeInputStream(inputStream);
            urlConnection.disconnect();
        }

        responseParser.getResults();

        return 0;
    }

    private void closeInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }

    private InputStream getInputStreamFromConnection(URLConnection urlConnection) {
        try {
            return urlConnection.getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException("", e);
        }
    }

    private HttpURLConnection getHttpURLConnection() {

        URL url;
        try
        {
            url = new URL(requestUrl);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("", e);
        }

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.connect();
        }
        catch (IOException e) {
            throw new RuntimeException("", e);
        }
        return urlConnection;
    }

}
