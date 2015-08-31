package com.test.contentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DataSrc {
	// Database fields
	private SQLiteDatabase database;
	private static DBHelper dbHelper;
	private static DataSrc mInstance;

	public static String TAG = DataSrc.class.getName();

	// public static final String AUTHORITY = "com.test.data.DataSrc";

	public static DataSrc initialize(Context context) {
		if (mInstance == null) {
			mInstance = new DataSrc(context);
		}
		return mInstance;
	}

	public static DataSrc getInstance() {
		return mInstance;
	}

	private DataSrc(Context context) {
		if (dbHelper == null)
			dbHelper = DBHelper.getInstance(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void update() {
		dbHelper.onUpgrade(database, 0, 1);
	}

	public void beginTransaction() {
		database.beginTransactionNonExclusive();
	}

	public void setTransactionSuccessful() {
		database.setTransactionSuccessful();
	}

	public void endTransaction() {
		database.endTransaction();
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public DBHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(DBHelper dbHelper) {
		DataSrc.dbHelper = dbHelper;
	}

	private ContentValues getContentValues(A a) {
		ContentValues values = new ContentValues();
		values.put(TBL_A.COLUMN_ID, a.getId());
		values.put(TBL_A.COLUMN_A1, a.getA1());
		values.put(TBL_A.COLUMN_A2, a.getA2());
		return values;
	}

	public Uri insert(Activity activity, A a) {
		return activity.getContentResolver().insert(Provider.CONTENT_URI_TBL_A, getContentValues(a));
	}

	public int update(Activity activity, A a) {
		return activity.getContentResolver().update(Provider.CONTENT_URI_TBL_A, getContentValues(a), TBL_A.COLUMN_ID + "=?", new String[]{a.getId()});
	}

	public int delete(Activity activity, A a) {
		return activity.getContentResolver().delete(Provider.CONTENT_URI_TBL_A, TBL_A.COLUMN_ID + "=?", new String[]{a.getId()});
	}

	public A getLastA(Activity activity) {
		A a = null;
		Cursor c = activity.getContentResolver().query(Provider.CONTENT_URI_TBL_A, TBL_A.ALL_COLUMNS, null, null, TBL_A.COLUMN_ID + " DESC");
		if (c != null) {
			if (c.moveToFirst()) {
				a = cursorToA(c);
			}
			c.close();
		}
		return a;
	}

	public static A cursorToA(Cursor c) {
		A a = new A();
		a.setId(c.getString(c.getColumnIndex(TBL_A.COLUMN_ID)));
		a.setA1(c.getString(c.getColumnIndex(TBL_A.COLUMN_A1)));
		a.setA2(c.getString(c.getColumnIndex(TBL_A.COLUMN_A2)));
		return a;
	}

	private ContentValues getContentValues(B b) {
		ContentValues values = new ContentValues();
		values.put(TBL_B.COLUMN_ID, b.getId());
		values.put(TBL_B.COLUMN_B1, b.getB1());
		values.put(TBL_B.COLUMN_B2, b.getB2());
		return values;
	}

	public Uri insert(Activity activity, B b) {
		return activity.getContentResolver().insert(Provider.CONTENT_URI_TBL_B, getContentValues(b));
	}

	public int update(Activity activity, B b) {
		return activity.getContentResolver().update(Provider.CONTENT_URI_TBL_B, getContentValues(b), TBL_B.COLUMN_ID + "=?", new String[]{b.getId()});
	}

	public int delete(Activity activity, B b) {
		return activity.getContentResolver().delete(Provider.CONTENT_URI_TBL_B, TBL_B.COLUMN_ID + "=?", new String[]{b.getId()});
	}

	public B getLastB(Activity activity) {
		B b = null;
		Cursor c = activity.getContentResolver().query(Provider.CONTENT_URI_TBL_B, TBL_B.ALL_COLUMNS, null, null, TBL_B.COLUMN_ID + " DESC");
		if (c.moveToFirst()) {
			b = cursorToB(c);
		}
		c.close();
		return b;
	}

	public static B cursorToB(Cursor c) {
		B b = new B();
		b.setId(c.getString(c.getColumnIndex(TBL_B.COLUMN_ID)));
		b.setB1(c.getString(c.getColumnIndex(TBL_B.COLUMN_B1)));
		b.setB2(c.getString(c.getColumnIndex(TBL_B.COLUMN_B2)));
		return b;
	}

}
