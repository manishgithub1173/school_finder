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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.RestWebService.RestClient;
import com.hackathon.sequoia.sequoiahackathon.activity.SchoolDetailActivity;
import com.hackathon.sequoia.sequoiahackathon.activity.UserAccountActivity;
import com.hackathon.sequoia.sequoiahackathon.api.Review;
import com.hackathon.sequoia.sequoiahackathon.api.SchoolDetailResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SchoolDetailFragment extends Fragment {


    private int mSchoolId;
    private OnFragmentInteractionListener mListener;

    private TextView mSchoolNameTv;
    private ImageView mSchoolImage;
    private RatingBar mInfraRating;
    private RatingBar mTeachingRating;
    private ArrayList<Review> reviewList = new ArrayList<>();

    public static SchoolDetailFragment newInstance(int id) {
        SchoolDetailFragment fragment = new SchoolDetailFragment();
        Bundle args = new Bundle();
        args.putInt("school_id", id);
        fragment.setArguments(args);
        return fragment;
    }

    public SchoolDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSchoolId = getArguments().getInt("school_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_school_detail, container, false);

        mSchoolNameTv = (TextView)view.findViewById(R.id.tv_school_name);
        mSchoolImage = (ImageView)view.findViewById(R.id.iv_school);

        mInfraRating = (RatingBar)view.findViewById(R.id.rating_infra);
        mTeachingRating = (RatingBar)view.findViewById(R.id.rating_teacher_quality);

        Button button = (Button)view.findViewById(R.id.btn_add_review);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.writeReview(mSchoolId, mSchoolNameTv.getText().toString(),
                        mInfraRating.getRating(), mTeachingRating.getRating());
            }
        });

        Button seeReview = (Button)view.findViewById(R.id.btn_reviews);
        seeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.showReview(reviewList);
            }
        });

        getDetails();
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
        // TODO: Update argument type and name
        public void writeReview(int id, String name, float rating1, float rating2);
        public void showReview(ArrayList<Review> list);
    }

    private void getDetails() {
        RestClient client = RestClient.getInstance(getActivity());
        ((SchoolDetailActivity) getActivity()).showProgressDialog("Getting school details...");
        client.getSchoolDetail(mSchoolId, new Callback<SchoolDetailResponse>() {
            @Override
            public void success(SchoolDetailResponse schoolDetailResponse, Response response) {
                ((SchoolDetailActivity) getActivity()).hideDialog();
                mSchoolNameTv.setText(schoolDetailResponse.getSchool().getName());
                String url = "http://www.thehindu.com/thehindu/lf/2002/03/26/images/2002032600350201.jpg";
                if(!TextUtils.isEmpty(schoolDetailResponse.getSchool().getImage())) {
                    url = schoolDetailResponse.getSchool().getImage();
                }
                Picasso.with(getActivity()).
                        load(url)
                        .into(mSchoolImage);
                mInfraRating.setRating((float) schoolDetailResponse.getRating().getInfrastructure());
                mTeachingRating.setRating((float)schoolDetailResponse.getRating().getTeaching());
                reviewList.addAll(schoolDetailResponse.getReviews());
            }

            @Override
            public void failure(RetrofitError error) {

                ((SchoolDetailActivity) getActivity()).hideDialog();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
