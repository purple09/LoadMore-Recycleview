package gz.purple.demo;

import android.view.View;
import android.widget.TextView;

import gz.purple.R;
import gz.purple.loadmore.commonadapter.AdapterItem;

/**
 * @Description: TODO describe this class
 * @Copyright: Copyright (c) 2016 chexiang.com. All right reserved.
 * @Author: guizhen
 * @Date: 2016/6/17 17:25
 * @Modifier: guizhen
 * @Update: 2016/6/17 17:25
 */
public class PullItem implements AdapterItem<String> {
    @Override
    public int getLayoutResId() {
        return R.layout.pull_item;
    }

    TextView tv;

    @Override
    public void bindViews(View root) {
        tv = (TextView) root.findViewById(R.id.tv);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(String s, int position) {
        tv.setText(s);
    }
}
