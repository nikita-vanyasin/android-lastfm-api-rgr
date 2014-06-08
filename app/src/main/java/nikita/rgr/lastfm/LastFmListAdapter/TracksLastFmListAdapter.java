package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;

/**
 * Created by Nikita on 08.06.14.
 */
public class TracksLastFmListAdapter extends LastFmListAdapter {

    public TracksLastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, pageSize, footer);
    }

    @Override
    protected LastFmApiResponseParser createResponseParser() {
        return new GeoGetTopArtistsApiResponseParser();
    }
}
