package nikita.rgr.lastfm.LastFmApiResponseParser;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import nikita.rgr.lastfm.LastFmObject.LastFmObject;
import nikita.rgr.lastfm.MyLog;

/**
 * Created by Nikita on 25.05.14.
 */
abstract public class LastFmApiResponseParser {

    protected List<LastFmObject> results;

    abstract protected String getResultElementTagName();
    abstract protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException;

    public List<LastFmObject> getResults() {
        return results;
    }

    public void parse(InputStream response)
    {
        results = new ArrayList<>();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( response, null );

            readLfmTag(xpp);

        } catch (XmlPullParserException e) {
            throw new RuntimeException("", e);
        }
        catch (IOException e)
        {
            throw new RuntimeException("", e);
        }
    }

    private void readLfmTag(XmlPullParser xpp) throws IOException, XmlPullParserException
    {
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            Boolean isStartTag = eventType == XmlPullParser.START_TAG;
            if (isStartTag)
            {
                String tagName = xpp.getName();
                String searchTagName = getResultElementTagName();
                Boolean nameMatchesToResultElementName = tagName.equals(searchTagName);
                if (nameMatchesToResultElementName) {
                    parseResultElement(xpp);
                }
            }

            eventType = xpp.next();
        }
    }
}
