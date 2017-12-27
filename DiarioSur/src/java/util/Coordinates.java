package util;

import service.GoogleMaps;

public class Coordinates {
    private double latitude;
    private double longitude;

    public Coordinates() {
        this(.0, .0);
    }

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates(String address) {
        Coordinates tmp = GoogleMaps.getInstance().address2Coords(address);
        this.latitude = tmp.latitude;
        this.longitude = tmp.longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distance(Coordinates other) {
        return Math.sqrt(Math.pow(latitude - other.latitude, 2) + Math.pow(longitude - other.longitude, 2));
    }

    public boolean inRadius(Coordinates other, double radius) {
        return distance(other) <= radius;
    }
}
