package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import nikita.rgr.lastfm.GeoInfo;
import nikita.rgr.lastfm.LastFmApiRequest;
import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.Artist;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public class ArtistsLastFmListAdapter extends LastFmListAdapter {

    public ArtistsLastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, pageSize, footer);
    }

    @Override
    protected LastFmApiResponseParser createResponseParser() {
        return new GeoGetTopArtistsApiResponseParser();
    }

    @Override
    protected void setupRequestUrlBuilderSettings(LastFmApiRequest builder) {
        builder.setMethod(getContext().getString(R.string.artistsApiMethodName));

        builder.setAdditionalParam("country", GeoInfo.getInstance().getCurrentCountryName());
    }

    @Override
    protected View forgeItemView(View convertView, int position, ViewGroup parent) {
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_list_row, parent, false);

            TextView name = (TextView) convertView.findViewById(R.id.artistName);
            TextView count = (TextView) convertView.findViewById(R.id.trackListenersCount);
            SmartImageView imageView = (SmartImageView) convertView.findViewById(R.id.artist_image);
            convertView.setTag(new Holder(name, count, imageView));
        }

        final Artist artist = (Artist) getItem(position);

        Holder h = (Holder) convertView.getTag();

        h.Name.setText(artist.Name);
        h.ListenersCount.setText(artist.ListenersCount.toString());
        h.ImageView.setImageUrl(artist.SmallImageUrl);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(artist.ArtistUrl));
                getContext().startActivity(browserIntent);
            }
        });

        return convertView;
    }

    private static class Holder {
        public final TextView Name;
        public final TextView ListenersCount;
        public final SmartImageView ImageView;

        private Holder(TextView name, TextView count, SmartImageView imageView) {
            this.Name = name;
            this.ListenersCount = count;
            this.ImageView = imageView;
        }
    }
}
