package com.pete.restroulette;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Pete on 3/22/2018.
 */

public class MapFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Intent intent = MapsActivity.newIntent(getActivity());
        startActivity(intent);
    }

}
