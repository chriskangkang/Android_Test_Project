package com.test.slidingtabbasic.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.test.slidingtabbasic.R;
import com.test.slidingtabbasic.common.view.SlidingTabLayout;

public class SlidingTabsBasicFragment extends Fragment {

	static final String LOG_TAG = SlidingTabsBasicFragment.class.getName();

	private SlidingTabLayout slidingTabLayout;
	private ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_sample, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// Get the ViewPager and set it's PagerAdapter so that it can display items
		viewPager = (ViewPager) view.findViewById(R.id.viewpager);
		viewPager.setAdapter(new SamplePagerAdapter(getActivity().getApplicationContext()));

		// Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had it's PagerAdapter set.
		slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
		slidingTabLayout.setViewPager(viewPager);
		slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tabTextView);

	}


}
