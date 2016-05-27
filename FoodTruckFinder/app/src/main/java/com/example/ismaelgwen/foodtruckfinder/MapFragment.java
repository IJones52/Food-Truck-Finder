package com.example.ismaelgwen.foodtruckfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.twitter.sdk.android.core.internal.TwitterCollection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MapView mapView;

    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;
    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor


    }


    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                Location l = new Location("place");
                locationListenerNetwork.onLocationChanged(l);

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitudeNetwork, longitudeNetwork))
                        .title("You are here"));
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(47.6908946, -122.3657819))
                    .title("Chuck's Hop Shop"));
             /*/  mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(47.6666891, -122.3711647))
                        .title("Stoup Brewing"));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(47.6638753, -122.377054))
                        .title("Peddler Brewing"));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(47.6645364, -122.3702458))
*/
            }
        });
        return view;

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    // Activity lifecycle calls
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
   public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}

        private void showAlert() {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("Enable Location")
                    .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                            "use this app")
                    .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    });
            dialog.show();
        }
    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };
    //for selecting map markers as current positions, needs to be modified
  /*  public void placeGTMarker() {
        alMarkerGT = new ArrayList<Marker>();
        marker = new Marker("my Marker", "", latLng);
        marker.setMarker(activity.getResources()
                .getDrawable(R.drawable.map_pin));
        mv.addMarker(marker);
        alMarkerGT.add(marker);
        itemizedIconOverlayGT = new ItemizedIconOverlay(activity, alMarkerGT,
                new OnItemGestureListener<Marker>() {

                    @Override
                    public boolean onItemSingleTapUp(int index, Marker item) {
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int index, Marker item) {
                        return false;
                    }
                });
        mv.addItemizedOverlay(itemizedIconOverlayGT);
    }*/

class ReadCoordinatesFromURLTask extends AsyncTask<Void, Void, List<LatLng>> {
    @Override

    protected List<LatLng> doInBackground(Void... voids) {

        ArrayList<LatLng> points = new ArrayList<>();

        try {

            // Parse JSON
            JSONObject json =  MapFragment.readJsonFromUrl("placeholder");
            JSONArray features = json.getJSONArray("features");
            JSONObject feature = features.getJSONObject(0);
            JSONObject geometry = feature.getJSONObject("geometry");
            if (geometry != null) {
                String type = geometry.getString("type");


                if (!TextUtils.isEmpty(type) && type.equalsIgnoreCase("LineString")) {

                    // Get the Coordinates
                    JSONArray coords = geometry.getJSONArray("coordinates");
                    for (int lc = 0; lc < coords.length(); lc++) {
                        JSONArray coord = coords.getJSONArray(lc);
                        LatLng latLng = new LatLng(coord.getDouble(1), coord.getDouble(0));
                        points.add(latLng);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("OK", "Exception Loading GeoJSON: " + e.toString());
        }

        return points;
    }

    @Override
    protected void onPostExecute(List<LatLng> points) {
        super.onPostExecute(points);

        if (points.size() > 0) {
            final LatLng[] pointsArray = points.toArray(new LatLng[points.size()]);




                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(MapboxMap mapboxMap) {
                        for(int i = 0; i<pointsArray.length; i++)
                        {
                            mapboxMap.addMarker(new MarkerOptions()
                            .position(pointsArray[i]));


                    }}
                });

        }
    }
}
}