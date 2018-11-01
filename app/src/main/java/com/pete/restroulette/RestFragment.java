package com.pete.restroulette;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pete on 12/6/2017.
 */

public class RestFragment extends Fragment {

    private EditText mSearchText;
    private Button mShootButton;
    private SearchResults mSearchResults;
    private SupportMapFragment mapFragment;
    public static RecyclerView mRestRecyclerView;
    private List<SearchResults> mItems = new ArrayList<>();

    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 15;

    public static RestFragment newInstance(){
        return new RestFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_search, container, false);

        mSearchText = (EditText) v.findViewById((R.id.search_text));
        //mSearchText.setText(mSearchTerms.getZipCode());
        mSearchText.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after){

            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count){
                //mSearchTerms.setZipCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s){

            }

        });

        mShootButton = (Button) v.findViewById(R.id.shoot_button);

        mShootButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //FragmentManager manager = getFragmentManager();
                //updateItems();
                Intent i = ResultsActivity
                        .newIntent(getActivity());
                startActivity(i);

            }
        });

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng latLng = new LatLng(39.905919,-75.013937);
                    googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title("Cherry Hill"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            });
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return v;
    }



}
