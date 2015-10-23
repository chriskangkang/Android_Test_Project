package com.test.slidingtabbasic.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.test.slidingtabbasic.R;

class SamplePagerAdapter extends PagerAdapter {

	static final String LOG_TAG = SamplePagerAdapter.class.getName();
	private Context context;
	private LayoutInflater layoutInflater;

	public SamplePagerAdapter(Context context) {
		this.context = context;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return o == view;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Tab " + (position + 1);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// Inflate a new layout from our resources
		View view = layoutInflater.inflate(R.layout.pager_item, container, false);
		// Add the newly created View to the ViewPager
		container.addView(view);

		// Retrieve a TextView from the inflated View, and update it's text
		TextView title = (TextView) view.findViewById(R.id.pageContent);
		title.setText("Page: "+String.valueOf(position + 1));

		Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
		Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
	}

}