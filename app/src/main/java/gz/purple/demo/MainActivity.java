package gz.purple.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import gz.purple.R;
import gz.purple.loadmore.RVLoadMoreRvOnScrollListener;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> dataList;
    RecyclerView rv;
    Handler handler;
    PtrFrameLayout ptrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();

        dataList = new ArrayList<>();

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rv.setItemAnimator(new DefaultItemAnimator());
        final PullAdapter adapter = new PullAdapter(this);
        adapter.setHasFooter(true);
        adapter.setData(dataList);
        final LoadingFooter loadingFooter = new LoadingFooter(this);
        adapter.setFooterView(loadingFooter);
        adapter.setLoadMoreListener(rv, new RVLoadMoreRvOnScrollListener(loadingFooter) {

            @Override
            public void onPrepareLoad(RecyclerView recyclerView) {
            }

            @Override
            public void onLoadCancel(RecyclerView recyclerView) {
            }

            @Override
            public void onLoadMore(RecyclerView recyclerView) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int total = dataList.size();
                        for (int i = total; i < total + 5; i++) {
                            dataList.add("item" + (i + 1));
                        }
                        adapter.notifyDataSetChanged();
                        loadCompleted();
                        if (dataList.size() >= 50) adapter.setHasFooter(false);
                    }
                }, 1000);

            }
        });
        rv.setAdapter(adapter);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    dataList.add("item" + (i + 1));
                }
                adapter.notifyDataSetChanged();
            }
        },5000);

        ptrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_pull_refresh);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.clear();
                        for (int i = 0; i < 15; i++) {
                            dataList.add("refresh-item" + (i + 1));
                        }
                        adapter.notifyDataSetChanged();
                        ptrFrameLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
    }
}
