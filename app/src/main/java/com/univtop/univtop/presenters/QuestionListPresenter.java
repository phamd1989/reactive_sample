package com.univtop.univtop.presenters;

import android.support.annotation.NonNull;

import com.univtop.univtop.models.Question;
import com.univtop.univtop.models.paging.PageableList;
import com.univtop.univtop.services.APIService;
import com.univtop.univtop.utils.UnivtopSubscriber;
import com.univtop.univtop.views.QuestionListView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dungpham on 3/26/16.
 */
public class QuestionListPresenter extends BasePresenter<List<Question>, QuestionListView> {
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        // Business logic is in the presenter
        if (model.size() == 0) {
            view().showEmpty();
        } else {
            view().showQuestions(model);
        }
    }

    @Override
    public void bindView(@NonNull QuestionListView view) {
        super.bindView(view);

        // Let's not reload data if it's already here
        if (model == null && !isLoadingData) {
            view().showLoading();
            loadData();
        }
    }

    private void loadData() {
        isLoadingData = true;
        APIService.getInstance().getPublicQuestions(10, 0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UnivtopSubscriber<PageableList<Question>>() {
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        isLoadingData = false;
                        view().refreshFinished();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        isLoadingData = false;
                        view().refreshFinished();
                    }

                    @Override
                    public void onNext(PageableList<Question> questionPageableList) {
                        if (questionPageableList == null) return;
                        else {
                            List<Question> questions = questionPageableList.getData();
                            setModel(questions);
                        }
                    }
                });
    }
}
