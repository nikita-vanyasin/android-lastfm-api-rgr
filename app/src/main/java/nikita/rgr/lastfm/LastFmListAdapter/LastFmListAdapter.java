package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import nikita.rgr.lastfm.LastFmApiRequest;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.LastFmObject;
import nikita.rgr.lastfm.LoadItemsTask;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public abstract class LastFmListAdapter extends ArrayAdapter<LastFmObject> {

    private boolean hasMoreItems;
    private int currentPage;
    private final TextView footer;
    private int pageSize;

    public LastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, android.R.layout.two_line_list_item);
        this.footer = footer;
        this.hasMoreItems = true;
        this.pageSize = pageSize;
        this.currentPage = 1;
    }

    public void setTotalPages(Integer totalPages) {
        if (currentPage >= totalPages) {
            hasMoreItems = false;
            footer.setText(getContext().getString(R.string.no_more_items));
        }
    }

    abstract protected void setupRequestUrlBuilderSettings(LastFmApiRequest apiRequest);

    abstract protected LastFmApiResponseParser createResponseParser();

    private LastFmApiRequest getApiRequest() {
        LastFmApiRequest apiRequest = new LastFmApiRequest(getContext());
        setupRequestUrlBuilderSettings(apiRequest);
        apiRequest.setPage(currentPage);
        apiRequest.setLimit(pageSize);

        return apiRequest;
    }

    public void loadPage() {
        LoadItemsTask t = new LoadItemsTask(this, createResponseParser(), getApiRequest());
        t.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == getCount() - 1 && hasMoreItems) {

            currentPage++;

            loadPage();
            footer.setText(getContext().getString(R.string.Loading));
        }

        return forgeItemView(convertView, position, parent);
    }

    abstract protected View forgeItemView(View convertView, int position, ViewGroup parent);
}
