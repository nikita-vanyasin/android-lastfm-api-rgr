package nikita.rgr.lastfm;

import android.os.AsyncTask;

import java.util.ArrayList;
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

        for (LastFmObject p : result) {
            adapter.add(p);
        }

        adapter.notifyDataSetChanged();
    }

    protected final List<LastFmObject> doInBackground(Void... voids) {
       /* try
        {*/
        return LastFmObjectsRepository.getInstance().getObjects(request, responseParser);
     /*   }
        catch (RuntimeException e)
        {
            // show error to user!
            return new ArrayList<>();
        }*/
    }
}
