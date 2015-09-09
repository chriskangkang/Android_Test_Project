package com.test.contentprovider;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
	private final static String TAG = MainActivity.class.getName();
	private final static int LOADER_TBL_A = 0;
	private final static int LOADER_TBL_B = 1;

	private ListView listviewA;
	private CursorAdapterA cursorAdapterA;
	private ListView listviewB;
	private CursorAdapterB cursorAdapterB;

	private ScheduledThreadPoolExecutor scheduledThreadAdd;
	private ScheduledThreadPoolExecutor scheduledThreadUpdate;
	private ScheduledThreadPoolExecutor scheduledThreadDelete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			DataSrc.initialize(getApplicationContext()).open();
		} catch (SQLiteException e) {
			e.printStackTrace();
		}

		listviewA = (ListView) findViewById(R.id.listViewA);
		cursorAdapterA = new CursorAdapterA(MainActivity.this, null, true);
		listviewA.setAdapter(cursorAdapterA);

		listviewB = (ListView) findViewById(R.id.listViewB);
		cursorAdapterB = new CursorAdapterB(MainActivity.this, null, true);
		listviewB.setAdapter(cursorAdapterB);
	}

	@Override
	protected void onResume() {
		super.onResume();

		Loader<?> oldLoaderA = getLoaderManager().getLoader(LOADER_TBL_A);
		if (oldLoaderA != null) {
			getLoaderManager().destroyLoader(LOADER_TBL_A);
			Log.e(TAG, "getLoaderManager().destroyLoader(LOADER_TBL_A)");
		}
		getLoaderManager().initLoader(LOADER_TBL_A, null, new LoaderManager.LoaderCallbacks<Cursor>() {
			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				CursorLoader cursorLoader = new CursorLoader(MainActivity.this, Provider.CONTENT_URI_TBL_A_DESC, null, null, null, null);
				cursorLoader.setUpdateThrottle(10000);
				return cursorLoader;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
				cursorAdapterA.swapCursor(cursor);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				cursorAdapterA.swapCursor(null);
			}
		});

		Loader<?> oldLoaderB = getLoaderManager().getLoader(LOADER_TBL_B);
		if (oldLoaderB != null) {
			getLoaderManager().destroyLoader(LOADER_TBL_B);
			Log.e(TAG, "getLoaderManager().destroyLoader(LOADER_TBL_B)");
		}
		getLoaderManager().initLoader(LOADER_TBL_B, null, new LoaderManager.LoaderCallbacks<Cursor>() {
			@Override
			public Loader<Cursor> onCreateLoader(int id, Bundle args) {
				CursorLoader cursorLoader = new CursorLoader(MainActivity.this, Provider.CONTENT_URI_TBL_B, TBL_B.ALL_COLUMNS, null, null, null);
				cursorLoader.setUpdateThrottle(500);
				return cursorLoader;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
				cursorAdapterB.swapCursor(cursor);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader) {
				cursorAdapterB.swapCursor(null);
			}
		});

		scheduledThreadAdd = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);
		scheduledThreadAdd.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		scheduledThreadAdd.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		scheduledThreadAdd.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				A a = new A();
				a.setA1("A " + (new Date()).toString());
				a.setA2("A " + System.currentTimeMillis());
				DataSrc.getInstance().insert(MainActivity.this, a);
				B b = new B();
				b.setB1("B " + (new Date()).toString());
				b.setB2("B " + System.currentTimeMillis());
				DataSrc.getInstance().insert(MainActivity.this, b);
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);
		scheduledThreadUpdate = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);
		scheduledThreadUpdate.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		scheduledThreadUpdate.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		scheduledThreadUpdate.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				A a = new A();
				a.setId("1");
				a.setA1("A " + (new Date()).toString());
				a.setA2("A " + System.currentTimeMillis() + "-Update");
				DataSrc.getInstance().update(MainActivity.this, a);
				B b = new B();
				b.setId("1");
				b.setB1("B " + (new Date()).toString());
				b.setB2("B " + System.currentTimeMillis() + "-Update");
				DataSrc.getInstance().update(MainActivity.this, b);
			}
		}, 1000, 1000, TimeUnit.MILLISECONDS);
		scheduledThreadDelete = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);
		scheduledThreadDelete.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
		scheduledThreadDelete.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
		scheduledThreadDelete.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				A a = DataSrc.getInstance().getLastA(MainActivity.this);
				if (a != null) {
					DataSrc.getInstance().delete(MainActivity.this, a);
				}
				B b = DataSrc.getInstance().getLastB(MainActivity.this);
				if (b != null) {
					DataSrc.getInstance().delete(MainActivity.this, b);
				}
			}
		}, 2000, 2000, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Loader<?> oldLoaderA = getLoaderManager().getLoader(LOADER_TBL_A);
		if (oldLoaderA != null) {
			getLoaderManager().destroyLoader(LOADER_TBL_A);
			Log.e(TAG, "getLoaderManager().destroyLoader(LOADER_TBL_A)");
		}
		Loader<?> oldLoaderB = getLoaderManager().getLoader(LOADER_TBL_B);
		if (oldLoaderB != null) {
			getLoaderManager().destroyLoader(LOADER_TBL_B);
			Log.e(TAG, "getLoaderManager().destroyLoader(LOADER_TBL_B)");
		}
		scheduledThreadAdd.shutdown();
		scheduledThreadUpdate.shutdown();
		scheduledThreadDelete.shutdown();
		Log.e(TAG, "scheduledThreadAdd.shutdown()");
		Log.e(TAG, "scheduledThreadUpdate.shutdown()");
		Log.e(TAG, "scheduledThreadDelete.shutdown()");
	}

}
