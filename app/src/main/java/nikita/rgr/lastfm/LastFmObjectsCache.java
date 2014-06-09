package nikita.rgr.lastfm;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import junit.framework.Assert;

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
    private static final String KEY_OBJECT_CLASS = "class";

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
        String className = gson.fromJson(object.get(KEY_OBJECT_CLASS), String.class);
        JsonArray array = object.getAsJsonArray(KEY_OBJECTS);
        ArrayList<LastFmObject> results = new ArrayList<>();

        try
        {
            for (int i = 0; i < array.size(); ++i)
            {
                results.add((LastFmObject)gson.fromJson(array.get(i), Class.forName(className)));
            }
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("error while deserializing cache", e);
        }

        return results;

    }

    public boolean has(String key) {
        return !isExpiredCacheFile(getCacheFile(key));
    }

    private boolean isExpiredCacheFile(File file) {
        String data = readFile(file);

        if (data.isEmpty()) {
            return true;
        }

        try
        {
            JsonObject object = gson.fromJson(data, JsonObject.class);
            JsonElement valuesElement = object.get(KEY_EXPIRATION_DATE);

            long objExpirationTimeStamp = gson.fromJson(valuesElement, long.class);
            long currentTimeStamp = new Date().getTime();

            return (objExpirationTimeStamp <= currentTimeStamp);
        }
        catch (JsonSyntaxException e)
        {
            return true;
        }
    }

    public void set(String key, List<LastFmObject> values, Date expirationDate) {

        Assert.assertTrue(values.size() > 0);

        JsonObject object = new JsonObject();

        object.addProperty(KEY_EXPIRATION_DATE, expirationDate.getTime());
        object.addProperty(KEY_OBJECT_CLASS, values.get(0).getClass().getName());

        JsonArray array = new JsonArray();
        for (LastFmObject value : values)
        {
            JsonElement valueTree = gson.toJsonTree(value);
            array.add(valueTree);
        }

        object.add(KEY_OBJECTS, array);

        writeFile(key, gson.toJson(object));
    }

    private void cleanExpiredCache() {
        File cacheDir = context.getCacheDir();
        File files[] = cacheDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }

            Boolean isExpired = isExpiredCacheFile(file);
            if (isExpired) {
                MyLog.d("clearing not valid cache: " + file.getName());
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
