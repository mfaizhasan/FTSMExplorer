package mfaizhasan.com.ftsmexplorer;

/**
 * Created by FAIZ on 27/9/2016.
 */

public class LocationData {

    private String imageURL, title, floor, block, lat, longi;

    public LocationData() {
    }

    public LocationData(String imageURL, String title, String floor, String block, String lat, String longi) {
        this.imageURL = imageURL;
        this.title = title;
        this.floor = floor;
        this.block = block;
        this.lat = lat;
        this.longi = longi;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
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
}
