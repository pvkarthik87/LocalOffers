package com.kar.localoffers.views.util;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pvkarthik on 2016-01-28.
 */
public abstract class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

	//data to update the view
	private View emptyView;
	private View loadingView;
	private View loadingFailedView; //loading failed
	private boolean isLoading;
	private boolean isLoadFailed;
	private SwipeRefreshLayout swipeRefreshLayout;

	protected void updateEmptyView() {

		boolean isEmpty = isEmptyList();

		View viewToShow = null;
		if (loadingFailedView != null && isLoadFailed) viewToShow = loadingFailedView;
		else if (loadingView != null && (isLoading || isLoadFailed)) viewToShow = loadingView;
		else if (isEmpty) viewToShow = emptyView;

		//Log.d(TAG, String.format("updateEmptyView: showLoading=%b showEmpty=%b isEmpty=%b isLoading=%b loadingView=%b isServer=%b",
		//		showLoading,showEmpty,isEmpty,isLoading,loadingView!=null,this instanceof ServerVideoListAdapter));

		showIfMatches(loadingView, viewToShow);
		showIfMatches(loadingFailedView, viewToShow);
		showIfMatches(emptyView, viewToShow);
	}

	private void showIfMatches(View v, View toShow) {
       /* if (v!=null)
            v.setVisibility(v==toShow?View.VISIBLE:View.INVISIBLE);*/
		if (v == null) return;

		if (v == toShow)
			v.setVisibility(View.VISIBLE);
		else
			v.setVisibility(View.INVISIBLE);
	}

	public void setEmptyView(View emptyView) {
		this.emptyView = emptyView;
		updateEmptyView();
	}

	public void setLoadingView(View loadingView) {
		this.loadingView = loadingView;
		updateEmptyView();
	}

	public void setLoadingFailedView(View loadFailedView) {
		this.loadingFailedView = loadFailedView;
		updateEmptyView();
	}

	public void setLoading(boolean loading) {
		//Log.d(TAG, "setLoading - "+loading+"  server?="+(this instanceof ServerVideoListAdapter));
		this.isLoading = loading;
		if (loading) isLoadFailed = false;
		if (!loading && swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
		updateEmptyView();
	}

	public void setLoadingFailed() {
		this.isLoadFailed = true;
		updateEmptyView();
	}

	public void setSwipeView(SwipeRefreshLayout swipeRefreshLayout) {
		this.swipeRefreshLayout = swipeRefreshLayout;
	}

	public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

	public abstract void onBindViewHolder(T holder, int position);

	public abstract int getItemCount();

	public abstract boolean isEmptyList();

}