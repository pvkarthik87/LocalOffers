package com.kar.localoffers.views.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pvkarthik on 2016-01-20.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
{
	public static interface OnRecyclerItemClickListener
	{
		public void onItemClick(View view, int position);
		public void onItemLongClick(View view, int position);
	}

	private OnRecyclerItemClickListener mListener;
	private GestureDetector mGestureDetector;

	public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnRecyclerItemClickListener listener)
	{
		mListener = listener;

		mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
		{
			@Override
			public boolean onSingleTapUp(MotionEvent e)
			{
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e)
			{
				View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

				if(childView != null && mListener != null)
				{
					mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
				}
			}
		});
	}

	@Override
	public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
	{
		View childView = view.findChildViewUnder(e.getX(), e.getY());

		if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
		{
			mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
		}

		return false;
	}

	@Override
	public void onTouchEvent(RecyclerView view, MotionEvent motionEvent){}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

	}
}
