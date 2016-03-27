package com.univtop.univtop.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.univtop.univtop.presenters.BasePresenter;

/**
 * Created by dungpham on 3/26/16.
 */
public abstract class MvpViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder {
    protected P presenter;

    public MvpViewHolder(View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter) {
        this.presenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        presenter = null;
    }
}