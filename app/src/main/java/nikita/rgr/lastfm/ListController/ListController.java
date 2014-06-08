package nikita.rgr.lastfm.ListController;

import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import nikita.rgr.lastfm.EndlessScrollListener;
import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LoadItemsTask;
import nikita.rgr.lastfm.MainActivity;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 24.05.14.
 */
public abstract class ListController {

    private MainActivity activity;
    private ListView listView;
    private LoadItemsTask loadItemsTask;
    private ListAdapter listAdapter;
    private int currentPage = 0;

    protected ListController(final MainActivity activity, ListView listView) {
        this.activity = activity;
        this.listView = listView;


        FrameLayout footerLayout = (FrameLayout)activity.getLayoutInflater().inflate(R.layout.list_footer,null);
        TextView footer = (TextView) footerLayout.findViewById(R.id.footer);

        listView.addFooterView(footerLayout);



        loadItemsTask = new LoadItemsTask(createResponseParser(), getApiRequestUrl());
        listView.setOnScrollListener(new EndlessScrollListener(this));

        listAdapter = new ArrayAdapter<>(activity, getListItemResourceId(), new ArrayList<>());
        listView.setAdapter(listAdapter);
    }

    public void advanceCurrentPage()
    {
        currentPage++;
    }

    public void show()
    {
        // if list is empty and no errors - loadNextPage()
        if (listAdapter.isEmpty())
        {
            loadNextPage();
        }
    }

    public void loadNextPage()
    {
        loadItemsTask.execute(currentPage + 1);
    }

    abstract protected LastFmApiResponseParser createResponseParser();

    /*abstract*/ protected int getListItemResourceId()
    {
        return android.R.layout.simple_list_item_2;
    }

    private String getApiRequestUrl()
    {
        LastFmApiRequestUrlBuilder builder = new LastFmApiRequestUrlBuilder();
        return builder.getUrl();
    }

}
