package nikita.rgr.lastfm.LastFmApiResponseParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nikita.rgr.lastfm.LastFmObject.Artist;


/**
 * Created by Nikita on 07.06.14.
 */
public class GeoGetTopArtistsApiResponseParser extends LastFmApiResponseParser {

    @Override
    protected String getResultElementTagName() {
        return "artist";
    }

    @Override
    protected String getElementsRootNode() {
        return "topartists";
    }

    @Override
    protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException {

        Artist artist = new Artist();

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals(getResultElementTagName())))) {

            if (eventType == XmlPullParser.START_TAG) {
                String tagName = xpp.getName();
                if (tagName.equals("name")) {
                    artist.Name = xpp.nextText();
                } else if (tagName.equals("listeners")) {
                    artist.ListenersCount = Integer.parseInt(xpp.nextText());
                } else if (tagName.equals("url")) {
                    artist.ArtistUrl = xpp.nextText();
                } else if (tagName.equals("image")) {
                    String url = parseImageUrl(xpp, "small");
                    if (!url.isEmpty()) {
                        artist.SmallImageUrl = url;
                    }
                }
            }

            eventType = xpp.next();
        }

        results.add(artist);
    }
}
