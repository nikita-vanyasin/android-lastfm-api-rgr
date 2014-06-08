package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopTracksApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.Track;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public class TracksLastFmListAdapter extends LastFmListAdapter {

    public TracksLastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, pageSize, footer);
    }

    @Override
    protected LastFmApiResponseParser createResponseParser() {
        return new GeoGetTopTracksApiResponseParser();
    }

    @Override
    protected void setupRequestUrlBuilderSettings(LastFmApiRequestUrlBuilder builder) {
        builder.setMethod(getContext().getString(R.string.tracksApiMethodName));
    }

    @Override
    protected View forgeItemView(View convertView, int position, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_list_row, parent, false);

            TextView name = (TextView) convertView.findViewById(R.id.trackName);
            TextView artistName = (TextView) convertView.findViewById(R.id.trackArtistName);
            TextView count = (TextView) convertView.findViewById(R.id.trackListenersCount);
            SmartImageView imageView = (SmartImageView) convertView.findViewById(R.id.track_image);

            convertView.setTag(new Holder(name, artistName, count, imageView));
        }

        Track track = (Track)getItem(position);
        Holder h = (Holder) convertView.getTag();

        h.Name.setText(track.Name);
        h.TrackArtistName.setText(track.ArtistName);
        h.ListenersCount.setText(track.ListenersCount);
        h.ImageView.setImageUrl(track.SmallImageUrl);

        return convertView;
    }

    private static class Holder{
        public final TextView Name;
        public final TextView TrackArtistName;
        public final TextView ListenersCount;
        public final SmartImageView ImageView;

        private Holder(TextView name, TextView artistName, TextView count, SmartImageView imageView) {
            this.Name = name;
            this.TrackArtistName = artistName;
            this.ListenersCount = count;
            this.ImageView = imageView;
        }
    }
}
