package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetEventsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public class EventsLastFmListAdapter extends LastFmListAdapter {

    public EventsLastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, pageSize, footer);
    }


    @Override
    protected LastFmApiResponseParser createResponseParser() {
        return new GeoGetEventsApiResponseParser();
    }

    @Override
    protected void setupRequestUrlBuilderSettings(LastFmApiRequestUrlBuilder builder) {
        builder.setMethod(getContext().getString(R.string.eventsApiMethodName));
    }

    @Override
    protected View forgeItemView(View convertView, int position, ViewGroup parent) {

        return convertView;
    }
}
