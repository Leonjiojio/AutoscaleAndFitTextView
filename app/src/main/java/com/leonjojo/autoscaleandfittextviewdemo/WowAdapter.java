package com.leonjojo.autoscaleandfittextviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class WowAdapter<E> extends BaseAdapter {

	private Context context;
	private List<E> data;
	private LayoutInflater inflater;


	public WowAdapter(Context context) {
		super();
		setContext(context);
		data=new ArrayList<>();
		inflater= LayoutInflater.from(context);

	}

	public WowAdapter(Context context, List<E> data) {
		super();
		setContext(context);
		this.data=data;
		inflater= LayoutInflater.from(context);

	}
	protected final LayoutInflater getInflater() {
		return inflater;
	}

	public void setData(List<E> cacheList) {
		if (cacheList == null || cacheList.isEmpty()) {
			return;
		}
		data.clear();
		data.addAll(cacheList);
		notifyDataSetChanged();
	}

	private void setContext(Context context) {
		if (null==context) {
			throw new IllegalArgumentException("Context can not be none!!!!!");
		}else {
			this.context=context;
		}
	}
	
	protected final Context getContext() {
		return context;
	}
	
	protected final List<E> getData() {
		return data;
	}
	

	@Override
	public E getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

}
