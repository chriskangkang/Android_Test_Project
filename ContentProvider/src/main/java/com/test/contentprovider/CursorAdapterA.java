package com.test.contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CursorAdapterA extends CursorAdapter {
	public static String TAG = CursorAdapterA.class.getName();

	private LayoutInflater layoutInflater;

	public CursorAdapterA(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.row_a, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final A a;
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		a = DataSrc.cursorToA(cursor);

		holder.textId.setText(a.getId());
		holder.textA1.setText(a.getA1());
		holder.textA2.setText(a.getA2());
	}

	public static class ViewHolder {
		public TextView textId;
		public TextView textA1;
		public TextView textA2;

		public ViewHolder(View convertView) {
			textId = (TextView) convertView.findViewById(R.id.textId);
			textA1 = (TextView) convertView.findViewById(R.id.textA1);
			textA2 = (TextView) convertView.findViewById(R.id.textA2);
		}
	}

}
