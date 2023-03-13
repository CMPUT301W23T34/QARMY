
/*
 * Map Fragment
 *
 * Version: 1.0
 *
 * Date: 2023-03-09
 *
 * Copyright 2023 CMPUT301W23T34
 *
 * Sources:
 * -Mark Kurtz, Manuel Stahl, 2022-11-25, https://github.com/osmdroid/osmdroid/blob/master/OpenStreetMapViewer/src/main/java/org/osmdroid/StarterMapFragment.java, osmdroid
 * -ycuicui, 2017-11-22, https://github.com/osmdroid/osmdroid/issues/771
 */



package com.example.QArmy.UI;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Displays map
 * @author Japkirat Kaur
 * @version 1.0
 */

public class MapFragment extends Fragment {

    private MapView mapView;
    private IMapController mapController;
    private MyLocationNewOverlay locationOverlay;
    private ActivityResultLauncher<String[]> locationPermissionRequest;

    public MapFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates the MapView
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return mapView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapView = new MapView(inflater.getContext());
        mapView.setDestroyMode(false);
        mapView.setVisibility(View.INVISIBLE);
        mapView.setId(R.id.map);

        Context context = getActivity().getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (coarseLocationGranted != null && coarseLocationGranted && fineLocationGranted != null && fineLocationGranted)  {
                                mapView.setVisibility(View.VISIBLE);
                            }
                        }
                );
        mapController = mapView.getController();

        return mapView;
    }

    /**
     * Adds overlay to mapView
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getActivity();
        locationPermissionRequest.launch(new String[] {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
        });

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),mapView);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        locationOverlay.setDrawAccuracyEnabled(true);
        mapView.getOverlays().add(locationOverlay);

        mapView.setMultiTouchControls(true);
        mapView.setTilesScaledToDpi(true);

        mapController.setZoom(10.5);
        mapController.setCenter(locationOverlay.getMyLocation());

        mapView.setOnTouchListener((v, event) -> {
            if( 100 * event.getX() < 85 * v.getWidth()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                v.performClick();
                return false;
            }
            return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapController.setCenter(locationOverlay.getMyLocation());
        mapView.onResume();
        checkPermissions(getActivity());
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDetach();
    }

    private void checkPermissions(Context context) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Enable Location Services to view Map",Toast.LENGTH_LONG).show();
        } else {
            mapView.setVisibility(View.VISIBLE);
        }
    }

}
