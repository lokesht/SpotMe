package location.track.model;

/**
 * Created by Lokesh on 28-09-2015.
 */
public class Item {

    String lat;
    String longi;
    String date;

    public Item(String lat, String longi, String date) {
        this.lat = lat;
        this.longi = longi;
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
