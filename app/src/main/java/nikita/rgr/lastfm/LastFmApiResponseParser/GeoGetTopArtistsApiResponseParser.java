package nikita.rgr.lastfm.LastFmApiResponseParser;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import nikita.rgr.lastfm.LastFmObject.Artist;
import nikita.rgr.lastfm.MyLog;


/**
 * Created by Nikita on 07.06.14.
 */
public class GeoGetTopArtistsApiResponseParser extends LastFmApiResponseParser {

    @Override
    protected String getResultElementTagName() {
        return "artist";
    }

    @Override
    protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException {

        Artist artist = new Artist();

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals(getResultElementTagName())))) {

            if (eventType == XmlPullParser.START_TAG)
            {
                String tagName = xpp.getName();
                if (tagName.equals("name"))
                {
                    artist.Name = xpp.nextText();
                }
                else if (tagName.equals("listeners"))
                {
                    artist.ListenersCount = Integer.parseInt(xpp.nextText());
                }
                else if (tagName.equals("url"))
                {
                    artist.ArtistUrl = xpp.nextText();
                }
                else if (tagName.equals("image"))
                {
                    String sizeAttributeValue = "";
                    for (int i = 0; i < xpp.getAttributeCount(); ++i)
                    {
                        String attributeName = xpp.getAttributeName(i);
                        if (attributeName.equals("size"))
                        {
                            sizeAttributeValue = xpp.getAttributeValue(i);
                        }
                    }

                    if (sizeAttributeValue.equals("small"))
                    {
                        artist.SmallImageUrl = xpp.nextText();
                    }
                }
            }

            eventType = xpp.next();
        }

        results.add(artist);
    }
}
