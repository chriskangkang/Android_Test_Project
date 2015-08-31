package com.test.contentprovider;

public class TBL_A {

	public static final String TAG = TBL_A.class.getSimpleName();
	public static final String NAME = "tbl_a";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_A1 = "a1";
	public static final String COLUMN_A2 = "a2";

	public static String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_A1, COLUMN_A2 };

	public static final String TABLE_CREATE_SQL = "CREATE TABLE "
			+ NAME + "( "
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_A1 + " TEXT NOT NULL, "
			+ COLUMN_A2 + " TEXT NOT NULL" + ")";
}
