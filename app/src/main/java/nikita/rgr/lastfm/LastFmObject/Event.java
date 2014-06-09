package nikita.rgr.lastfm.LastFmObject;

import android.os.Parcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nikita on 07.06.14.
 */
public class Event extends LastFmObject implements android.os.Parcelable {

    public String Title = "";
    public String SmallImageUrl = "";
    public List<String> Artists = new ArrayList<>();
    public String Headliner = "";
    public String StartDate = "";
    public String StartTime = "";
    public String Description = "";
    public String EventUrl = "";

    public String EventLocationName = "";
    public String City = "";
    public String Country = "";
    public String Street = "";

    public String Timezone = "";


    public String getAddress() {
        return Street + (Street.isEmpty() ? "" : ", ") + City;
    }

    public Date getStartDateTime() {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm", Locale.ENGLISH);
        try {
            return df.parse(StartDate + " " + StartTime);
        }
        catch (ParseException e) {
            throw new RuntimeException("error while parsing datetime", e);
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "Title='" + Title + '\'' +
                ", SmallImageUrl='" + SmallImageUrl + '\'' +
                ", Artists=" + Artists +
                ", Headliner='" + Headliner + '\'' +
                ", StartDate='" + StartDate + '\'' +
                ", StartTime='" + StartTime + '\'' +
                ", Description='" + Description + '\'' +
                ", EventUrl='" + EventUrl + '\'' +
                ", EventLocationName='" + EventLocationName + '\'' +
                ", City='" + City + '\'' +
                ", Country='" + Country + '\'' +
                ", Street='" + Street + '\'' +
                ", Timezone='" + Timezone + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Title);
        dest.writeString(this.SmallImageUrl);
        dest.writeStringList(this.Artists);
        dest.writeString(this.Headliner);
        dest.writeString(this.StartDate);
        dest.writeString(this.StartTime);
        dest.writeString(this.Description);
        dest.writeString(this.EventUrl);
        dest.writeString(this.EventLocationName);
        dest.writeString(this.City);
        dest.writeString(this.Country);
        dest.writeString(this.Street);
        dest.writeString(this.Timezone);
    }

    public Event() {
    }

    private Event(Parcel in) {
        this.Title = in.readString();
        this.SmallImageUrl = in.readString();
        in.readStringList(this.Artists);
        this.Headliner = in.readString();
        this.StartDate = in.readString();
        this.StartTime = in.readString();
        this.Description = in.readString();
        this.EventUrl = in.readString();
        this.EventLocationName = in.readString();
        this.City = in.readString();
        this.Country = in.readString();
        this.Street = in.readString();
        this.Timezone = in.readString();
    }

    public static Creator<Event> CREATOR = new Creator<Event>() {
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
