package gz.purple.demo;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import gz.purple.R;
import gz.purple.loadmore.LoadMoreFooter;

public class LoadingFooter extends LinearLayout implements LoadMoreFooter {

    private int mState = LOADING_STATE_INIT;
    private ProgressBar pb;
    private TextView tv;

    public LoadingFooter(Context context) {
        super(context);
        init();
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.footer_loading, this);
        pb = (ProgressBar) findViewById(R.id.footer_progressBar);
        tv = (TextView) findViewById(R.id.footer_tv);
        setState(LOADING_STATE_NORMAL);
    }

    public void setState(@LoadingState int state) {
        if (mState == state) return;
        mState = state;
        switch (mState) {
            case LOADING_STATE_NORMAL:
                setVisibility(INVISIBLE);
                break;
            case LOADING_STATE_PREPARE:
                setVisibility(VISIBLE);
                pb.setVisibility(INVISIBLE);
                tv.setText("释放加载更多");
                break;
            case LOADING_STATE_LOAD:
                setVisibility(VISIBLE);
                pb.setVisibility(VISIBLE);
                tv.setText("加载中");
                break;
            case LOADING_STATE_COMPLETE:
                setState(LOADING_STATE_NORMAL);
                break;

        }
    }

    public int getState() {
        return mState;
    }

    private static final int LOADING_STATE_INIT = -1;

    public static final int LOADING_STATE_NORMAL = 0;

    public static final int LOADING_STATE_PREPARE = 1;

    public static final int LOADING_STATE_LOAD = 2;

    public static final int LOADING_STATE_COMPLETE = 3;

    @Override
    public void onLoadMore() {
        setState(LoadingFooter.LOADING_STATE_LOAD);
    }

    @Override
    public void onPrepareLoad() {
        setState(LoadingFooter.LOADING_STATE_PREPARE);
    }

    @Override
    public void onLoadCancel() {
        setState(LoadingFooter.LOADING_STATE_NORMAL);
    }

    @Override
    public void onLoadCompleted() {
        setState(LoadingFooter.LOADING_STATE_COMPLETE);
    }


    @IntDef({LOADING_STATE_INIT, LOADING_STATE_NORMAL, LOADING_STATE_LOAD, LOADING_STATE_COMPLETE, LOADING_STATE_PREPARE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingState {
    }

}
