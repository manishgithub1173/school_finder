package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.RestWebService.RestClient;
import com.hackathon.sequoia.sequoiahackathon.activity.UserAccountActivity;
import com.hackathon.sequoia.sequoiahackathon.api.SignUpResponse;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;

import org.w3c.dom.Text;

import retrofit.Callback;
import retrofit.RetrofitError;


public class LoginFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public interface LoginFragmentInterface {
        void onLoginSuccessful();
        void onSignupClicked();
    }

    private LoginFragmentInterface mCallback;

    private EditText mEmailAddressView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private TextView mSignupTextview;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmailAddressView = (EditText) view.findViewById(R.id.login_email);
        mPasswordView = (EditText) view.findViewById(R.id.login_password);
        mLoginButton = (Button) view.findViewById(R.id.login_button);
        mSignupTextview = (TextView)view.findViewById(R.id.link_signup);


        mLoginButton.setOnClickListener(mFragmentClickListener);
        mSignupTextview.setOnClickListener(mFragmentClickListener);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (LoginFragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    private View.OnClickListener mFragmentClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.login_button) {
                emailLogin();
            }

            else if (v.getId() == R.id.link_signup) {
                mCallback.onSignupClicked();
            }
        }
    };

    private void showDialog(String message) {
        ((UserAccountActivity) getActivity()).showMessageDialog(message);
    }

    private void emailLogin() {
        String email = mEmailAddressView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (TextUtils.isEmpty(email.trim())) {
            showDialog("Email cannot be empty");
        }

        else if (TextUtils.isEmpty(password.trim())) {
            showDialog("Password cannot be empty");
        }

        else {
            ((UserAccountActivity) getActivity()).showProgressDialog("Logging in...");
            RestClient client = RestClient.getInstance(
                    getActivity().getApplicationContext());

            client.login(email, password, new Callback<SignUpResponse>() {
                @Override
                public void success(SignUpResponse loginResponse, retrofit.client.Response response) {
                    if (getActivity() == null || loginResponse == null) {
                        return;
                    }

                    ((UserAccountActivity) getActivity()).hideDialog();
                    int id = loginResponse.getId();
                    AppPreference.getInstance(getActivity()).setUserLoginStatus(true);
                    AppPreference.getInstance(getActivity()).setUserLoginId(id);
                    mCallback.onLoginSuccessful();
                }

                @Override
                public void failure(RetrofitError error) {
                    if (getActivity() == null) {
                        return;
                    }

                    showDialog("Error in login..");
                }
            });
        }
    }
}
