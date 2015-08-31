package com.hackathon.sequoia.sequoiahackathon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hackathon.sequoia.sequoiahackathon.R;
import com.hackathon.sequoia.sequoiahackathon.api.Review;
import com.hackathon.sequoia.sequoiahackathon.fragment.ReviewFragment;

import java.util.List;

/**
 * Created by manishkumar on 30/08/15.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {
    private List<Review> mReviewList;
    private Context mContext;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.mReviewList = reviewList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_list_item, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Review review = mReviewList.get(i);

        //Setting text view title
        customViewHolder.name.setText(review.getName());
        customViewHolder.comments.setText(review.getComment());
    }

    @Override
    public int getItemCount() {
        return (null != mReviewList ? mReviewList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView comments;

        public CustomViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.title);
            this.comments = (TextView) view.findViewById(R.id.comment);
        }
    }
}
