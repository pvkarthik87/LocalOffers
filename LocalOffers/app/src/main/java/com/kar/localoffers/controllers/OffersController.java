package com.kar.localoffers.controllers;

import android.support.v4.app.FragmentActivity;

import com.kar.localoffers.R;
import com.kar.localoffers.views.OffersFragment;
import com.kar.localoffers.views.SettingsFragment;

/**
 * Created by Karthik on 4/9/2016.
 */
public class OffersController implements SettingsFragment.SettingsListener {

    private FragmentActivity mActivity;
    private OffersFragment mOffersFragment;
    private SettingsFragment mSettingsFragment;

    public OffersController(FragmentActivity activity) {
        mActivity = activity;
    }

    public void initFragments() {
        mSettingsFragment = SettingsFragment.newInstance();
        mSettingsFragment.setListener(this);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mSettingsFragment, "offers")
                .commit();
    }

    @Override
    public void onFindClicked(String uid, String apikey, String appId, String pub0) {
        mOffersFragment = OffersFragment.newInstance(uid, apikey, appId, pub0);
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mOffersFragment, "offers")
                .addToBackStack("OFFERS")
                .commit();
    }
}
