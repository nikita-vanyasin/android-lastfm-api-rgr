package nikita.rgr.lastfm;

import android.content.Context;

import junit.framework.Assert;

import java.util.List;

import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;
import nikita.rgr.lastfm.LastFmObject.LastFmObject;

/**
 * Created by Nikita on 09.06.14.
 */
public class LastFmObjectsRepository {

    private static LastFmObjectsRepository instance;
    private final LastFmObjectsCache cache;
    private final LastFmApiClient apiClient;

    public static void createInstance(Context context) {
        if (instance == null) {
            instance = new LastFmObjectsRepository(context);
        }
    }

    public static LastFmObjectsRepository getInstance() {
        Assert.assertNotNull(instance);
        return instance;
    }

    private LastFmObjectsRepository(Context context) {
        cache = new LastFmObjectsCache(context);
        apiClient = new LastFmApiClient();
    }

    public List<LastFmObject> getObjects(LastFmApiRequest request, LastFmApiResponseParser apiResponseParser) {

        MyLog.d("requesting " + request.getUrl());

        String key = request.getHash();
        if (cache.has(key)) {
            return cache.get(key);
        }

        LastFmApiResponse response = apiClient.makeRequest(request, apiResponseParser);
        if (!response.Objects.isEmpty()) {
            cache.set(key, response.Objects, response.ExpirationDate);
        }
        return response.Objects;
    }
}
