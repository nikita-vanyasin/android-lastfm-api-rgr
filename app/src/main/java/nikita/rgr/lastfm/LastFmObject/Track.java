package nikita.rgr.lastfm.LastFmObject;

import android.os.Parcel;

/**
 * Created by Nikita on 07.06.14.
 */
public class Track extends LastFmObject implements android.os.Parcelable {

    public String Name;
    public String TrackUrl;
    public String ArtistName;
    public String ListenersCount;
    public String SmallImageUrl;

    @Override
    public String toString() {
        return "Track{" +
                "Name='" + Name + '\'' +
                ", TrackUrl='" + TrackUrl + '\'' +
                ", ArtistName='" + ArtistName + '\'' +
                ", ListenersCount=" + ListenersCount +
                ", SmallImageUrl='" + SmallImageUrl + '\'' +
                '}';
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.TrackUrl);
        dest.writeString(this.ArtistName);
        dest.writeString(this.ListenersCount);
        dest.writeString(this.SmallImageUrl);
    }

    public Track() {
    }

    private Track(Parcel in) {
        this.Name = in.readString();
        this.TrackUrl = in.readString();
        this.ArtistName = in.readString();
        this.ListenersCount = in.readString();
        this.SmallImageUrl = in.readString();
    }

    public static Creator<Track> CREATOR = new Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
