package nikita.rgr.lastfm.LastFmObject;

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
public class Event extends LastFmObject {

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


    public String getAddress()
    {
        return Street + (Street.isEmpty() ? "" : ", ") + City;
    }

    public Date getStartDateTime()
    {
        DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm", Locale.ENGLISH);
        try {
            return df.parse(StartDate + " " + StartTime);
        }
        catch (ParseException e)
        {
            throw new RuntimeException("error while parsing datetime", e);
        }
    }

}
