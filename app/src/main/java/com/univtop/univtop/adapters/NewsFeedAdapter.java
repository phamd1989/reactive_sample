package com.univtop.univtop.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.univtop.univtop.R;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.services.PagingApiService;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dungpham on 11/1/15.
 */
public class NewsFeedAdapter extends PageableListAdapter<Question> {

    private boolean mIsFollowed = false;

    public NewsFeedAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @Bind(R.id.profile_pic_iv)
        SimpleDraweeView profilePic;
        @Bind(R.id.name_tv)
        TextView name;
        @Bind(R.id.timestamp_tv)
        TextView timestamp;
        @Bind(R.id.question_title_tv)
        TextView questionTitle;
        @Bind(R.id.question_details_tv)
        TextView questionDetails;
        @Bind(R.id.upvote_btn)
        View upvote;
        @Bind(R.id.answer_tv)
        TextView answers;
        @Bind(R.id.follow_btn)
        ImageView followBtn;
        @Bind(R.id.upvotes_tv)
        TextView upvotes;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            rootView = v;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position) {
        Question question = getItemAtPosition(position);
        final ViewHolder vh = (ViewHolder) holder;
        vh.name.setText(question.getUser().getUsername());
        vh.timestamp.setText(question.getTimestamp());
        vh.questionTitle.setText(question.getQuestionTitle());
        vh.questionDetails.setText(question.getQuestionDetails());
        vh.answers.setText(Integer.toString(question.getAnswers()) + " Answers");
        if (question.getVotes() > 0) {
            vh.upvotes.setText(Integer.toString(question.getVotes()));
        }
        vh.profilePic.setImageURI(Uri.parse(question.getUser().getAvatar()));
        vh.followBtn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (mIsFollowed) {
                    vh.followBtn.setBackground(mContext.getResources().getDrawable(R.drawable.selector_profile_follow));
                } else {
                    vh.followBtn.setBackground(mContext.getResources().getDrawable(R.drawable.selector_profile_following));
                }
                mIsFollowed = !mIsFollowed;
            }
        });
    }

    @Override
    public void fetchPage(String url) {
        Uri uri = Uri.parse(url);
        String authority = uri.getAuthority();
        String part = uri.getScheme() + "://" + authority;
        String path = url.replace(part + "/", "");

        (new PagingApiService(APIService.API_URL)).getPublicQuestionsPage(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }
}
