package com.kar.localoffers.views.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.kar.localoffers.R;
import com.kar.localoffers.api.ApiRepo;
import com.kar.localoffers.api.volleyutils.VolleyRequestQueue;
import com.kar.localoffers.models.Offer;
import com.kar.localoffers.models.OfferRequest;
import com.kar.localoffers.models.OfferResponse;
import com.kar.localoffers.utils.Utills;
import com.kar.localoffers.views.util.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pvkarthik on 22/01/16.
 */
public class OfferListAdapter extends BaseAdapter<OfferListAdapter.OfferListItemViewHolder> {

    private List<Offer> mOfferList;
    private Context mContext;
    private String mGAdId;
    private boolean mIsGoogleAdFetchDone;
    private int mPageNumber = 1;
    private OfferRequest mOfferRequest;

    public OfferListAdapter(Context context){
        mContext = context;
        mOfferList = new ArrayList<>(4);
    }

    @Override
    public OfferListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_offer_listitem, parent, false);
        OfferListItemViewHolder deviceItemView = new OfferListItemViewHolder(view);
        return deviceItemView;
    }

    @Override
    public void onBindViewHolder(OfferListItemViewHolder viewHolder, int position) {
        if(position < mOfferList.size()) {
            Offer offer = mOfferList.get(position);
            viewHolder.mOfferImg.setImageUrl(offer.getThumbnail().getHires(), VolleyRequestQueue.getInstance(mContext).getImageLoader());
            viewHolder.mOfferTitle.setText(offer.getTitle());
            viewHolder.mOfferAmt.setText("" + offer.getPayout());
            viewHolder.mOfferTeaser.setText(offer.getTeaser());
            viewHolder.mOfferAction1.setText(offer.getOfferTypes().get(0).getReadable());
            if(offer.getOfferTypes().size() > 1) {
                viewHolder.mOfferAction2.setText(offer.getOfferTypes().get(1).getReadable());
                viewHolder.mOfferAction2.setVisibility(View.VISIBLE);
            }
            else {
                viewHolder.mOfferAction2.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mOfferList.size();
    }

    public void setEmptyList() {
        setLoading(false);
        updateEmptyView();
        notifyDataSetChanged();
    }

    public void onLoadFinished() {
        setLoading(false);
        updateEmptyView();
    }

    public void onLoadFailed() {
        setLoading(false);
        updateEmptyView();
    }

    public void loadOffers(OfferRequest request) {
        mOfferRequest = request;
        if (mOfferList.size() == 0) {
            setLoading(true);
        }
        if(!mIsGoogleAdFetchDone) {
            fetchGoogleAdId();
        }
        else {
            getOffersFromServer();
        }
    }

    private void fetchGoogleAdId() {
        new GoogleAdIdFetchTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class GoogleAdIdFetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return Utills.getGoogleAdId(mContext);
        }

        @Override
        protected void onPostExecute(String result) {
            mGAdId = result;
            mIsGoogleAdFetchDone = true;
            loadOffers(mOfferRequest);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private void getOffersFromServer() {
        //mOfferRequest.appId = "2070";
        mOfferRequest.deviceId = mGAdId;
        mOfferRequest.deviceIp = "109.235.143.113";
        mOfferRequest.deviceLocale = "DE";
        mOfferRequest.page = "" + mPageNumber;
        mOfferRequest.userCreationTime = ""+(System.currentTimeMillis()/1000);
        //mOfferRequest.campaignName = "campaign2";
        mOfferRequest.requestTimeStamp = ""+(System.currentTimeMillis()/1000);
        //mOfferRequest.uId = "spiderman";
        //mOfferRequest.apiKey = "1c915e3b5d42d05136185030892fbb846c278927";
        ApiRepo.getOffers(mOfferRequest, new Response.Listener<OfferResponse>() {
            @Override
            public void onResponse(OfferResponse response) {
                handleGetDevicesResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoadFailed();
            }
        });
    }

    @Override
    public boolean isEmptyList() {
        return mOfferList.isEmpty();
    }

    private void handleGetDevicesResponse(OfferResponse response) {
        if (response != null) {
            mPageNumber++;
            if(response.getCount() != null && response.getCount() > 0) {
                mOfferList.addAll(response.getOffers());
            }
            if (mOfferList.size() > 0) {
                onLoadFinished();
                if(response.getCount() != null && response.getCount() > 0) {
                    notifyItemRangeInserted(mOfferList.size() - response.getCount(), response.getCount());
                }
            }
            else {
                setEmptyList();
                onLoadFinished();
            }
        }
        else {
            onLoadFailed();
        }
    }

    public static class OfferListItemViewHolder extends RecyclerView.ViewHolder {

        private NetworkImageView mOfferImg;
        private TextView mOfferTitle;
        private Button mOfferAmt;
        private TextView mOfferTeaser;
        private TextView mOfferAction1;
        private TextView mOfferAction2;

        public OfferListItemViewHolder(View itemView) {
            super(itemView);
            mOfferImg = (NetworkImageView) itemView.findViewById(R.id.offerImg);
            mOfferTitle = (TextView)itemView.findViewById(R.id.offerTitle);
            mOfferAmt = (Button)itemView.findViewById(R.id.offerAmt);
            mOfferTeaser = (TextView)itemView.findViewById(R.id.offerTeaser);
            mOfferAction1 = (TextView)itemView.findViewById(R.id.offer1);
            mOfferAction2 = (TextView)itemView.findViewById(R.id.offer2);
        }

    }
}
