package nikita.rgr.lastfm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends Activity {

    private TabHost tabHost;

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

        TabHost.TabSpec spec1=tabHost.newTabSpec("tabArtists");
        spec1.setContent(R.id.tabArtists);
        spec1.setIndicator(getString(R.string.artists));

        TabHost.TabSpec spec2=tabHost.newTabSpec("tabTracks");
        spec2.setIndicator(getString(R.string.tracks));
        spec2.setContent(R.id.tabTracks);

        TabHost.TabSpec spec3=tabHost.newTabSpec("tabEvents");
        spec3.setIndicator(getString(R.string.events));
        spec3.setContent(R.id.tabEvents);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);
        tabHost.addTab(spec3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
