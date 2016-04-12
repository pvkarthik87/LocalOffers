package com.kar.localoffers.api;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.kar.localoffers.OffersApplication;
import com.kar.localoffers.api.volleyutils.GsonRequest;
import com.kar.localoffers.api.volleyutils.VolleyRequestQueue;
import com.kar.localoffers.models.OfferRequest;
import com.kar.localoffers.models.OfferResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pvkarthik on 14/6/15.
 */
public class ApiRepo {

    private final static String SERVER_URL = ApiServer.BASE_URL;

    private final static String TAG = ApiRepo.class.getName();

    public enum API_Type
    {
        GET_OFFERS,
    }

    public final static String GET_OFFERS_API = "offers.json?";

    private final static String getAPIUrl(API_Type requestType, String appendUrl)
    {
        StringBuilder url = new StringBuilder(SERVER_URL);
        switch(requestType)
        {
            case GET_OFFERS:
            {
                url.append(GET_OFFERS_API);
            }
            break;

            default:
                break;
        }
        url.append(appendUrl);
        return url.toString();
    }

    // fetches offers from Fyber Offer wall api
    // At a time a page of offers will be fetched.
    public final static void getOffers(OfferRequest oReq, Response.Listener<OfferResponse> listener, Response.ErrorListener errorListener)
    {
        if(oReq == null || listener == null || errorListener == null)
            return;
        StringBuilder url = new StringBuilder(getAPIUrl(API_Type.GET_OFFERS, ""));
        Map<String, String> params = new LinkedHashMap<>(4);
        params.put("appid", oReq.appId);
        params.put("device_id", oReq.deviceId);
        params.put("ip", oReq.deviceIp);
        params.put("locale", oReq.deviceLocale);
        params.put("offer_types", "112");
        params.put("page", oReq.page);
        params.put("ps_time", oReq.userCreationTime);
        params.put("pub0", oReq.campaignName);
        params.put("timestamp", oReq.requestTimeStamp);
        params.put("uid", oReq.uId);

        if(params != null) {
            StringBuilder unencodedParams = new StringBuilder();
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    unencodedParams.append(key + "=" + params.get(key).toString()).append("&");
                }
            }
            unencodedParams.append(oReq.apiKey);
            String hash = "";
            try {
                hash = SHA1(unencodedParams.toString());
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if(TextUtils.isEmpty(hash))
                return;

            StringBuilder encodedParams = new StringBuilder();
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    encodedParams.append(key + "=" + URLEncoder.encode(params.get(key).toString())).append("&");
                }
            }
            encodedParams.append("hashkey=" + hash);
            url.append(encodedParams.toString());
        }


        GsonRequest<OfferResponse> req = new GsonRequest<>(url.toString(), OfferResponse.class, null, null, Request.Method.GET, listener, errorListener);
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        VolleyRequestQueue.getInstance(OffersApplication.context).addToRequestQueue(req);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    private static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

}
