package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import util.Coordinates;

public class GoogleMaps {
    private static GoogleMaps instance;

    private WebTarget webTarget;
    private Client client;
    private static final String API_GEOCODE = "https://maps.google.com/maps/api/geocode/json?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&address=%ADDRESS";
    private static final String API_DISTANCEMATRIX = "https://maps.googleapis.com/maps/api/distancematrix/json?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&origins=%ORIGIN&destinations=%DESTINATION";

    public static GoogleMaps getInstance() {
        if (instance == null)
            instance = new GoogleMaps();
        return instance;
    }

    private GoogleMaps() {
        client = ClientBuilder.newClient();
    }

    public Coordinates address2Coords(String address) throws ClientErrorException {
        webTarget = (WebTarget) client.target(API_GEOCODE.replaceAll("(%ADDRESS)", address.replaceAll("(\\s+)", "+")));
        String response = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        JsonObject loc = (new JsonParser()).parse(response).getAsJsonObject().getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");

        return new Coordinates(loc.get("lat").getAsDouble(), loc.get("lng").getAsDouble());
    }

    public String time(String origin, String destination) throws ClientErrorException {
        webTarget = (WebTarget) client.target(API_DISTANCEMATRIX
            .replaceAll("(%ORIGIN)", origin.replaceAll("(\\s+)", "+"))
            .replaceAll("(%DESTINATION)", destination.replaceAll("(\\s+)", "+"))
        );

        String response = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        return (new JsonParser()).parse(response).getAsJsonObject().getAsJsonArray("rows").get(0).getAsJsonObject().getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("duration").get("text").getAsString();
    }

    public String time(Coordinates origin, String destination) throws ClientErrorException {
        return time(origin.toString(), destination);
    }

    public String time(String origin, Coordinates destination) throws ClientErrorException {
        return time(origin, destination.toString());
    }

    public String time(Coordinates origin, Coordinates destination) throws ClientErrorException {
        return time(origin.toString(), destination.toString());
    }

    public double distance(String origin, String destination) throws ClientErrorException {
        webTarget = (WebTarget) client.target(API_DISTANCEMATRIX
            .replaceAll("(%ORIGIN)", origin.replaceAll("(\\s+)", "+"))
            .replaceAll("(%DESTINATION)", destination.replaceAll("(\\s+)", "+"))
        );

        String response = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        return (new JsonParser()).parse(response).getAsJsonObject().getAsJsonArray("rows").get(0).getAsJsonObject().getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("distance").get("value").getAsDouble();
    }

    public double distance(Coordinates origin, String destination) throws ClientErrorException {
        return distance(origin.toString(), destination);
    }

    public double distance(String origin, Coordinates destination) throws ClientErrorException {
        return distance(origin, destination.toString());
    }

    public double distance(Coordinates origin, Coordinates destination) throws ClientErrorException {
        return distance(origin.toString(), destination.toString());
    }

    public void close() {
        client.close();
    }
}
