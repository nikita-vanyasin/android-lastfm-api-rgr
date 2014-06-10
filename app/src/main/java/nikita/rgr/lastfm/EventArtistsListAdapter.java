package nikita.rgr.lastfm;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nikita on 10.06.14.
 */
public class EventArtistsListAdapter extends ArrayAdapter<String> {

    private final String headliner;

    public EventArtistsListAdapter(Context context, List<String> artists, String headliner) {
        super(context, android.R.layout.simple_list_item_1, artists);
        this.headliner = headliner;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
/*
        if (position == getCount() - 1 && hasMoreItems) {

            currentPage++;

            loadPage();
            footer.setText(getContext().getString(R.string.Loading));
        }

        return forgeItemView(convertView, position, parent);*/

        convertView = super.getView(position, convertView, parent);

        if (getItem(position).equals(headliner)) {
            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setTextColor(Color.RED);
        }

        return convertView;
    }
}
