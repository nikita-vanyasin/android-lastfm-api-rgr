package nikita.rgr.lastfm;

import android.widget.AbsListView;

import nikita.rgr.lastfm.ListController.ListController;

/**
 * Created by Nikita on 25.05.14.
 */
public class EndlessScrollListener implements AbsListView.OnScrollListener {


    private int visibleThreshold = 5;
    private int previousTotal = 0;
    private boolean loading = true;
    private ListController listController;

    public EndlessScrollListener(ListController listController) {
        this.listController = listController;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                listController.advanceCurrentPage();
            }
        } else if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            listController.loadNextPage();
            loading = true;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}
