package gz.purple.loadmore;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class RVLoadMoreRvOnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 手指离开后触发加载更多
     */
    public static final int LOAD_MORE_IDLE = 1;
    /**
     * 立即触发加载更多
     */
    public static final int LOAD_MORE_IMMEDIATE = 2;

    @IntDef({LOAD_MORE_IDLE, LOAD_MORE_IMMEDIATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMore {
    }

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;


    /**
     * 加载更多的模式，默认立即触发加载更多（这样滑动更爽）。
     */
    private int mLoadMoreMode = LOAD_MORE_IMMEDIATE;

    /**
     * 加载更多功能开关，默认开启.
     */
    private boolean enable = true;

    private boolean isLoading = false;

    private LoadMoreFooter loadMoreFooter;

    private RecyclerView.LayoutManager layoutManager;

    private RVLoadMoreRvOnScrollListener() {
        super();
    }

    public RVLoadMoreRvOnScrollListener(LoadMoreFooter loadMoreFooter) {
        this.loadMoreFooter = loadMoreFooter;
    }

    /**
     * 这方法一般没啥用
     *
     * @param loadMoreFooter
     * @return
     */
    public RVLoadMoreRvOnScrollListener setFooter(LoadMoreFooter loadMoreFooter) {
        this.loadMoreFooter = loadMoreFooter;
        return this;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        initLayoutManager(recyclerView);
        int position = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();

        if (enable && !isLoading && layoutManager.getChildCount() > 0) {
            if (mLoadMoreMode == LOAD_MORE_IMMEDIATE) {
                if (position != lastVisibleItemPosition
                        && position == (layoutManager.getItemCount() - 1)) {
                    loadMoreFooter.onLoadMore();
                    onLoadMore(recyclerView);
                    isLoading = true;
                }
            } else if (mLoadMoreMode == LOAD_MORE_IDLE) {
                if (position == (layoutManager.getItemCount() - 1)) {
                    loadMoreFooter.onPrepareLoad();
                    onPrepareLoad(recyclerView);
                }
                //未释放，往回滑动
                if (lastVisibleItemPosition == (layoutManager.getItemCount() - 1) && position == lastVisibleItemPosition - 1) {
                    loadMoreFooter.onLoadCancel();
                    onLoadCancel(recyclerView);
                }
            }
        }
        Log.e("listener:", lastVisibleItemPosition + "===" + position);
        lastVisibleItemPosition = position;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        initLayoutManager(recyclerView);

        if (enable
                && !isLoading
                && mLoadMoreMode == LOAD_MORE_IDLE
                && layoutManager.getChildCount() > 0
                && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisibleItemPosition == (layoutManager.getItemCount() - 1)) {
            loadMoreFooter.onLoadMore();
            onLoadMore(recyclerView);
            isLoading = true;
        }
    }

    private void initLayoutManager(RecyclerView recyclerView) {
        if (layoutManager == null)
            layoutManager = recyclerView.getLayoutManager();
    }


    public RVLoadMoreRvOnScrollListener setLoadMoreMode(@LoadMore int loadMoreMode) {
        mLoadMoreMode = loadMoreMode;
        return this;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void loadCompleted() {
        isLoading = false;
        loadMoreFooter.onLoadCompleted();
    }

    public boolean isLoading() {
        return isLoading;
    }

    /**
     * 触发了加载更多的回调事件，在这里更新数据。
     *
     * @param recyclerView recycleview啊
     */
    public abstract void onLoadMore(RecyclerView recyclerView);

    /**
     * 当{@link RVLoadMoreRvOnScrollListener#mLoadMoreMode} 为 {@link RVLoadMoreRvOnScrollListener#LOAD_MORE_IDLE} 时，
     * 滑到footer可见，手释放前会调用此方法。
     *
     * @param recyclerView recycleview啊
     */
    public abstract void onPrepareLoad(RecyclerView recyclerView);


    /**
     * 当{@link RVLoadMoreRvOnScrollListener#mLoadMoreMode} 为 {@link RVLoadMoreRvOnScrollListener#LOAD_MORE_IDLE} 时，
     * 滑到footer可见后，手未释放，又滑到footer不可见的时候调用此方法。
     *
     * @param recyclerView recycleview啊
     */
    public abstract void onLoadCancel(RecyclerView recyclerView);
}
