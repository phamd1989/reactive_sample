package com.univtop.univtop.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    public NewsFeedAdapter(Context context) {
        super(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        @Bind(R.id.question_details)
        TextView questionDetails;

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
        String questionDetail = question.getTitle();
        final ViewHolder vh = (ViewHolder) holder;
        vh.questionDetails.setText(questionDetail);
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
