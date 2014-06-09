package nikita.rgr.lastfm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

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
        lockOrientationChange();
        if (savedInstanceState == null) {
            //...
        }

        GeoInfo.createInstance(this);
        LastFmObjectsRepository.createInstance(this);

        if (!GeoInfo.getInstance().isLocationInfoAvailable())
        {
            showLocationUnavailableAlert();
            return;
        }

        initTabs();
    }

    private void lockOrientationChange() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void showLocationUnavailableAlert() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_enable_services))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initTabs() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        listControllers = new HashMap<>();

        initTab("tabArtists", R.id.tabArtists, R.string.artists, new ArtistsListController(this, (ListView) findViewById(R.id.artistsList)));
        initTab("tabTracks", R.id.tabTracks, R.string.tracks, new TracksListController(this, (ListView) findViewById(R.id.tracksList)));
        initTab("tabEvents", R.id.tabEvents, R.string.events, new EventsListController(this, (ListView) findViewById(R.id.eventsList)));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String id) {
                listControllers.get(id).show();
            }
        });

        listControllers.get(tabHost.getCurrentTabTag()).show();
    }

    private void initTab(String tabId, int tabContentId, int tabTitleTextId, ListController listController) {
        TabHost.TabSpec spec = tabHost.newTabSpec(tabId);
        spec.setContent(tabContentId);
        spec.setIndicator(getString(tabTitleTextId));
        tabHost.addTab(spec);

        listControllers.put(tabId, listController);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        lockOrientationChange();
    }
}
