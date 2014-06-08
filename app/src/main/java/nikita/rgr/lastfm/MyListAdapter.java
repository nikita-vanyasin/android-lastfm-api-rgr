package nikita.rgr.lastfm;

import android.content.Context;
import android.widget.ArrayAdapter;

import nikita.rgr.lastfm.LastFmObject.LastFmObject;

/**
 * Created by Nikita on 08.06.14.
 */
public class MyListAdapter extends ArrayAdapter<LastFmObject> {


    public ProdAdapter(Context context, int pageSize, TextView footer) {
        super(context, android.R.layout.two_line_list_item);
        mPageSize = pageSize;
        mFooter = footer;
        mHasMoreItems = true;
    }

}
