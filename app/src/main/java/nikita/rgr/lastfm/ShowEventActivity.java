package nikita.rgr.lastfm;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nikita.rgr.lastfm.LastFmObject.Event;

public class ShowEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);

        if (savedInstanceState == null) {
           //...
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Event event = (Event) bundle.get(BundleKey.EVENT);

            renderEvent(event);
        }
    }

    private void renderEvent(final Event event)
    {
        TextView title = (TextView) findViewById(R.id.eventMainTitle);
        TextView locationName = (TextView) findViewById(R.id.eventMainLocationName);
        TextView dateTime = (TextView) findViewById(R.id.eventMainDateTime);
        TextView address = (TextView) findViewById(R.id.eventAddress);
        TextView description = (TextView) findViewById(R.id.eventDescription);
        SmartImageView imageView = (SmartImageView) findViewById(R.id.eventMainImage);
        Button addToCalendarButton = (Button) findViewById(R.id.addToCalendarButton);
        ListView artistsListView = (ListView) findViewById(R.id.eventArtistsListView);

        title.setText(event.Title);
        locationName.setText(event.EventLocationName);
        dateTime.setText(event.StartDate + " " + event.StartTime);
        address.setText(event.getAddress());
        description.setText(Html.fromHtml(event.Description));
        imageView.setImageUrl(event.SmallImageUrl);

        addToCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putEventToCalendar(event);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, event.Artists);
        artistsListView.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.EventUrl));
                startActivity(browserIntent);
            }
        });
    }

    private void putEventToCalendar(Event event)
    {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.Events.TITLE, event.Title);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.getAddress());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, event.Description);

        Date dateTime = event.getStartDateTime();

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateTime.getTime());

        startActivity(intent);
    }
}
