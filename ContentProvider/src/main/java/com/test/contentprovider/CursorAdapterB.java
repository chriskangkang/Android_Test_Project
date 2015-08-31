package com.test.contentprovider;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CursorAdapterB extends CursorAdapter {
	public static String TAG = CursorAdapterB.class.getName();

	private LayoutInflater layoutInflater;

	public CursorAdapterB(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.row_b, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder(view);
			view.setTag(holder);
		}

		B b = DataSrc.cursorToB(cursor);
		if (b != null) {
			holder.textId.setText(b.getId());
			holder.textB1.setText(b.getB1());
			holder.textB2.setText(b.getB2());
		} else {
			Log.e(TAG, "b is null");
		}
	}

	public static class ViewHolder {
		public TextView textId;
		public TextView textB1;
		public TextView textB2;

		public ViewHolder(View convertView) {
			textId = (TextView) convertView.findViewById(R.id.textId);
			textB1 = (TextView) convertView.findViewById(R.id.textB1);
			textB2 = (TextView) convertView.findViewById(R.id.textB2);
		}
	}

}
