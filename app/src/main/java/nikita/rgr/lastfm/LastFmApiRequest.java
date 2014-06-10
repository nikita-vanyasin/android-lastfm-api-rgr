package nikita.rgr.lastfm;

import android.content.Context;
import android.util.Xml;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestInputStream;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Nikita on 07.06.14.
 */
public class LastFmApiRequest {

    private String baseUrl;
    private String method;
    private Integer page;
    private Integer limit;
    private String apiKey;
    private final Context context;

    private HashMap<String, String> additionalParams;

    public LastFmApiRequest(Context context) {
        this.context = context;
        this.baseUrl = context.getString(R.string.api_base_url);
        this.apiKey = context.getString(R.string.api_key);
        this.additionalParams = new HashMap<>();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setMethod(String apiMethodName) {
        method = apiMethodName;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setAdditionalParam(String key, String value) {
        additionalParams.put(key, value);
    }

    public URL getUrl() {
        String res = getUrlString();

        MyLog.d("URL:   " + res);

        try {
            return new URL(res);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(context.getString(R.string.error_preparing_url), e);
        }
    }

    public String getHash() {
        String requestUrl = getUrlString();
        return Encrypt.md5(requestUrl);
    }

    private String getUrlString() {
        HashMap<String, String> params = new HashMap<>();

        params.put("page", page.toString());
        params.put("limit", limit.toString());

        params.put("method", method);
        params.put("api_key", apiKey);

        params.putAll(additionalParams);

        return baseUrl + "?" + buildQueryStringFromParams(params);
    }

    private String buildQueryStringFromParams(HashMap<String, String> params) {
        String result = "";
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pairs = (HashMap.Entry) it.next();

            result += pairs.getKey() + "=" + pairs.getValue();

            if (it.hasNext()) {
                result += "&";
            }
        }

        return result;
    }

}
