package com.test.contentprovider;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = DBHelper.class.getSimpleName();
	private static final String DATABASE_NAME = "test";
	private static final int DATABASE_VERSION = 1;
	private static DBHelper mInstance;

	public static synchronized DBHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBHelper(context);
		}
		return mInstance;
	}

	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.setWriteAheadLoggingEnabled(true);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(TAG, "onCreate DB");
		db.beginTransaction();
		try {
			db.execSQL(TBL_A.TABLE_CREATE_SQL);
			db.execSQL(TBL_B.TABLE_CREATE_SQL);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DBHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ". Old data will be destroyed");
		db.execSQL("DROP TABLE IF EXISTS " + TBL_A.NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TBL_B.NAME);
		onCreate(db);
	}

	@Override
	public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase db = super.getWritableDatabase();
		db.enableWriteAheadLogging();
		return db;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		// Enable foreign key constrains
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}

}
