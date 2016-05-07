package com.univtop.univtop.adapters;

import com.univtop.univtop.presenters.BasePresenter;
import com.univtop.univtop.views.MvpViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dungpham on 3/26/16.
 */
public abstract class MvpRecyclerListAdapter<M, P extends BasePresenter, VH extends MvpViewHolder<P>> extends MvpRecyclerAdapter<M, P, VH> {
    private final List<M> models;

    public MvpRecyclerListAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data) {
        models.clear();
        presenters.clear();

        for (M item : data) {
            addInternal(item);
        }
        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data) {
        int currSize = getItemCount();
        for (M item: data) {
            addInternal(item);
        }
        notifyItemRangeInserted(currSize, getItemCount() - 1);
    }

    private void addInternal(M item) {
        System.err.println("Adding item " + getModelId(item));
        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int position) {
        return models.get(position);
    }

    public abstract String getNextPage();
}
