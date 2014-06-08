package nikita.rgr.lastfm;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;
import android.webkit.URLUtil;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Nikita on 07.06.14.
 */
public class LastFmApiRequestUrlBuilder {

    private String baseUrl;
    private String method;
    private Integer page;
    private Integer limit;
    private String apiKey;

    public LastFmApiRequestUrlBuilder(Context context) {
        this.baseUrl = context.getString(R.string.api_base_url);
        this.apiKey = context.getString(R.string.api_key);
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public void setMethod(String apiMethodName)
    {
        method = apiMethodName;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public void setAdditionalParam(String key, String value)
    {

    }

    public String getUrl()
    {
        HashMap<String, String> params = new HashMap<>();

        params.put("page", page.toString());
        params.put("limit", limit.toString());

        params.put("method", method);
        params.put("api_key", apiKey);
        params.put("country", "spain");

        String res = baseUrl + "?" + buildQueryStringFromParams(params);
        MyLog.d("URL^  :   " + res);
        return res;
    }

    private String buildQueryStringFromParams(HashMap<String, String> params)
    {
        String result = "";
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry)it.next();

            result += pairs.getKey() + "=" + pairs.getValue();

            if (it.hasNext())
            {
                result += "&";
            }

            it.remove();
        }

        return result;
    }

}
