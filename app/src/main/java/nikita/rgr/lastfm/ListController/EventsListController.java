package nikita.rgr.lastfm.ListController;

import android.widget.ListView;

import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
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
    protected LastFmApiResponseParser createResponseParser() {
        return new GeoGetTopArtistsApiResponseParser();
    }


}
