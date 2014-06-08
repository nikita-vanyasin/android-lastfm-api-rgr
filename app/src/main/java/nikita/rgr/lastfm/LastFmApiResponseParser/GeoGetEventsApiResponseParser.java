package nikita.rgr.lastfm.LastFmApiResponseParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nikita.rgr.lastfm.LastFmObject.Event;

/**
 * Created by Nikita on 08.06.14.
 */
public class GeoGetEventsApiResponseParser extends LastFmApiResponseParser {

    @Override
    protected String getElementsRootNode() {
        return "events";
    }

    @Override
    protected String getResultElementTagName() {
        return "event";
    }

    @Override
    protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException {

        Event event = new Event();

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals(getResultElementTagName())))) {

            if (eventType == XmlPullParser.START_TAG)
            {
                String tagName = xpp.getName();
                if (tagName.equals("title"))
                {
                    event.Title = xpp.nextText();
                }
                else if (tagName.equals("startDate"))
                {
                    event.StartDate = xpp.nextText();
                }
                else if (tagName.equals("startTime"))
                {
                    event.StartTime = xpp.nextText();
                }
                else if (tagName.equals("description"))
                {
                    event.Description = xpp.nextText();
                }
                else if (tagName.equals("url"))
                {
                    event.EventUrl = xpp.nextText();
                }
                else if (tagName.equals("artists"))
                {
                    parseArtistsTag(event, xpp);
                }
                else if (tagName.equals("venue"))
                {
                    parseVenueTag(event, xpp);
                }
                else if (tagName.equals("image"))
                {
                    String url = parseImageUrl(xpp, "medium");
                    if (!url.isEmpty())
                    {
                        event.SmallImageUrl = url;
                    }
                }
            }

            eventType = xpp.next();
        }

        results.add(event);
    }

    private void parseArtistsTag(Event event, XmlPullParser xpp) throws XmlPullParserException, IOException
    {
        List<String> artists = new ArrayList<>();
        String headliner = "";

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals("artists"))))
        {
            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("artist"))
            {
                artists.add(xpp.nextText());
            }

            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("headliner"))
            {
                headliner = xpp.nextText();
            }

            eventType = xpp.next();
        }

        event.Artists = artists;
        event.Headliner = headliner;
    }

    private void parseVenueTag(Event event, XmlPullParser xpp) throws XmlPullParserException, IOException
    {
        String locationName = "";

        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals("venue")))) {
            if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("name"))
            {
                locationName = xpp.nextText();
            }
            else if (eventType == XmlPullParser.START_TAG && xpp.getName().equals("location"))
            {
                parseLocationTag(event, xpp);
            }

            eventType = xpp.next();
        }

        event.EventLocationName = locationName;
    }

    private void parseLocationTag(Event event, XmlPullParser xpp) throws XmlPullParserException, IOException
    {
        int eventType = xpp.getEventType();
        while (!((eventType == XmlPullParser.END_TAG) && (xpp.getName().equals("location")))) {
            if (eventType == XmlPullParser.START_TAG)
            {
                String tagName = xpp.getName();
                if (tagName.equals("city"))
                {
                    event.City = xpp.nextText();
                }
                else if (tagName.equals("country"))
                {
                    event.Country = xpp.nextText();
                }
                else if (tagName.equals("street"))
                {
                    event.Street = xpp.nextText();
                }
            }

            eventType = xpp.next();
        }
    }
}
