package util;

import service.GoogleMaps;

//import service.GoogleMaps;

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


    public double drivingDistance(Coordinates destination) {
        return 0.0;//GoogleMaps.getInstance().distance(this, destination);
    }

    public double distance(Coordinates destination) {
        return Math.toDegrees(
            Math.acos(
                Math.sin(Math.toRadians(latitude)) *
                Math.sin(Math.toRadians(destination.latitude)) +
                Math.cos(Math.toRadians(latitude)) *
                Math.cos(Math.toRadians(destination.latitude)) *
                Math.cos(Math.toRadians(longitude - destination.longitude))
            )
        ) * 69.09 * 1.6093;
    }

    public boolean inRadius(Coordinates destination, double radius) {
        return distance(destination) <= radius;
    }

    @Override
    public String toString() {
        return latitude + "," + longitude;
    }
}
