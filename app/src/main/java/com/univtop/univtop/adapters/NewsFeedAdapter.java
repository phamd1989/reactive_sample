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
    private ClickQuestionDetailListener mListener;

    public interface ClickQuestionDetailListener {
        void launch(final Question question, final int pos);
    }

    public void setQuestionDetailListener(ClickQuestionDetailListener listener) {
        mListener = listener;
    }

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
        View followBtn;
        @Bind(R.id.unfollow_btn)
        View unfollowBtn;
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

    private View.OnClickListener getFollowClickListener(final ViewHolder vh, final Question question) {
        final boolean isFollowed = question.is_followed();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.followBtn.setVisibility(View.GONE);
                vh.unfollowBtn.setVisibility(View.VISIBLE);
                vh.unfollowBtn.setOnClickListener(getUnfollowClickListener(vh, question));
                question.setIs_followed(!isFollowed);
            }
        };
    }

    private View.OnClickListener getUnfollowClickListener(final ViewHolder vh, final Question question) {
        final boolean isFollowed = question.is_followed();
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.followBtn.setVisibility(View.VISIBLE);
                vh.followBtn.setOnClickListener(getFollowClickListener(vh, question));
                vh.unfollowBtn.setVisibility(View.GONE);
                question.setIs_followed(!isFollowed);
            }
        };
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Question question = getItemAtPosition(position);
        final ViewHolder vh = (ViewHolder) holder;
        final boolean isFollowed = question.is_followed();

        vh.name.setText(question.getUser().getUsername());
        vh.timestamp.setText(question.getTimestamp());
        vh.questionTitle.setText(question.getQuestionTitle());
        vh.questionDetails.setText(question.getQuestionDetails());
        vh.answers.setText(Integer.toString(question.getAnswers()) + " Answers");
        if (question.getVotes() > 0) {
            vh.upvotes.setText(Integer.toString(question.getVotes()));
        }
        vh.profilePic.setImageURI(Uri.parse(question.getUser().getAvatar()));
        if (isFollowed) {
            vh.followBtn.setVisibility(View.GONE);
            vh.unfollowBtn.setVisibility(View.VISIBLE);
            vh.unfollowBtn.setOnClickListener(getUnfollowClickListener(vh, question));
        } else {
            vh.followBtn.setVisibility(View.VISIBLE);
            vh.unfollowBtn.setVisibility(View.GONE);
            vh.followBtn.setOnClickListener(getFollowClickListener(vh, question));
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.launch(question, position);
                }
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

    public void refreshAtPos(int pos, boolean isFollowing) {
        Question question = getItemAtPosition(pos);
        question.setIs_followed(isFollowing);
        notifyItemChanged(pos);
    }
}
