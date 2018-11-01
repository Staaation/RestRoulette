package com.pete.restroulette;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Pete on 2/26/2018.
 */

public class RestActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, RestActivity.class);
    }

    @Override
    protected Fragment createFragment(){
        return RestFragment.newInstance();
    }
}
