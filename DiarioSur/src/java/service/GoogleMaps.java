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
    private static final String API = "https://maps.google.com/maps/api/geocode/json?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&address=";

    public static GoogleMaps getInstance() {
        if (instance == null)
            instance = new GoogleMaps();
        return instance;
    }

    private GoogleMaps() {
        client = ClientBuilder.newClient();
    }

    public Coordinates address2Coords(String address) throws ClientErrorException {
        webTarget = client.target(API + address);
        String response = webTarget.request(MediaType.APPLICATION_JSON).get(String.class);
        JsonObject loc = new JsonParser().parse(response).getAsJsonObject().getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");

        return new Coordinates(loc.get("lat").getAsDouble(), loc.get("lng").getAsDouble());
    }

    public void close() {
        client.close();
    }
}
