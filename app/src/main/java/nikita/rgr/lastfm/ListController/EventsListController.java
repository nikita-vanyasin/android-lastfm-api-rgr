package nikita.rgr.lastfm.ListController;

import android.widget.ListView;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmListAdapter.EventsLastFmListAdapter;
import nikita.rgr.lastfm.LastFmListAdapter.LastFmListAdapter;
import nikita.rgr.lastfm.ListController.ListController;
import nikita.rgr.lastfm.MainActivity;

/**
 * Created by Nikita on 24.05.14.
 */
public class EventsListController extends ListController {

    public EventsListController(MainActivity activity, ListView listVew) {
        super(activity, listVew);
    }


    @Override
    protected LastFmListAdapter createListAdapter(MainActivity activity, TextView footer) {
        return new EventsLastFmListAdapter(activity, 20, footer);
    }
}
