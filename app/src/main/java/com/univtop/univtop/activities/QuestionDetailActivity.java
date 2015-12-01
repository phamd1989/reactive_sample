package com.univtop.univtop.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.univtop.univtop.R;
import com.univtop.univtop.adapters.QuestionAdapter;
import com.univtop.univtop.fragments.NewsFeedFragment;
import com.univtop.univtop.models.Question;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QuestionDetailActivity extends AbstractBaseActivity {
    private Question mQuestion;
    private int mPos;
    private RecyclerView mRecyclerView;

//    @Bind(R.id.profile_pic_iv)
//    SimpleDraweeView profilePic;
//    @Bind(R.id.name_tv)
//    TextView name;
//    @Bind(R.id.timestamp_tv)
//    TextView timestamp;
//    @Bind(R.id.question_title_tv)
//    TextView questionTitle;
//    @Bind(R.id.question_details_tv)
//    TextView questionDetails;
//    @Bind(R.id.upvote_btn)
//    View upvote;
//    @Bind(R.id.answer_tv)
//    TextView answers;
//    @Bind(R.id.follow_btn)
//    View followBtn;
//    @Bind(R.id.unfollow_btn)
//    View unfollowBtn;
//    @Bind(R.id.upvotes_tv)
//    TextView upvotes;


    public static Intent getLaunchIntent(Context context, Question question, int pos) {
        Intent intent = new Intent(context, QuestionDetailActivity.class);
        intent.putExtra(NewsFeedFragment.QUESTION, question);
        intent.putExtra(NewsFeedFragment.POSITION, pos);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        ButterKnife.bind(this);
        setupToolbar();
        setupActionBar("Question");

        mRecyclerView = (RecyclerView) findViewById(R.id.profile_recyclerView);
        mQuestion = (Question) getIntent().getSerializableExtra(NewsFeedFragment.QUESTION);
        mPos = getIntent().getIntExtra(NewsFeedFragment.POSITION, -1);
        QuestionAdapter adapter = new QuestionAdapter(mQuestion);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        final boolean isFollowed = mQuestion.is_followed();
//
//        name.setText(mQuestion.getUser().getUsername());
//        timestamp.setText(mQuestion.getTimestamp());
//        questionTitle.setText(mQuestion.getQuestionTitle());
//        questionDetails.setText(mQuestion.getQuestionDetails());
//        answers.setText(Integer.toString(mQuestion.getAnswers()) + " Answers");
//        if (mQuestion.getVotes() > 0) {
//            upvotes.setText(Integer.toString(mQuestion.getVotes()));
//        }
//        profilePic.setImageURI(Uri.parse(mQuestion.getUser().getAvatar()));
//        if (isFollowed) {
//            followBtn.setVisibility(View.GONE);
//            unfollowBtn.setVisibility(View.VISIBLE);
//            unfollowBtn.setOnClickListener(getUnfollowClickListener(mQuestion));
//        } else {
//            followBtn.setVisibility(View.VISIBLE);
//            unfollowBtn.setVisibility(View.GONE);
//            followBtn.setOnClickListener(getFollowClickListener(mQuestion));
//        }
    }

//    private View.OnClickListener getFollowClickListener(final Question question) {
//        final boolean isFollowed = question.is_followed();
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                followBtn.setVisibility(View.GONE);
//                unfollowBtn.setVisibility(View.VISIBLE);
//                unfollowBtn.setOnClickListener(getUnfollowClickListener(question));
//                question.setIs_followed(!isFollowed);
//            }
//        };
//    }
//
//    private View.OnClickListener getUnfollowClickListener(final Question question) {
//        final boolean isFollowed = question.is_followed();
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                followBtn.setVisibility(View.VISIBLE);
//                followBtn.setOnClickListener(getFollowClickListener(question));
//                unfollowBtn.setVisibility(View.GONE);
//                question.setIs_followed(!isFollowed);
//            }
//        };
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case android.R.id.home:
                Intent data = new Intent();
                data.putExtra(NewsFeedFragment.POSITION, mPos);
                data.putExtra(NewsFeedFragment.FOLLOWING, mQuestion.is_followed());
                setResult(RESULT_OK, data);
                finish();
                break;
            default:
                break;
        }
        super.onOptionsItemSelected(item);
        return true;
    }
}
