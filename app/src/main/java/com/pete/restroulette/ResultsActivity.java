package com.pete.restroulette;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by Pete on 4/9/2018.
 */

public class ResultsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, ResultsActivity.class);
    }

    @Override
    protected Fragment createFragment(){
        return ResultFragment.newInstance();
    }
}

