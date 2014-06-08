package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import nikita.rgr.lastfm.BundleKey;
import nikita.rgr.lastfm.GeoInfo;
import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetEventsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.Event;
import nikita.rgr.lastfm.LastFmObject.Track;
import nikita.rgr.lastfm.R;
import nikita.rgr.lastfm.ShowEventActivity;

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
        builder.setAdditionalParam("long", String.valueOf(GeoInfo.getInstance().getLongitude()));
        builder.setAdditionalParam("lat", String.valueOf(GeoInfo.getInstance().getLatitude()));
        builder.setAdditionalParam("country", String.valueOf(GeoInfo.getInstance().getCurrentCountryName()));
    }

    @Override
    protected View forgeItemView(View convertView, int position, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_row, parent, false);

            TextView title = (TextView) convertView.findViewById(R.id.eventTitle);
            TextView locationName = (TextView) convertView.findViewById(R.id.eventLocationName);
            TextView dateTime = (TextView) convertView.findViewById(R.id.eventDateTime);
            TextView city = (TextView) convertView.findViewById(R.id.eventCity);
            SmartImageView imageView = (SmartImageView) convertView.findViewById(R.id.event_image);

            convertView.setTag(new Holder(title, locationName, dateTime, city, imageView));
        }

        final Event event = (Event)getItem(position);
        Holder h = (Holder) convertView.getTag();

        h.Title.setText(event.Title);
        h.LocationName.setText(event.EventLocationName);
        h.DateTime.setText(event.StartDate + " " + event.StartTime);
        h.City.setText(event.City);
        h.ImageView.setImageUrl(event.SmallImageUrl);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showContent = new Intent(getContext(), ShowEventActivity.class);
                showContent.putExtra(BundleKey.EVENT, event);
                getContext().startActivity(showContent);

            }
        });

        return convertView;
    }

    private static class Holder{
        public final TextView Title;
        public final TextView LocationName;
        public final TextView DateTime;
        public final TextView City;
        public final SmartImageView ImageView;

        private Holder(TextView title, TextView location, TextView dateTime, TextView city, SmartImageView imageView) {
            this.Title = title;
            this.LocationName = location;
            this.DateTime = dateTime;
            this.City = city;
            this.ImageView = imageView;
        }
    }
}
