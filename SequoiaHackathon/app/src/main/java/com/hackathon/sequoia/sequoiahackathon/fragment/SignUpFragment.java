package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.RestWebService.RestClient;
import com.hackathon.sequoia.sequoiahackathon.activity.UserAccountActivity;
import com.hackathon.sequoia.sequoiahackathon.api.SignUpResponse;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;
import com.hackathon.sequoia.sequoiahackathon.global.Utilities;

import retrofit.Callback;
import retrofit.RetrofitError;


public class SignUpFragment extends Fragment {

    public static final String TAG = "com.hackathon.sequoia.sequoiahackathon.fragment.SIGNUP";
    public static final String SCREEN_NAME = "SIGNUP";

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    public static interface SignupFragmentInterface {
        void onSignupSuccessful();
    }

    private EditText mNameView;
    private EditText mEmailAddressView;
    private EditText mPhoneView;
    private EditText mPasswordView;


    private Button mCreateAccountButton;

    private SignupFragmentInterface mCallback;

    private View.OnClickListener mFragmentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.signup_btn) {
                signupUsingEmail();
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (SignupFragmentInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mNameView = (EditText) view.findViewById(R.id.signup_name);
        mEmailAddressView = (EditText) view.findViewById(R.id.signup_email);
        mPhoneView = (EditText) view.findViewById(R.id.signup_phone);
        mPasswordView = (EditText) view.findViewById(R.id.signup_password);

        mCreateAccountButton = (Button) view.findViewById(R.id.signup_btn);
        mCreateAccountButton.setOnClickListener(mFragmentClickListener);

        mNameView.requestFocus();
        return view;
    }

    private void signupUsingEmail() {
        final String name = mNameView.getText().toString().trim();
        final String email = mEmailAddressView.getText().toString();
        final String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(name.trim())) {
            showDialog("Name cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(email.trim())) {
            showDialog("Email cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(phone.trim())) {
            showDialog("Email cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password.trim())) {
            showDialog("Password cannot be empty");
            return;
        }

        ((UserAccountActivity) getActivity()).showProgressDialog("Creating Account...");
        RestClient client = RestClient.getInstance(getActivity().getApplicationContext());

        client.createAccount(name, email, phone, password , new Callback<SignUpResponse>() {
            @Override
            public void success(SignUpResponse signUpResponse, retrofit.client.Response response) {
                if (getActivity() == null || signUpResponse == null) {
                    return;
                }

                ((UserAccountActivity) getActivity()).hideDialog();
                int id = signUpResponse.getId();
                AppPreference.getInstance(getActivity().getApplicationContext()).setUserLoginId(id);
                AppPreference.getInstance(getActivity().getApplicationContext()).setUserLoginStatus(true);
                mCallback.onSignupSuccessful();
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() == null) {
                    return;
                }

                ((UserAccountActivity) getActivity()).hideDialog();

                showDialog("Error creating an account");
            }
        });
    }


    private void showDialog(String message) {
        ((UserAccountActivity) getActivity()).showMessageDialog(message);
    }
}
