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

    private Integer totalPagesCount;
    protected List<LastFmObject> results;

    abstract protected String getResultElementTagName();
    abstract protected void parseResultElement(XmlPullParser xpp) throws IOException, XmlPullParserException;
    abstract protected String getElementsRootNode();

    public List<LastFmObject> getResults() {
        return results;
    }

    public Integer getTotalPagesCount()
    {
        return totalPagesCount;
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

    protected String parseImageUrl(XmlPullParser xpp, String requiredSize) throws XmlPullParserException, IOException {
        String sizeAttributeValue = "";
        for (int i = 0; i < xpp.getAttributeCount(); ++i)
        {
            String attributeName = xpp.getAttributeName(i);
            if (attributeName.equals("size"))
            {
                sizeAttributeValue = xpp.getAttributeValue(i);
            }
        }

        if (sizeAttributeValue.equals(requiredSize))
        {
            return xpp.nextText();
        }

        return "";
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

                if (tagName.equals(getElementsRootNode()))
                {
                    String totalPagesAttributeValue = "";
                    for (int i = 0; i < xpp.getAttributeCount(); ++i)
                    {
                        String attributeName = xpp.getAttributeName(i);
                        if (attributeName.equals("totalPages"))
                        {
                            totalPagesAttributeValue = xpp.getAttributeValue(i);
                        }
                    }

                    if (totalPagesAttributeValue.isEmpty())
                    {
                        throw new RuntimeException("totalPages attribute not found.");
                    }

                    totalPagesCount = Integer.parseInt(totalPagesAttributeValue);
                }
            }

            eventType = xpp.next();
        }
    }
}
