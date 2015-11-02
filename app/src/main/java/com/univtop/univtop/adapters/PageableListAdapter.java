package com.univtop.univtop.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.univtop.univtop.R;
import com.univtop.univtop.models.paging.Meta;
import com.univtop.univtop.models.paging.PageableList;
import com.univtop.univtop.utils.UnivtopSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by dungpham on 11/2/15.
 */
public class PageableListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int LOADING_TYPE = 0;

    private List<T> mData;
    private Class mWrapper;
    Meta mCursor;
    protected Context mContext;

    private boolean mIsLoading = false;
    private boolean mIsEmpty = false;
    private boolean mNoMorePosts = false;
    private boolean mCallInFlight = false;
    private boolean mEmptyDataOnRefresh = false;

    private PageableListListener mListener;

    /**
     * Lets activities subscribe to know when to hide their refresh indicators
     */
    public interface PageableListListener {
        public void refreshFinished();
        public void empty();
        public void notEmpty();
    }

    public void setListener(PageableListListener listener){
        mListener = listener;
    }

    public void removeListener(){
        mListener = null;
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder{
        public LoadingViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setupView(){

        }
    }

    public PageableListAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public boolean isEmpty() {
        return mIsEmpty;
    }

    public boolean isLoading() { return mIsLoading; }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == LOADING_TYPE){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_view, parent, false);
            LoadingViewHolder vh = new LoadingViewHolder(v);
            return vh;
        } else {
            return onCreateContentViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LoadingViewHolder){
            LoadingViewHolder vh = (LoadingViewHolder) holder;
            if(!mCallInFlight && !mIsLoading){
                mCallInFlight = true;
                if(mCursor != null && mCursor.getNext() != null){
                    String url = mCursor.getNext();
                    fetchPage(url);
                }
            }
            vh.setupView();
        } else {
            onBindContentViewHolder(holder, position);
        }
    }

    /**
     * Fetches next page and subscribes adapter to changes!
     *
     * @param url
     */
    // TODO: incorporate entire paging call into adapter
    public void fetchPage(String url){

    }

    /**
     * Called by the parent adapter for the child to bind content!
     * @param holder
     * @param position
     */
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, int position){

    }

    public void removeItemAtPosition(int position){
        if(position < mData.size()){
            mData.remove(position);
        }
    }

    /**
     * @param item
     */
    public void removeItem(T item){
        mData.remove(item);
        notifyDataSetChanged();
    }

    public T getItemAtPosition(int position){
        if(position >= mData.size()) return null;
        return mData.get(position);
    }

    /**
     * Called by the parent adapter for the child to create content!
     * @param parent
     * @param viewType
     */
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent, int viewType){
        return null;
    }

    /**
     * Only need to override this method if you have more than one content type!
     * @param position
     * @return
     */
    public int getContentItemViewType(int position){
        return -1;
    }

    @Override
    public final int getItemViewType(int position) {
        if(position >= getContentItemCount(mData.size())){
            return LOADING_TYPE;
        } else {
            return getContentItemViewType(position);
        }
    }

    @Override
    public final int getItemCount() {
        return (mIsEmpty || mIsLoading)? getContentItemCount(0) :
                getContentItemCount((mData == null)? 0 : mData.size()) + (mNoMorePosts? 0 : 1);
    }

    /**
     * Allow children to do any additional mucking with the count if they need
     * @param count
     * @return
     */
    public int getContentItemCount(int count){
        return count;
    }

    public UnivtopSubscriber<PageableList<T>> getSubscriber() {
        return new UnivtopSubscriber<PageableList<T>>() {
            @Override
            public void onCompleted() {
                if(mListener != null) mListener.refreshFinished();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if(mListener != null){
                    mListener.refreshFinished();
                    if(mData == null || mData.isEmpty()){
                        if(mListener != null) mListener.empty();
                        mIsEmpty = true;
                        mNoMorePosts = true;
                    }
                }
            }

            @Override
            public void onNext(PageableList<T> pageableList) {
                updateData(pageableList);
            }
        };
    }

    public void emptyDataOnRefresh(){
        mEmptyDataOnRefresh = true;
    }

    public void emptyData(){
        mData.clear();
        mCursor = null;
    }

    public void setLoading(boolean loading){
        mIsLoading = loading;
    }

    public void updateData(PageableList<T> pList){
        if(mEmptyDataOnRefresh){
            mEmptyDataOnRefresh = false;
            emptyData();
        }
        mCallInFlight = false;
        mIsLoading = false;
        if(pList == null){
            return;
        }
        List<T> newContent = pList.getData();
        if(newContent == null || newContent.isEmpty()){
            mNoMorePosts = true;
        } else {
            mNoMorePosts = pList.getMeta() == null || pList.getMeta().getNext() == null;
            mData.addAll(newContent);
        }

        if(mData != null && !mData.isEmpty()){
            if(mListener != null) mListener.notEmpty();
            mIsEmpty = false;
        } else {
            if(mListener != null) mListener.empty();
            mIsEmpty = true;
            mNoMorePosts = true;
        }
        mCursor = pList.getMeta();
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mData;
    }
}
