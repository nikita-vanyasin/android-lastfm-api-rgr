package nikita.rgr.lastfm.LastFmApiResponseParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nikita.rgr.lastfm.LastFmObject.Track;

/**
 * Created by Nikita on 08.06.14.
 */
public class GeoGetTopTracksApiResponseParser extends LastFmApiResponseParser {

    @Override
    protected String getElementsRootNode() {
        return "toptracks";
    }

    @Override
    protected String getResultElementTagName() {
        return "track";
    }

    @Override
    protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException {

        Track track = new Track();

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals(getResultElementTagName())))) {

            if (eventType == XmlPullParser.START_TAG) {
                String tagName = xpp.getName();
                if (tagName.equals("name")) {
                    track.Name = xpp.nextText();
                } else if (tagName.equals("listeners")) {
                    track.ListenersCount = xpp.nextText();
                } else if (tagName.equals("url")) {
                    track.TrackUrl = xpp.nextText();
                } else if (tagName.equals("image")) {
                    String url = parseImageUrl(xpp, "small");
                    if (!url.isEmpty()) {
                        track.SmallImageUrl = url;
                    }
                } else if (tagName.equals("artist")) {
                    track.ArtistName = readArtistName(xpp);
                }
            }

            eventType = xpp.next();
        }

        results.add(track);
    }

    private String readArtistName(XmlPullParser xpp) throws XmlPullParserException, IOException {
        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals("artist")))) {
            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("name")) {
                return xpp.nextText();
            }

            eventType = xpp.next();
        }

        throw new RuntimeException("track artist name not found");
    }

}
