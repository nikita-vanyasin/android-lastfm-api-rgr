package nikita.rgr.lastfm.ListController;

import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmListAdapter.LastFmListAdapter;
import nikita.rgr.lastfm.MainActivity;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 24.05.14.
 */
public abstract class ListController {

    private LastFmListAdapter listAdapter;

    protected ListController(final MainActivity activity, ListView listView) {


        FrameLayout footerLayout = (FrameLayout) activity.getLayoutInflater().inflate(R.layout.list_footer, null);
        TextView footer = (TextView) footerLayout.findViewById(R.id.footer);

        listView.addFooterView(footerLayout);

        listAdapter = createListAdapter(activity, footer);
        listView.setAdapter(listAdapter);
    }

    public void show() {
        if (listAdapter.isEmpty()) {
            listAdapter.loadPage();
        }
    }

    abstract protected LastFmListAdapter createListAdapter(final MainActivity activity, TextView footer);

}
