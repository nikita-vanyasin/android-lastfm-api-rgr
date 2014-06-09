package nikita.rgr.lastfm;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import nikita.rgr.lastfm.LastFmApiResponseParser.LastFmApiResponseParser;

/**
 * Created by Nikita on 09.06.14.
 */
class LastFmApiClient {


    public LastFmApiResponse makeRequest(LastFmApiRequest request, LastFmApiResponseParser apiResponseParser) {
        LastFmApiResponse response = new LastFmApiResponse();

        HttpURLConnection urlConnection = getHttpURLConnection(request);
        urlConnection.getExpiration();
        response.ExpirationDate = getExpirationDate(urlConnection);

        InputStream inputStream = getInputStreamFromConnection(urlConnection);
        MyLog.d("connection established");
        try {
            apiResponseParser.parse(inputStream);
        }
        finally {
            closeInputStream(inputStream);
            urlConnection.disconnect();
        }
        response.Objects = apiResponseParser.getResults();

        return response;
    }


    private void closeInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }

    private InputStream getInputStreamFromConnection(URLConnection urlConnection) {
        try {
            return urlConnection.getInputStream();
        }
        catch (IOException e) {
            throw new RuntimeException("", e);
        }
    }

    private HttpURLConnection getHttpURLConnection(LastFmApiRequest request) {

        URL url = request.getUrl();

        HttpURLConnection urlConnection;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(false);
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.connect();
        }
        catch (IOException e) {
            throw new RuntimeException("Can not connect", e);
        }
        return urlConnection;
    }

    private Date getExpirationDate(URLConnection connection) {
        long expirationTimeStamp = connection.getExpiration();
        if (expirationTimeStamp <= 0) {
            return new Date();
        }

        return new Date(expirationTimeStamp);
    }


}
