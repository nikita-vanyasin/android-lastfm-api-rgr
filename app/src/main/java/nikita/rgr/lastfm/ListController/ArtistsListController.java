package nikita.rgr.lastfm.ListController;

import android.widget.ListView;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmListAdapter.ArtistsLastFmListAdapter;
import nikita.rgr.lastfm.LastFmListAdapter.LastFmListAdapter;
import nikita.rgr.lastfm.MainActivity;

/**
 * Created by Nikita on 24.05.14.
 */
public class ArtistsListController extends ListController {

    public static final int PAGE_SIZE = 50;

    public ArtistsListController(MainActivity activity, ListView listVew) {
        super(activity, listVew);
    }

    @Override
    protected LastFmListAdapter createListAdapter(MainActivity activity, TextView footer) {
        return new ArtistsLastFmListAdapter(activity, PAGE_SIZE, footer);
    }

}
