package gz.purple.loadmore.commonadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gz.purple.loadmore.RvLoadMoreAdapter;

/**
 * @Description: TODO describe this class
 * @Copyright: Copyright (c) 2016 chexiang.com. All right reserved.
 * @Author: guizhen
 * @Date: 2016/6/17 16:52
 * @Modifier: guizhen
 * @Update: 2016/6/17 16:52
 */
public abstract class RvLoadMoreCommonAdapter<T> extends RvLoadMoreAdapter implements IAdapter<T> {
    private List<T> mDataList;

    private Object mType;

    private ItemTypeUtil mUtil;

    public RvLoadMoreCommonAdapter(@Nullable List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mDataList = data;
        mUtil = new ItemTypeUtil();
    }

    @Override
    public void setData(@NonNull List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItemType(int position) {
        return -1; // default
    }

    @NonNull
    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }


    @Override
    public int getRvItemViewType(int position) {
        mType = getItemType(position);
        return mUtil.getIntType(mType);
    }

    @Override
    public void onRvBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RcvAdapterItem) holder).item.handleData(getConvertedData(mDataList.get(position), mType), position);
    }

    @Override
    public RecyclerView.ViewHolder onRvCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mType));
    }

    @Override
    public int getRvItemCount() {
        return mDataList.size();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部用到的viewHold
    ///////////////////////////////////////////////////////////////////////////

    private static class RcvAdapterItem extends RecyclerView.ViewHolder {

        protected AdapterItem item;

        protected RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
    }

}
