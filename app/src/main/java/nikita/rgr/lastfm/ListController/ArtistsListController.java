package nikita.rgr.lastfm.ListController;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import nikita.rgr.lastfm.LastFmApiResponseParser.GeoGetTopArtistsApiResponseParser;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmListAdapter.ArtistsLastFmListAdapter;
import nikita.rgr.lastfm.LastFmListAdapter.LastFmListAdapter;
import nikita.rgr.lastfm.LastFmObject.Artist;
import nikita.rgr.lastfm.ListController.ListController;
import nikita.rgr.lastfm.MainActivity;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 24.05.14.
 */
public class ArtistsListController extends ListController {

    public ArtistsListController(MainActivity activity, ListView listVew) {
        super(activity, listVew);
    }

    @Override
    protected LastFmListAdapter createListAdapter(MainActivity activity, TextView footer) {
        return new ArtistsLastFmListAdapter(activity, 50, footer);
    }

}
