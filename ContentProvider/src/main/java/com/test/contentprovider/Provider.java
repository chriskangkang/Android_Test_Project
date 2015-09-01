package com.test.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class Provider extends ContentProvider {
	public static String TAG = Provider.class.getName();
	public static final String AUTHORITY = "ContentProvider.DataSrc";
	public static final String CONTENT_URI = "content://" + AUTHORITY + "/";

	private static final String TBL_A_DESC = TBL_A.NAME + "/DESC";
	public static final Uri CONTENT_URI_TBL_A = Uri.parse(CONTENT_URI + TBL_A.NAME);
	public static final Uri CONTENT_URI_TBL_A_DESC = Uri.parse(CONTENT_URI + TBL_A_DESC);

	public static final Uri CONTENT_URI_TBL_B = Uri.parse(CONTENT_URI + TBL_B.NAME);

	private static final int CODE_ALL_A = 0;
	private static final int CODE_ALL_B = 1;
	private static final int CODE_A_DESC = 2;

	private final UriMatcher mUriMatcher;

	public Provider() {
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(AUTHORITY, TBL_A.NAME, CODE_ALL_A);
		mUriMatcher.addURI(AUTHORITY, TBL_B.NAME, CODE_ALL_B);
		mUriMatcher.addURI(AUTHORITY, TBL_A_DESC, CODE_A_DESC);
	}

	@Override
	public boolean onCreate() {
		Log.e(TAG, "onCreate");
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.i(TAG, "query URI: " + uri);
		String tableName = "";
		switch (mUriMatcher.match(uri)) {
			case CODE_ALL_A:
				tableName = TBL_A.NAME;
				projection = TBL_A.ALL_COLUMNS;
				break;
			case CODE_ALL_B:
				tableName = TBL_B.NAME;
				projection = TBL_B.ALL_COLUMNS;
				break;
			case CODE_A_DESC:
				tableName = TBL_A.NAME + " AS " + TBL_A.NAME + " INNER JOIN " + TBL_B.NAME + " AS " + TBL_B.NAME
						+ " ON (" + TBL_A.NAME + "." + TBL_A.COLUMN_ID + "=" + TBL_B.NAME + "." + TBL_B.COLUMN_ID + ")";
				String[] c = new String[TBL_A.ALL_COLUMNS.length + TBL_B.ALL_COLUMNS.length];
				int pos = 0;
				for (String element : TBL_A.ALL_COLUMNS) {
					c[pos] = TBL_A.NAME + "." + element;
					pos++;
				}
				for (String element : TBL_B.ALL_COLUMNS) {
					c[pos] = TBL_B.NAME + "." + element;
					pos++;
				}
				projection = c;
				selection = TBL_A.NAME + "." + TBL_A.COLUMN_ID + ">=1";
				sortOrder = TBL_A.NAME + "." + TBL_A.COLUMN_ID + " DESC";
				break;
			default:
				Log.e(TAG, "query Unknown URI " + uri);
		}
		Cursor c = DataSrc.getInstance().getDatabase().query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
		if (c != null) {
			c.setNotificationUri(getContext().getContentResolver(), uri);
		}
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.i(TAG, "insert URI: " + uri);
		String tableName = "";
		switch (mUriMatcher.match(uri)) {
			case CODE_ALL_A:
				tableName = TBL_A.NAME;
				break;
			case CODE_ALL_B:
				tableName = TBL_B.NAME;
				break;
			default:
				Log.e(TAG, "insert Unknown URI " + uri);
				return null;
		}

		long count = DataSrc.getInstance().getDatabase().insert(tableName, "", values);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
			return uri;
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		Log.i(TAG, "update URI: " + uri);
		String tableName = "";
		switch (mUriMatcher.match(uri)) {
			case CODE_ALL_A:
				tableName = TBL_A.NAME;
				break;
			case CODE_ALL_B:
				tableName = TBL_B.NAME;
				break;
			default:
				Log.e(TAG, "update Unknown URI " + uri);
				return 0;
		}
		int count = DataSrc.getInstance().getDatabase().update(tableName, values, selection, selectionArgs);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.i(TAG, "delete URI: " + uri);
		String tableName = "";
		switch (mUriMatcher.match(uri)) {
			case CODE_ALL_A:
				tableName = TBL_A.NAME;
				break;
			case CODE_ALL_B:
				tableName = TBL_B.NAME;
				break;
			default:
				Log.e(TAG, "delete Unknown URI " + uri);
				return 0;
		}
		int count = DataSrc.getInstance().getDatabase().delete(tableName, selection, selectionArgs);
		if (count > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return count;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

}
