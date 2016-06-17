package gz.purple.demo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import gz.purple.R;
import gz.purple.loadmore.RvLoadMoreAdapter;

public class PullAdapter extends RvLoadMoreAdapter {


    private ArrayList<String> dataList;


    public void setData(ArrayList<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setHasFooter(!isHasFooter());
//            Toast.makeText(context, "hasFoot:" + isHasFooter(), Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public int getRvItemCount() {
        return dataList.size();
    }

    @Override
    public int getRvItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onRvCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pull_item, parent, false));
    }

    @Override
    public void onRvBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).tv.setText(dataList.get(position));
        ((MyViewHolder) holder).tv.setOnClickListener(clickListener);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}
