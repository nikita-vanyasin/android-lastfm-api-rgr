package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.LastFmObject;
import nikita.rgr.lastfm.LoadItemsTask;
import nikita.rgr.lastfm.MyLog;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public abstract class LastFmListAdapter extends ArrayAdapter<LastFmObject> {

    private boolean hasMoreItems;
    private int currentPage;
    private final TextView footer;
    private LastFmApiRequestUrlBuilder requestUrlBuilder;

    public LastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, android.R.layout.two_line_list_item);
        this.footer = footer;
        this.hasMoreItems = true;
        this.currentPage = 0;

        requestUrlBuilder = new LastFmApiRequestUrlBuilder(context);
        requestUrlBuilder.setLimit(pageSize);

        setupRequestUrlBuilderSettings(requestUrlBuilder);
    }

    public void setTotalPages(Integer totalPages){
        if (currentPage >= totalPages)
        {
            hasMoreItems = false;
            footer.setText(getContext().getString(R.string.no_more_items));
        }
    }

    abstract protected void setupRequestUrlBuilderSettings(LastFmApiRequestUrlBuilder builder);

    abstract protected LastFmApiResponseParser createResponseParser();

    private String getApiRequestUrl()
    {
        //from: position + 1, to: position + 1 + pageSize
        requestUrlBuilder.setPage(currentPage);
        return requestUrlBuilder.getUrl();
    }

    public void loadPage()
    {
        LoadItemsTask t = new LoadItemsTask(getContext(),this, createResponseParser(), getApiRequestUrl());
        t.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == getCount() - 1 && hasMoreItems){

            currentPage++;

            loadPage();
            footer.setText(getContext().getString(R.string.Loading));
        }

        return forgeItemView(convertView, position, parent);
    }

    abstract protected View forgeItemView(View convertView, int position, ViewGroup parent);
}
