package gz.purple.loadmore;

/**
 * @Description: TODO describe this class
 * @Copyright: Copyright (c) 2016 chexiang.com. All right reserved.
 * @Author: guizhen
 * @Date: 2016/6/16 14:13
 * @Modifier: guizhen
 * @Update: 2016/6/16 14:13
 */
public interface LoadMoreFooter {

    void onLoadMore();

    void onPrepareLoad();


    void onLoadCancel();

    void onLoadCompleted();

}
