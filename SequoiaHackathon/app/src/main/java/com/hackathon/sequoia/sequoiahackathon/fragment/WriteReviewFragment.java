package com.hackathon.sequoia.sequoiahackathon.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.RestWebService.RestClient;
import com.hackathon.sequoia.sequoiahackathon.activity.UserAccountActivity;
import com.hackathon.sequoia.sequoiahackathon.api.SubmitResponse;
import com.hackathon.sequoia.sequoiahackathon.global.AppPreference;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by manishkumar on 30/08/15.
 */
public class WriteReviewFragment extends Fragment {

    private int mSchoolId;
    private OnFragmentInteractionListener mListener;

    private float infraRatingVal;
    private float teachingRatingVal;
    private String mSchoolName;

    private TextView mSchoolNameTv;
    private EditText mReviewComments;

    public static WriteReviewFragment newInstance(int id, String name) {
        WriteReviewFragment fragment = new WriteReviewFragment();
        Bundle args = new Bundle();
        args.putInt("school_id", id);
        args.putString("school_name", name);
        fragment.setArguments(args);
        return fragment;
    }

    public WriteReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSchoolId = getArguments().getInt("school_id");
            mSchoolName = getArguments().getString("school_name");
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_write_review, container, false);

        mSchoolNameTv = (TextView)view.findViewById(R.id.tv_school_name1);
        mReviewComments = (EditText)view.findViewById(R.id.et_comment);
        mSchoolNameTv.setText(mSchoolName);
        final RatingBar infraRating = (RatingBar)view.findViewById(R.id.rating_infra1);
        infraRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                infraRatingVal = rating;
            }
        });

        RatingBar teachingRating = (RatingBar)view.findViewById(R.id.rating_teacher_quality1);
        teachingRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                teachingRatingVal = rating;
            }
        });

        Button submitButton = (Button)view.findViewById(R.id.btn_submit_review);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mReviewComments.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please add comments", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendReview();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onSubmitSuccesful();
    }

    private void sendReview() {
        //API call
        if(!AppPreference.getInstance(getActivity()).isUserLoggedin()) {
            UserAccountActivity.showLogin(getActivity());
        } else {
            int id = AppPreference.getInstance(getActivity()).getUserId();
            RestClient client = RestClient.getInstance(getActivity());
            String rating = String.valueOf(infraRatingVal) + "," + String.valueOf(teachingRatingVal);
            client.submitReview(mSchoolId, id, mReviewComments.getText().toString(), "", rating, new Callback<SubmitResponse>() {
                @Override
                public void success(SubmitResponse submitResponse, Response response) {
                    mListener.onSubmitSuccesful();
                    Toast.makeText(getActivity(), "Review Submitted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
