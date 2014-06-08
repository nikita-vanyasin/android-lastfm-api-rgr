package nikita.rgr.lastfm;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.HashMap;

import nikita.rgr.lastfm.ListController.ArtistsListController;
import nikita.rgr.lastfm.ListController.EventsListController;
import nikita.rgr.lastfm.ListController.ListController;
import nikita.rgr.lastfm.ListController.TracksListController;

public class MainActivity extends Activity {

    private TabHost tabHost;
    private HashMap<String, ListController> listControllers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
          //...
        }

        initTabs();
    }

    private void initTabs()
    {
        tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        listControllers = new HashMap<>();

        initTab("tabArtists", R.id.tabArtists, R.string.artists,  new ArtistsListController(this, (ListView)findViewById(R.id.artistsList)));
        initTab("tabTracks", R.id.tabTracks, R.string.tracks, new TracksListController(this, (ListView)findViewById(R.id.tracksList)));
        initTab("tabEvents", R.id.tabEvents, R.string.events, new EventsListController(this, (ListView)findViewById(R.id.eventsList)));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String id) {
                listControllers.get(id).show();
            }
        });

        listControllers.get(tabHost.getCurrentTabTag()).show();
    }

    private void initTab(String tabId, int tabContentId, int tabTitleTextId, ListController listController)
    {
        TabHost.TabSpec spec = tabHost.newTabSpec(tabId);
        spec.setContent(tabContentId);
        spec.setIndicator(getString(tabTitleTextId));
        tabHost.addTab(spec);

        listControllers.put(tabId, listController);
    }

}
