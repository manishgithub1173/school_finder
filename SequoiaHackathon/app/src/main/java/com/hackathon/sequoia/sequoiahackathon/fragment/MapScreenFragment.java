package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.RestWebService.RestClient;
import com.hackathon.sequoia.sequoiahackathon.activity.UserAccountActivity;
import com.hackathon.sequoia.sequoiahackathon.api.NearBySchoolData;
import com.hackathon.sequoia.sequoiahackathon.api.NearbySchoolResponse;
import com.hackathon.sequoia.sequoiahackathon.api.School;
import com.hackathon.sequoia.sequoiahackathon.api.SignUpResponse;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;

public class MapScreenFragment extends Fragment implements GoogleMap.OnCameraChangeListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener{

    private OnFragmentInteraction mCallback;

    public interface OnFragmentInteraction {
        public void onMarkerClick(int id);
    }

    private GoogleMap mMapView;
    private SupportMapFragment map;
    private GoogleApiClient mGoogleApiClient;

    private HashMap<String, Integer> markerList = new HashMap<>();
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setSmallestDisplacement(100);

    public static MapScreenFragment newInstance() {
        MapScreenFragment fragment = new MapScreenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MapScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        initGoogleApiClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_map_screen, container, false);

        FragmentManager fm = getChildFragmentManager();
        map = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if(map != null) {
            mMapView = map.getMap();
        }
        mMapView.setOnCameraChangeListener(this);
        mMapView.setMyLocationEnabled(true);
        mMapView.getUiSettings().setMyLocationButtonEnabled(false);
        mMapView.getUiSettings().setZoomControlsEnabled(false);

        return rootView;
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnFragmentInteraction)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                REQUEST,
                this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        showMapLocation(location.getLatitude(), location.getLongitude());
        fetchResult(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    private void showMapLocation(double pickupLatitude, double pickupLongitude) {
        int zoom = 14;
        try {
            LatLng location = new LatLng(pickupLatitude, pickupLongitude);
            CameraUpdate center1 = CameraUpdateFactory.newLatLngZoom(location,
                    (float) zoom);

            if (mMapView != null) {
                mMapView.animateCamera(center1);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private final void addSchoolLocationsToMap(final ArrayList<NearBySchoolData> schoolList) {
        int i = 0;
        for (NearBySchoolData school : schoolList) {
            LatLng l = new LatLng(Double.valueOf(school.getLocation().getLatitude()),
                    Double.valueOf(school.getLocation().getLongitude()));

            MarkerOptions marker = new MarkerOptions()
                    .position(l)
                    .title(school.getSchool().getName())
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED));
            Marker markerObj = mMapView.addMarker(marker);
            markerList.put(markerObj.getId(), i);
            i++;
        }

        mMapView.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {

                try {
                    String id = marker.getId();
                    School school = schoolList.get(markerList.get(id)).getSchool();
                    //go to school details
                    mCallback.onMarkerClick(schoolList.get(markerList.get(id)).getSchool().getId());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void fetchResult(double lat, double lng) {

        RestClient client = RestClient.getInstance(
                getActivity().getApplicationContext());

        client.getNearbySchool(lat, lng, new Callback<NearbySchoolResponse>() {
            @Override
            public void success(NearbySchoolResponse nearbySchoolResponse, retrofit.client.Response response) {
                if (getActivity() == null || nearbySchoolResponse == null) {
                    return;
                }
                ArrayList<NearBySchoolData> data = (ArrayList) nearbySchoolResponse.getData();
                addSchoolLocationsToMap(data);
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() == null) {
                    return;
                }

                Toast.makeText(getActivity(), "Error in fetching results", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
