package com.test.objectobserver;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

public class ObjectObserverPattern extends Activity implements Observer, OnClickListener {
	private static final String TAG = ObjectObserverPattern.class.getName();
	private Button btn;
	private ObservableObject mObservableObject;
	private int count = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btn = (Button) findViewById(R.id.btn);

//		ObservableObject.initialize();
		mObservableObject = ObservableObject.getInstance();
		mObservableObject.addObserver(this);
		mObservableObject.setValue(String.valueOf(count++));

		btn.setText("value: " + mObservableObject.getValue());
		btn.setOnClickListener(this);
	}

	@Override
	public void update(Observable observable, Object data) {
		Log.e(TAG, "update");
		// This method is notified after data changes.
		Toast.makeText(this, "I am notified: " + mObservableObject.getValue(), Toast.LENGTH_SHORT).show();
		btn.setText("value: " + mObservableObject.getValue());
	}

	@Override
	public void onClick(View v) {
		Log.e(TAG, "onClick");
		mObservableObject.setValue(String.valueOf(count++));
	}
}
