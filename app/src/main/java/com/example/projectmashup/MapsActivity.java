package com.example.projectmashup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class MapsActivity extends FragmentActivity implements  OnMapReadyCallback {

    private GoogleMap mMap;
    private String txtaBuscar="";
    private Metricas metricas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        metricas = Metricas.createInstance(this);
        Bundle bundle = getIntent().getExtras();
        txtaBuscar = bundle.getString("textoAbuscar");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       //metricas.perceivedLatencyBegin();
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //metricas.perceivedLatencyStop("googlemap");
        mMap = googleMap;
        ClienteWebService1TaskConsultar cws1 = new ClienteWebService1TaskConsultar(mMap);
        cws1.execute();
        ClienteWebService2TaskConsultar cws2 = new ClienteWebService2TaskConsultar(mMap);
        cws2.execute();
    }




//******************************************************************************************************************
    private class ClienteWebService1TaskConsultar extends AsyncTask<String, Integer, JSONArray> {

        private GoogleMap mapa = null;

        public ClienteWebService1TaskConsultar(GoogleMap m) {
            mapa = m;
        }

        protected  JSONArray doInBackground(String... params) {
            // Create URL
            URL myURL = null;
            JSONArray jsonArr = null;
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                myURL = new URL("https://api.tomtom.com/search/2/search/"+txtaBuscar+".json?key=EIihl7C9hbrQgSut3zOSkRWtJar7U3hp&lat=-51.6333&lon=-69.2167&radius=10000");
                metricas.getConnectionType("tomtom");
                metricas.getPacketLoss("1.1.1.1", "tomtom");
                metricas.getLatency("1.1.1.1", "tomtom");
                metricas.perceivedLatencyBegin();
                HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                metricas.perceivedLatencyStop("tomtom");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                //Log.d("APIREST-----",response.toString());
                JSONObject json = new JSONObject(response.toString());
                metricas.getCountResultJSON(json, "numResults", "tomtom");
                metricas.getSizeJSON(json, "tomtom");
                jsonArr = json.getJSONArray("results");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArr;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArr) {
            super.onPostExecute(jsonArr);
            //Log.d("APIREST-----",jsonArr.toString());
            try {
                if ( jsonArr != null ) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        if (jsonObj.has("poi")) {
                            JSONObject jsonPoi = jsonObj.getJSONObject("poi");
                            String name = jsonPoi.getString("name");
                            JSONObject jsonPosition = jsonObj.getJSONObject("position");
                            Double lat = jsonPosition.getDouble("lat");
                            Double lon = jsonPosition.getDouble("lon");
                            //Log.d("APIREST-----",name);
                            //Log.d("APIREST-----",String.valueOf(lat));
                            //Log.d("APIREST-----",String.valueOf(lon))
                            LatLng p = new LatLng(lat, lon);
                            mapa.addMarker(new MarkerOptions().position(p).title(name));
                        }
                    }
                }
                // Add a marker in Rio Gallegos and move the camera
                LatLng rg = new LatLng(-51.6333,  -69.2167);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rg,15));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


//******************************************************************************************************************
    private class ClienteWebService2TaskConsultar extends AsyncTask<String, Integer, JSONArray> {

        private GoogleMap mapa = null;

        public ClienteWebService2TaskConsultar(GoogleMap m) {
            mapa = m;
        }

        protected  JSONArray doInBackground(String... params) {
            // Create URL
            URL myURL = null;
            JSONArray jsonArrItems = null;
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                myURL = new URL("https://api.foursquare.com/v2/venues/explore?client_id=RCGSLLDHDWQT00EU1H1QKQAXIYKIAH4OLWZJ1ZTXJYZIOSGJ&client_secret=ODYKP0XIL3YWIL25GKFYEJE3OUKIDLOSNAQQJPU011OB4K5X&v=20180323&ll=-51.6333,-69.2167&query="+txtaBuscar);
                metricas.getConnectionType("foursquare");
                metricas.getPacketLoss("1.1.1.1", "foursquare");
                metricas.getLatency("1.1.1.1", "foursquare");
                metricas.perceivedLatencyBegin();
                HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                metricas.perceivedLatencyStop("foursquare");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                //Log.d("APIREST-----",response.toString());
                JSONObject json = new JSONObject(response.toString());
                metricas.getCountResultJSON(json, "totalResults", "foursquare");
                metricas.getSizeJSON(json, "foursquare");
                JSONObject jsonResponse = json.getJSONObject("response");
                JSONArray jsonArrGroups = jsonResponse.getJSONArray("groups");
                JSONObject jsonObjCero = jsonArrGroups.getJSONObject(0);
                //Log.d("APIREST-name", jsonObjCero.getString("name"));
                jsonArrItems = jsonObjCero.getJSONArray("items");
                //Log.d("APIREST-longitud", Integer.toString(jsonArrItems.length()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonArrItems;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArr) {
            super.onPostExecute(jsonArr);
            try {
                if ( jsonArr != null ) {
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        if (jsonObj.has("venue")) {
                            JSONObject jsonVenue = jsonObj.getJSONObject("venue");
                            String name = jsonVenue.getString("name");
                            JSONObject jsonLocation = jsonVenue.getJSONObject("location");
                            Double lat = jsonLocation.getDouble("lat");
                            Double lng = jsonLocation.getDouble("lng");
                            Log.d("APIREST-----", name);
                            //Log.d("APIREST-----",String.valueOf(lat));
                            //Log.d("APIREST-----",String.valueOf(lon))
                            LatLng p = new LatLng(lat, lng);
                            mapa.addMarker(new MarkerOptions().position(p).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        }
                    }
                }
                // Add a marker in Rio Gallegos and move the camera
                LatLng rg = new LatLng(-51.6333,  -69.2167);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rg,13));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
