package com.test.contentprovider;

public class TBL_B {

	public static final String TAG = TBL_B.class.getSimpleName();
	public static final String NAME = "tbl_b";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_B1 = "b1";
	public static final String COLUMN_B2 = "b2";

	public static String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_B1, COLUMN_B2 };

	public static final String TABLE_CREATE_SQL = "CREATE TABLE "
			+ NAME + "( "
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_B1 + " TEXT NOT NULL, "
			+ COLUMN_B2 + " TEXT NOT NULL" + ")";
}
