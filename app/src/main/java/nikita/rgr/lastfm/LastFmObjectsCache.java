package nikita.rgr.lastfm;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nikita.rgr.lastfm.LastFmObject.LastFmObject;

/**
 * Created by Nikita on 09.06.14.
 */
public class LastFmObjectsCache {

    private static final String KEY_OBJECTS = "objects";
    private static final String KEY_EXPIRATION_DATE = "expire_date";

    private final Context context;
    private final Gson gson;

    public LastFmObjectsCache(Context context) {
        this.context = context;
        this.gson = new Gson();
        cleanExpiredCache();
    }

    public List<LastFmObject> get(String key) {
        String data = readFile(getCacheFile(key));

        JsonObject object = gson.fromJson(data, JsonObject.class);
        JsonElement valuesElement = object.get(KEY_OBJECTS);

        Type token = new TypeToken<List<LastFmObject>>() {
        }.getType();

        return gson.fromJson(valuesElement, token);

/*
        MyLog.d("data:" + data);

        try
        {
            JSONObject obj = new JSONObject(data);
            JSONArray arr = obj.getJSONArray(KEY_OBJECTS);



            ArrayList<LastFmObject> result = new ArrayList<>();
            for (int i = 0; i < arr.length(); ++i)
            {
                result.add((LastFmObject)arr.get(i));
            }

            return result;
        }
        catch (JSONException e)
        {
            throw new RuntimeException("Exception while parsing json: " + e.getMessage());
        }*/
    }

    public boolean has(String key) {
        return false;
        //return !isExpiredCacheFile(getCacheFile(key));
    }

    private boolean isExpiredCacheFile(File file) {
        String data = readFile(file);

        if (data.isEmpty()) {
            return true;
        }

        JsonObject object = gson.fromJson(data, JsonObject.class);
        JsonElement valuesElement = object.get(KEY_EXPIRATION_DATE);

        long objExpirationTimeStamp = gson.fromJson(valuesElement, long.class);
        long currentTimeStamp = new Date().getTime();

        return (objExpirationTimeStamp <= currentTimeStamp);

/*
        try
        {
            JSONObject obj = new JSONObject(data);
            long objExpirationTimeStamp = obj.getLong(KEY_EXPIRATION_DATE);
            long currentTimeStamp = new Date().getTime();

            return (objExpirationTimeStamp <= currentTimeStamp);
        }
        catch (JSONException e)
        {
            return true;
        }*/
    }

    public void set(String key, List<LastFmObject> value, Date expirationDate) {
        JsonObject object = new JsonObject();

        object.addProperty(KEY_EXPIRATION_DATE, expirationDate.getTime());
        JsonElement valuesTree = gson.toJsonTree(value);


        object.add(KEY_OBJECTS, valuesTree);
        writeFile(key, gson.toJson(object));


        /*
        JSONObject json = new JSONObject();
        try
        {
            json.put(KEY_EXPIRATION_DATE, expirationDate.getTime());

            JSONArray jsonArray = new JSONArray();
            for (LastFmObject object : value) {

                jsonArray.put(object);
            }

            json.put(KEY_OBJECTS, jsonArray);

        }
        catch (JSONException e)
        {
            MyLog.d("Exception while caching: " + e.getMessage());
            return;
        }

        writeFile(key, json.toString());*/
    }

    private void cleanExpiredCache() {
        File cacheDir = context.getCacheDir();
        File files[] = cacheDir.listFiles();

        for (File file : files) {
            MyLog.d("file in cache dir: " + file.getName());
            if (file.isDirectory()) {
                continue;
            }

            Boolean isExpired = isExpiredCacheFile(file);
            if (isExpired) {
                file.delete();
            }
        }
    }

    private void writeFile(String cacheKey, String data) {
        try {
            File cacheFile = new File(context.getCacheDir(), cacheKey);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(cacheFile));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            throw new RuntimeException("error writing file", e);
        }
    }

    private String readFile(File cacheFile) {
        if (!cacheFile.exists() || !cacheFile.canRead()) {
            return "";
        }

        FileInputStream fin;
        try {
            fin = new FileInputStream(cacheFile);
            String result = convertStreamToString(fin);
            fin.close();
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException("error while reading file", e);
        }
    }

    private File getCacheFile(String cacheKey) {
        return new File(context.getCacheDir(), cacheKey);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }
}
