package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class XRecyclerView extends RecyclerView {

    private  Context mContext;
    private boolean isLoadingData = false;
    private boolean isnomore = false;
    private int mRefreshProgressStyle = ProgressStyle.SysProgress;
    private int mLoadingMoreProgressStyle = ProgressStyle.SysProgress;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFootViews = new ArrayList<>();
    protected Adapter mAdapter;
    protected Adapter mWrapAdapter;
    private float mLastY = -1;
    private static final float DRAG_RATE = 2;
    private LoadingListener mLoadingListener;
    private ArrowRefreshHeader mRefreshHeader;
    private boolean pullRefreshEnabled = true;
    protected boolean loadingMoreEnabled = true;
    private static final int TYPE_REFRESH_HEADER =  -5;
    private static final int TYPE_HEADER =  -4;
    private static final int TYPE_NORMAL =  0;
    private static final int TYPE_FOOTER =  -3;
    private int previousTotal = 0;
    private int mPageCount = 0;
    public  LoadingMoreFooter mFootView;


    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if(pullRefreshEnabled) {
            ArrowRefreshHeader refreshHeader = new ArrowRefreshHeader(mContext);
            mHeaderViews.add(0, refreshHeader);
            mRefreshHeader = refreshHeader;
            mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
        }
//        LoadingMoreFooter footView = new LoadingMoreFooter(mContext);
//        footView.setProgressStyle(mLoadingMoreProgressStyle);
//        addFootView(footView);
//        mFootViews.get(0).setVisibility(GONE);

        mFootView = new LoadingMoreFooter(mContext);
        mFootView.setProgressStyle(mLoadingMoreProgressStyle);
        addFootView(mFootView);
        mFootViews.get(0).setVisibility(GONE);
    }

    public void addHeaderView(View view) {
        if (pullRefreshEnabled && !(mHeaderViews.get(0) instanceof ArrowRefreshHeader)) {
            ArrowRefreshHeader refreshHeader = new ArrowRefreshHeader(mContext);
            mHeaderViews.add(0, refreshHeader);
            mRefreshHeader = refreshHeader;
            mRefreshHeader.setProgressStyle(mRefreshProgressStyle);
        }
        mHeaderViews.add(view);
        this.requestLayout();
    }

    public void addFootView(final View view) {
        mFootViews.clear();
        mFootViews.add(view);
    }

    public boolean isContainHeader(int id) {
        for (View v : mHeaderViews) {
            if (v.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void removeHeaderView(int id) {
        for (int i = 0; i < mHeaderViews.size();) {
            if (mHeaderViews.get(i).getId() == id) {
                mHeaderViews.remove(i);
                break;
            } else {
                i ++;
            }
        }
    }

    public void showFooter(boolean isShow) {
        if (isShow) {
            if (mFootViews.size() == 1) {
                return;
            }
            addFootView(mFootView);
            mFootViews.get(0).setVisibility(GONE);
            setLoadingMoreEnabled(true);
        } else {
            if (mFootViews.size() == 0) {
                return;
            }
//            setLoadingMoreEnabled(false);
            mFootViews.remove(0);
        }
    }

    public void loadMoreComplete() {
        isLoadingData = false;
        if (mFootViews.size() == 0) {
            return;
        }
        View footView = mFootViews.get(0);
        if(footView instanceof  LoadingMoreFooter) {
            ( (LoadingMoreFooter) footView ).setState(LoadingMoreFooter.STATE_NORMAL);
        }else{
            footView.setVisibility(View.GONE);
        }
    }

    public void noMoreLoading() {
        isLoadingData = false;
        View footView = mFootViews.get(0);
        isnomore = true;
        if(footView instanceof  LoadingMoreFooter) {
            ( (LoadingMoreFooter) footView ).setState(LoadingMoreFooter.STATE_NOMORE);
        }else{
            footView.setVisibility(View.GONE);
        }
    }

    public void refreshComplete() {
        mRefreshHeader.refreshComplate();
        isLoadingData = false;
    }

    public  void setRefreshHeader(ArrowRefreshHeader refreshHeader){
        mRefreshHeader = refreshHeader;
    }

    public void setPullRefreshEnabled(boolean enabled){
        pullRefreshEnabled = enabled;
    }

    /**
     *  刷新状态结束
     *  加载更多状态结束
     */
    public void stopAll(){
        refreshComplete();
        loadMoreComplete();
    }
    /**
     * 是否可以加载更多
     */
    public void setLoadingMoreEnabled(boolean enabled){
        loadingMoreEnabled = enabled;
        if(enabled){
            if (mFootViews.size() == 0) {
                showFooter(true);
            } else {
                View footView = mFootViews.get(0);
                footView.setVisibility(View.VISIBLE);
                if(footView instanceof  LoadingMoreFooter) {
                    ( (LoadingMoreFooter) footView ).setState(LoadingMoreFooter.STATE_NORMAL);
                }
            }
        }else{
//            showFooter(false);
            noMoreLoading();
        }
    }

    public void setFootViewVisibility(int visibility){
        if(mFootViews != null && mFootViews.size() > 0) {
            View footView = mFootViews.get(0);
            if (footView != null) {
                footView.setVisibility(visibility);
            }
        }
    }

    public void setRefreshProgressStyle(int style) {
        mRefreshProgressStyle = style;
        if (mRefreshHeader != null){
            mRefreshHeader.setProgressStyle(style);
        }
    }

    public void setRefreshHeaderHint(String normal , String release , String loading , String done){
        if (mRefreshHeader != null) {
            mRefreshHeader.setHint(normal, release, loading, done);
        }
    }

    public void setRefreshHeaderHintLoadMore(){
        if (mRefreshHeader != null){
            mRefreshHeader.setHintLoadMore();
        }
    }

    public void setLoadingMoreProgressStyle(int style) {
        mLoadingMoreProgressStyle = style;
        if(mFootViews.size() > 0 && mFootViews.get(0) instanceof LoadingMoreFooter){
            ((LoadingMoreFooter) mFootViews.get(0)).setProgressStyle(style);
        }
    }

    public void setArrowImageView(int resid) {
        if (mRefreshHeader != null){
            mRefreshHeader.setArrowImageView(resid);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter  = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        LayoutManager layoutManager = getLayoutManager();
        int lastVisibleItemPosition;
        if (layoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
            lastVisibleItemPosition = findMax(into);
        } else {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        if (layoutManager.getChildCount() > 0
                && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && !isLoadingData && loadingMoreEnabled) {
            if (mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING && mFootViews.size() > 0) {
                View footView = mFootViews.get(0);

                if(footView instanceof  LoadingMoreFooter) {
                    ( (LoadingMoreFooter) footView ).setState(LoadingMoreFooter.STATE_LAODING);
                } else{
                    footView.setVisibility(View.VISIBLE);
                }
                isLoadingData = true;
                mLoadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if ( isOnTop() && pullRefreshEnabled) {
                    mRefreshHeader.onMove(deltaY / DRAG_RATE);
                    if(mRefreshHeader.getVisiableHeight() > 0 && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING ) {

                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if ( isOnTop() && pullRefreshEnabled) {
                    if( mRefreshHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            mLoadingListener.onRefresh();
//                            isLoadingData=true;
                            isnomore = false;
                            previousTotal = 0;
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private boolean isOnTop() {
        if (mHeaderViews == null || mHeaderViews.isEmpty()) {
            return false;
        }

        View view = mHeaderViews.get(0);
        if (view.getParent() != null) {
            return true;
        } else {
            return false;
        }
//        LayoutManager layoutManager = getLayoutManager();
//        int firstVisibleItemPosition;
//        if (layoutManager instanceof GridLayoutManager) {
//            firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        } else if ( layoutManager instanceof StaggeredGridLayoutManager ) {
//            int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
//            ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(into);
//            firstVisibleItemPosition = findMin(into);
//        } else {
//            firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//        }
//        if ( firstVisibleItemPosition <= 1 ) {
//             return true;
//        }
//        return false;
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    private class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private RecyclerView.Adapter adapter;

        private ArrayList<View> mHeaderViews;

        private ArrayList<View> mFootViews;

        private int headerPosition = 1;

        public WrapAdapter(ArrayList<View> headerViews, ArrayList<View> footViews, RecyclerView.Adapter adapter) {
            this.adapter = adapter;
            this.mHeaderViews = headerViews;
            this.mFootViews = footViews;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            if(manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position)||  isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if(lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    &&  (isHeader( holder.getLayoutPosition()) || isFooter( holder.getLayoutPosition())) ) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        public boolean isRefreshHeader(int position) {
            return position == 0 ;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(mHeaderViews.get(0));
            } else if (viewType == TYPE_HEADER) {
                return new SimpleViewHolder(mHeaderViews.get(headerPosition++ ));
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootViews.get(0));
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (isHeader(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(isRefreshHeader(position)){
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if(isFooter(position)){
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();;
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();
    }
}