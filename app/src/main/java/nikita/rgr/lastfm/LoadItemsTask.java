package nikita.rgr.lastfm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmListAdapter.LastFmListAdapter;
import nikita.rgr.lastfm.LastFmObject.LastFmObject;


/**
 * Created by Nikita on 25.05.14.
 */
public class LoadItemsTask extends AsyncTask<Void,Void,List<LastFmObject>> {

    private LastFmApiResponseParser responseParser;
    private String requestUrl;
    private LastFmListAdapter adapter;

    public LoadItemsTask(Context context, LastFmListAdapter adapter, LastFmApiResponseParser parser, String url) {
        responseParser = parser;
        requestUrl = url;
        this.adapter = adapter;
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
    protected final void onPostExecute(List<LastFmObject> result) {
        // insert items to ent of the list

        super.onPostExecute(result);

        for (LastFmObject p : result){
            adapter.add(p);
        }

        adapter.notifyDataSetChanged();



    }

    protected final List<LastFmObject> doInBackground(Void... voids) {
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

        return responseParser.getResults();
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
