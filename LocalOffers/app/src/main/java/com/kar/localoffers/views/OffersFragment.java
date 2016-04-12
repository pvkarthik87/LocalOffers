package com.kar.localoffers.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kar.localoffers.R;
import com.kar.localoffers.models.OfferRequest;
import com.kar.localoffers.views.adapters.OfferListAdapter;
import com.kar.localoffers.views.util.DividerItemDecoration;
import com.kar.localoffers.views.util.RecyclerItemClickListener;

import java.util.UUID;

/**
 * Created by Karthik on 4/9/2016.
 * Basic settings input fragment which gives user options to enter appid, uid, apikey, pub0
 */
public class OffersFragment extends Fragment implements RecyclerItemClickListener.OnRecyclerItemClickListener {

    private static final String UID = "UID";
    private static final String API_KEY = "API_KEY";
    private static final String APP_ID = "APP_ID";
    private static final String PUB0 = "PUB0";

    public static OffersFragment newInstance(String uid, String apikey, String appId, String pub0) {
        Bundle bundle = new Bundle();
        bundle.putString(UID, uid);
        bundle.putString(API_KEY, apikey);
        bundle.putString(APP_ID, appId);
        bundle.putString(PUB0, pub0);
        OffersFragment fragment = new OffersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mDeviceList;
    private LinearLayoutManager mLayoutManager;
    private OfferListAdapter mAdapter;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    private OfferRequest mOfferRequest = new OfferRequest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mOfferRequest.uId = bundle.getString(UID);
            mOfferRequest.apiKey = bundle.getString(API_KEY);
            mOfferRequest.appId = bundle.getString(APP_ID);
            mOfferRequest.campaignName = bundle.getString(PUB0);
        }
        setUpAdapter(view);
    }

    private void setUpAdapter(View view) {
        mDeviceList = (RecyclerView) view.findViewById(R.id.offer_list);

        mAdapter = new OfferListAdapter(getActivity());
        mAdapter.setEmptyView(view.findViewById(R.id.body_search_empty));
        mAdapter.setLoadingView(view.findViewById(R.id.body_loading_bar));
        mAdapter.setLoadingFailedView(view.findViewById(R.id.loading_failed));
        mDeviceList.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mDeviceList.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mDeviceList.setLayoutManager(mLayoutManager);

        // Offer click listener not used for now
        mDeviceList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mDeviceList, this));

        // more offers loading functionality.
        // Requests for next page of offers when user scrolls till last offer at present in list.
        mDeviceList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mDeviceList.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    loading = true;
                    mAdapter.loadOffers(mOfferRequest);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshOffers();
    }

    public void refreshOffers() {
        mAdapter.loadOffers(mOfferRequest);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
