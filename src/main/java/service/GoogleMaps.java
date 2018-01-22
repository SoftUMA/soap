package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
    //    jclient = JerseyClientFactory.clientBuilder().build();
    }

    public Coordinates address2Coords(String address) throws ClientErrorException {//la llamada a las apis en google se hace sin espacio en su lugar se utilizan +
    	String requestURL = API_GEOCODE.replaceAll("(%ADDRESS)", address.replaceAll("(\\s+)", "+"));
    	//webTarget = (WebTarget) client.target(requestURL);
//        webTarget = (WebTarget) jclient.target(API_GEOCODE.replaceAll("(%ADDRESS)", address.replaceAll("(\\s+)", "+")));
     
    	String response;
		try {
			response = getRemoteContents(requestURL);
	        JsonObject loc = (new JsonParser()).parse(response).getAsJsonObject().getAsJsonArray("results").get(0).getAsJsonObject().getAsJsonObject("geometry").getAsJsonObject("location");
	        return new Coordinates(loc.get("lat").getAsDouble(), loc.get("lng").getAsDouble());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return new Coordinates(36.7159668, -4.4770454);
    }

    public String time(String origin, String destination) throws ClientErrorException {
        webTarget = (WebTarget) client.target(API_DISTANCEMATRIX
//        webTarget = (WebTarget) jclient.target(API_DISTANCEMATRIX

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
    
    public String getRemoteContents(String url) throws Exception {
        URL urlObject = new URL(url);
        URLConnection conn = urlObject.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine, output = "";
        while ((inputLine = in.readLine()) != null) {
             output += inputLine;
        }   
        in.close();
            
        return output;
    }

    public double distance(String origin, String destination) throws ClientErrorException {
    	String requestURL = API_DISTANCEMATRIX
                .replaceAll("(%ORIGIN)", origin.replaceAll("(\\s+)", "+"))
                .replaceAll("(%DESTINATION)", destination.replaceAll("(\\s+)", "+"));
    	
        //webTarget = (WebTarget) client.target(requestURL);
    	
     /*   webTarget = (WebTarget) jclient.target(API_DISTANCEMATRIX
                .replaceAll("(%ORIGIN)", origin.replaceAll("(\\s+)", "+"))
                .replaceAll("(%DESTINATION)", destination.replaceAll("(\\s+)", "+"))
            );*/

        String response;
		try {
			response = getRemoteContents(requestURL);
	        return (new JsonParser()).parse(response).getAsJsonObject().getAsJsonArray("rows").get(0).getAsJsonObject().getAsJsonArray("elements").get(0).getAsJsonObject().getAsJsonObject("distance").get("value").getAsDouble();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
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
