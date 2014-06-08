package nikita.rgr.lastfm.LastFmObject;

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
}
