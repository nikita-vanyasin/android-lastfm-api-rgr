package nikita.rgr.lastfm;

import java.util.List;

/**
 * Created by Nikita on 07.06.14.
 */
public class LastFmApiRequestUrlBuilder {

    private String method;
    private int page;
    private int limit;
    private String apiKey;

    public void setMethod(String apiMethodNames)
    {

    }

    public void setPage(int page)
    {

    }

    public void setAdditionalParam(String key, String value)
    {

    }

    public String getUrl()
    {
        return "http://ws.audioscrobbler.com/2.0/?method=geo.gettopartists&country=spain&api_key=af854cc7be8dc1c0e3cf08fcb41ef4c6";
    }

}
