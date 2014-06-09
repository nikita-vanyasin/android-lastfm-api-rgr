package nikita.rgr.lastfm;

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
public class LoadItemsTask extends AsyncTask<Void, Void, List<LastFmObject>> {

    private LastFmApiResponseParser responseParser;
    private LastFmApiRequest request;
    private LastFmListAdapter adapter;

    public LoadItemsTask(LastFmListAdapter adapter, LastFmApiResponseParser parser, LastFmApiRequest request) {
        this.responseParser = parser;
        this.request = request;
        this.adapter = adapter;
    }

    @Override
    protected final void onPostExecute(List<LastFmObject> result) {
        super.onPostExecute(result);

        adapter.addAll(result);

        adapter.notifyDataSetChanged();

        //  adapter.setTotalPages(responseParser.getTotalPagesCount());
    }

    protected final List<LastFmObject> doInBackground(Void... voids) {
        return LastFmObjectsRepository.getInstance().getObjects(request, responseParser);
    }
}
