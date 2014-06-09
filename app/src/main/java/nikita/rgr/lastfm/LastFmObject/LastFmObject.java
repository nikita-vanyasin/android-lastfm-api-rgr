package nikita.rgr.lastfm.LastFmObject;

import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Nikita on 07.06.14.
 */
abstract public class LastFmObject implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

}
