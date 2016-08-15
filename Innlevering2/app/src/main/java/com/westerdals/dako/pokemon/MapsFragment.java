package com.westerdals.dako.pokemon;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.westerdals.dako.pokemon.db.PokemonDataSource;
import com.westerdals.dako.pokemon.http.DataHandler;
import com.westerdals.dako.pokemon.http.ResponseWrapper;
import com.westerdals.dako.pokemon.http.ResultsListener;
import com.westerdals.dako.pokemon.model.Location;
import com.westerdals.dako.pokemon.model.Pokemon;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.*;

public class MapsFragment
        extends Fragment
        implements OnMapReadyCallback, ResultsListener, OnRequestPermissionsResultCallback{
    private View view;
    private MapView mapView;
    private GoogleMap gMap;
    private Set<String> catchedPokemons = new HashSet<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        // Collect pokemon data from DB
        getPokemons();

        // Add objects to map when loaded
        mapView.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        // Save variable
        gMap = map;

        // Check Permissions and enable own position
        if (ContextCompat.checkSelfPermission
                (this.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission
                (this.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            checkPermissionsForAndroidM();
        else
            enableLocation();

        // Download data and add markers to map
        downloadLocationsData();
    }


    @Override
    public void onResultsSucceeded(ResponseWrapper response) {
        handleResponse(response);
    }


    private void getPokemons() {
        PokemonDataSource dataSource = new PokemonDataSource(view.getContext());

        // Get catched pokemons from db
        dataSource.open();
        List<Pokemon> pokemons = dataSource.getPokemons();
        dataSource.close();

        // Optimization
        // Reads list once and put them in hashset for
        // quick comparison when adding marker
        for (Pokemon p : pokemons) {
            catchedPokemons.add(p.getName());
        }
    }


    private void addMapMarkers(List<Location> locations) {
        //Add Markers
        for (Location l : locations) {
            addMarker(l);
        }

        // Center camera to first marker
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                locations.get(0).getLatitude(),
                locations.get(0).getLongitude()), 13));
    }


    public void addMarker(Location l) {
        MarkerOptions marker = new MarkerOptions()
                .position(new LatLng(l.getLatitude(), l.getLongitude()))
                .title(l.getName())
                .snippet(l.getHint())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Set blue marker on catched pokemons
        if (catchedPokemons != null) {
            if (catchedPokemons.contains(l.getName())) {
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                marker.snippet("Catched!");
            }
        }
        gMap.addMarker(marker);
    }


    private void handleResponse(ResponseWrapper response) {
        if (response.responseCode == 200) {
            addMapMarkers(inputStreamToLocation(response.response));
        } else {
            displayToast("Couldn't download pokemon locations");
        }
    }


    private void displayToast(String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    private void downloadLocationsData() {
        // Retrieve pokemon locations
        DataHandler dataHandler = new DataHandler(view.getContext());
        dataHandler.setOnResultsListener(this);
        dataHandler.setTargetURL(getString(R.string.API_URI_LOCATIONS));
        dataHandler.setProgressMessage("Downloading pokemon locations...");
        dataHandler.execute();
    }


    private List<Location> inputStreamToLocation(InputStream inputStream) {
        Scanner in = new Scanner(inputStream);
        Gson gson = new Gson();

        StringBuilder builder = new StringBuilder();
        while (in.hasNextLine()) {
            builder.append(in.nextLine());
        }

        Type listType = new TypeToken<List<Location>>() {}.getType();
        List<Location> result = gson.fromJson(builder.toString(), listType);

        return result;
    }


    private void checkPermissionsForAndroidM() {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                showMessageOKCancel("Allow to see your location on the map",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
            return;
        }
        enableLocation();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    enableLocation();
                } else {
                    // Permission Denied
                    displayToast("Locations denied");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @SuppressWarnings("MissingPermission") //Already checked
    private void enableLocation() {
        gMap.setMyLocationEnabled(true);
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this.getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
