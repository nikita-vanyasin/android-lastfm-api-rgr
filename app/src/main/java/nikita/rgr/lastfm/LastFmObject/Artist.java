package nikita.rgr.lastfm.LastFmObject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nikita on 07.06.14.
 */
public class Artist extends LastFmObject {

    public String SmallImageUrl;
    public String Name;
    public Integer ListenersCount;
    public String ArtistUrl;

    @Override
    public String toString() {
        return "Artist{" +
                "SmallImageUrl='" + SmallImageUrl + '\'' +
                ", Name='" + Name + '\'' +
                ", ListenersCount=" + ListenersCount +
                ", ArtistUrl='" + ArtistUrl + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.SmallImageUrl);
        dest.writeString(this.Name);
        dest.writeValue(this.ListenersCount);
        dest.writeString(this.ArtistUrl);
    }

    public Artist() {
    }

    private Artist(Parcel in) {
        this.SmallImageUrl = in.readString();
        this.Name = in.readString();
        this.ListenersCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ArtistUrl = in.readString();
    }

    public static Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}
