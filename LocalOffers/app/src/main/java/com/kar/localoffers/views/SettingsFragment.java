package com.kar.localoffers.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.kar.localoffers.R;

import org.json.JSONArray;

/**
 * Created by Karthik on 1/13/2016.
 */
public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public interface SettingsListener {
        void onFindClicked(String uid, String apikey, String appId, String pub0);
    }

    private EditText inputUid, inputApikey, inputAppId, inputPub0;
    private TextInputLayout inputLayoutUid, inputLayoutApiKey, inputLayoutAppId, inputLayoutPub0;
    private Button btnFindOffers;
    private SettingsListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputLayoutUid = (TextInputLayout) view.findViewById(R.id.input_layout_uid);
        inputLayoutApiKey = (TextInputLayout) view.findViewById(R.id.input_layout_apikey);
        inputLayoutAppId = (TextInputLayout) view.findViewById(R.id.input_layout_appid);
        inputLayoutPub0 = (TextInputLayout) view.findViewById(R.id.input_layout_pub0);
        inputUid = (EditText) view.findViewById(R.id.input_uid);
        inputApikey = (EditText) view.findViewById(R.id.input_apikey);
        inputAppId = (EditText) view.findViewById(R.id.input_appid);
        inputPub0 = (EditText) view.findViewById(R.id.input_pub0);
        btnFindOffers = (Button) view.findViewById(R.id.btn_getoffers);

        btnFindOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    public void setListener(SettingsListener listener) {
        mListener = listener;
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateField(inputUid, inputLayoutUid, R.string.err_msg_uid)) {
            return;
        }

        if (!validateField(inputApikey, inputLayoutApiKey, R.string.err_msg_api_key)) {
            return;
        }

        if (!validateField(inputAppId, inputLayoutAppId, R.string.err_msg_app_id)) {
            return;
        }

        if (!validateField(inputPub0, inputLayoutPub0, R.string.err_msg_pub0)) {
            return;
        }

        if (mListener != null) {
            mListener.onFindClicked(inputUid.getText().toString(), inputApikey.getText().toString(), inputAppId.getText().toString(), inputPub0.getText().toString());
        }
    }

    private boolean validateField(EditText editView, TextInputLayout ipLyt, int errorRsc) {
        if (editView.getText().toString().trim().isEmpty()) {
            ipLyt.setError(getString(errorRsc));
            requestFocus(editView);
            return false;
        } else {
            ipLyt.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
