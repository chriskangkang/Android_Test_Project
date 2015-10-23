package com.test.slidingtabbasic.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.test.slidingtabbasic.R;

public class MainActivity extends FragmentActivity {

	public static final String TAG = MainActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, new SlidingTabsBasicFragment()).commit();
		}
	}
}
