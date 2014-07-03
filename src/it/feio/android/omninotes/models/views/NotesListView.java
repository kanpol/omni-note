package it.feio.android.omninotes.models.views;

import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.models.adapters.NoteAdapter;
import it.feio.android.omninotes.models.listeners.ListViewEndlessListener;
import java.util.List;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class NotesListView extends ListView implements OnScrollListener {

	private View footer;
	private boolean isLoading;
	private NoteAdapter adapter;
	private ListViewEndlessListener mListViewEndlessListener;


	public NotesListView(Context context) {
		super(context);	
		this.setOnScrollListener(this);
	}


	public NotesListView(Context context, AttributeSet attrs) {
		super(context, attrs);	
		this.setOnScrollListener(this);
	}


	public NotesListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);	
		this.setOnScrollListener(this);
	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (adapter == null) return;

		if (adapter.getCount() == 0) return;

		int l = visibleItemCount + firstVisibleItem;
		if (l >= totalItemCount && !isLoading) {
			// It is time to add new data. We call the listener
			this.addFooterView(footer);
			isLoading = true;
			mListViewEndlessListener.loadData();
		}
	}


	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {}


	public void setLoadingView(int resId) {
		LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footer = (View) inflater.inflate(resId, null);
		this.addFooterView(footer);

	}


	public void setAdapter(NoteAdapter adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
		this.removeFooterView(footer);
	}


	public void addNewData(List<Note> data) {

		this.removeFooterView(footer);

		adapter.addAll(data);
		adapter.notifyDataSetChanged();
		isLoading = false;
	}


	public ListViewEndlessListener getListViewEndlessListener() {
		return mListViewEndlessListener;
	}


	public void setListViewEndlessListener(ListViewEndlessListener listener) {
		this.mListViewEndlessListener = listener;
	}
}
