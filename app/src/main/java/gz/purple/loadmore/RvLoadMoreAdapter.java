package gz.purple.loadmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class RvLoadMoreAdapter extends RecyclerView.Adapter {
    protected static final int FOOTER_VIEWTYPE = 10086;

    private boolean hasFooter = false;
    private View footerView;
    public Context context;
    private RVLoadMoreRvOnScrollListener listener;


    public RvLoadMoreAdapter(Context context) {
        this.context = context;
    }

    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        if (listener != null) listener.setEnable(hasFooter);
        notifyDataSetChanged();
    }

    /**
     * 加载更多的监听事件
     *
     * @param recyclerView 监听的recycleview
     * @param listener     回调
     */
    public void setLoadMoreListener(RecyclerView recyclerView, RVLoadMoreRvOnScrollListener listener) {
        recyclerView.addOnScrollListener(listener);
        this.listener = listener;
        listener.setEnable(isHasFooter());
    }

    public View getFooterView() {
        return footerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEWTYPE)
            return new FootHolder(footerView == null ? defaultFooter(parent) : footerView);
        else return onRvCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(hasFooter && position == getItemCount() - 1))
            onRvBindViewHolder(holder, position);
    }

    @Override
    public final int getItemCount() {
        return getRvItemCount() + (hasFooter ? 1 : 0);
    }

    @Override
    public final int getItemViewType(int position) {
        if (hasFooter && position == getItemCount() - 1)
            return FOOTER_VIEWTYPE;
        else
            return getRvItemViewType(position);
    }


    public class FootHolder extends RecyclerView.ViewHolder {

        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    private View defaultFooter(ViewGroup parent) {
        TextView footerTv = (TextView) LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        footerTv.setText("Footer");
        return footerTv;
    }

    /**
     * 等同getItemCount
     *
     * @return item个数
     */
    public abstract int getRvItemCount();

    /**
     * 等同getItemViewType
     *
     * @param position 对应的position
     * @return viewtype，footer的viewtype值为{@link RvLoadMoreAdapter#FOOTER_VIEWTYPE}。
     */
    public abstract int getRvItemViewType(int position);

    /**
     * 等同onCreateViewHolder
     *
     * @param parent   父view，这里是recycleview
     * @param viewType viewtype，footer的viewtype值为{@link RvLoadMoreAdapter#FOOTER_VIEWTYPE}。
     * @return {@link android.support.v7.widget.RecyclerView.ViewHolder}。
     */
    public abstract RecyclerView.ViewHolder onRvCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onRvBindViewHolder(RecyclerView.ViewHolder holder, int position);

}
