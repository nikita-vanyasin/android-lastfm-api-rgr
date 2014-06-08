package nikita.rgr.lastfm.LastFmListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import nikita.rgr.lastfm.LastFmApiRequestUrlBuilder;
import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.Artist;
import nikita.rgr.lastfm.LastFmObject.LastFmObject;
import nikita.rgr.lastfm.LoadItemsTask;
import nikita.rgr.lastfm.R;

/**
 * Created by Nikita on 08.06.14.
 */
public abstract class LastFmListAdapter extends ArrayAdapter<LastFmObject> {

    private boolean hasMoreItems;
    private final int pageSize;
    private final TextView footer;

    public LastFmListAdapter(Context context, int pageSize, TextView footer) {
        super(context, android.R.layout.two_line_list_item);
        this.pageSize = pageSize;
        this.footer = footer;
        this.hasMoreItems = true;
    }


    abstract protected LastFmApiResponseParser createResponseParser();

    private String getApiRequestUrl()
    {
        LastFmApiRequestUrlBuilder builder = new LastFmApiRequestUrlBuilder();
        return builder.getUrl();
    }

    public void loadNextPage()
    {
        LoadItemsTask t = new LoadItemsTask(getContext(),this, createResponseParser(), getApiRequestUrl());
        t.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (position == getCount() - 1 && hasMoreItems){

            //from: position + 1, to: position + 1 + pageSize

            LoadItemsTask t = new LoadItemsTask(getContext(),this, createResponseParser(), getApiRequestUrl());
            t.execute();
            footer.setText("Loading . . .");
        }

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_list_row, parent, false);

            TextView name = (TextView) convertView.findViewById(R.id.artistName);
            TextView count = (TextView) convertView.findViewById(R.id.listenersCount);
            SmartImageView imageView = (SmartImageView) convertView.findViewById(R.id.list_image);

            convertView.setTag(new Holder(name, count, imageView));
        }

        Artist p = (Artist)getItem(position);
        Holder h = (Holder) convertView.getTag();

        h.Name.setText(p.Name);
        h.ListenersCount.setText(p.ListenersCount.toString());
        h.ImageView.setImageUrl(p.SmallImageUrl);


        return convertView;
    }

    private static class Holder{
        public final TextView Name;
        public final TextView ListenersCount;
        public final SmartImageView ImageView;

        private Holder(TextView name, TextView count, SmartImageView imageView) {
            this.Name = name;
            this.ListenersCount = count;
            this.ImageView = imageView;
        }
    }
}
