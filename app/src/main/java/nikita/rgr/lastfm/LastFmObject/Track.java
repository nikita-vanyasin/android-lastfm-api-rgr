package nikita.rgr.lastfm.LastFmObject;

/**
 * Created by Nikita on 07.06.14.
 */
public class Track extends LastFmObject {

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
}
