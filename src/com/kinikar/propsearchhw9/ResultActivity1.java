package com.kinikar.propsearchhw9;

import java.io.InputStream;
import java.util.Locale;

import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.FacebookException;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

public class ResultActivity1 extends ActionBarActivity implements
		ActionBar.TabListener {
	public Intent intent;
	public static Context context = null;
	public static String street ;
	public static String city ;
	public static String state ;
	public static String zip ;
	public static String hyplink ;
	public static String ptype ;
	public static String ybuilt;
	public static String lotsize1;
	public static String finishedarea1;
	public static String bathroom;
	public static String bedrooms;
	public static String tayear;
	public static String ta1;
	public static String lastsoldprice1;
	public static String date1;
	public static String zestimateamt1;
	public static String zestimatedate12;
	public static String thirtydaysoverall;
	public static String thirtydaysoverall12;
	public static String alltimeproprange11;
	public static String alltimeproprange12;
	public static String rentzestimateamt1;
	public static String rentzestimatedate12;
	public static String thirtydaysrent;
	public static String thirtydaysrent12;
	public static String alltimerentrange11;
	public static String alltimerentrange12;
	public static String graph1;
	public static String graph5;
	public static String graph10;
	
	public static int currentGraph;
	
	public static Button img;
	public static ImageView imageSwitcher;
	public static ResultActivity1 parentObj;
	
	private UiLifecycleHelper uiHelper;
	public static String App_id="656734024443826";
	
    

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If context
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_activity1);
		intent = getIntent();
		
		context = getApplicationContext();
		
		
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        	     
		graph1=intent.getStringExtra("graph1");
		graph5=intent.getStringExtra("graph5");
	    graph10=intent.getStringExtra("graph10");
	    
	    currentGraph=0;
	    
	    parentObj = this;
	    
		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do context if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify context Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// context tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	
		
	public static void getNextGraph(int index){
	
			if(index < 1)
				currentGraph = 3;
			else
			if(index > 3)
				currentGraph = 1;
			else
				currentGraph=index;
			
	}
	
	public static String getGraph(){
		if(currentGraph == 1)
			return graph1;
		if(currentGraph == 2)
			return graph5;
		if(currentGraph == 3)
			return graph10;
		
		return null;
	}
	
	public static String getGraphText(){
		if(currentGraph == 1)
			return "Historical Zestimates for the past 1 Year";
		if(currentGraph == 2)
			return "Historical Zestimates for the past 5 Year";
		if(currentGraph == 3)
			return "Historical Zestimates for the past 10 Year";
		
		return null;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; context adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_activity1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			//return PlaceholderFragment.newInstance(position + 1);
			switch (position) {
			case 0: // Fragment # 0 - context will show FirstFragment
			return FirstFragment.newInstance(0,intent);
			case 1: // Fragment # 0 - context will show FirstFragment different title
			return SecondFragment.newInstance(1,intent);
			
			default:
			return null;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "basic info".toUpperCase(l);
			case 1:
				return "historical zestimates".toUpperCase(l);
			
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class FirstFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for context
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		
	    

		/**
		 * Returns a new instance of context fragment for the given section number.
		 */
		public static FirstFragment newInstance(int sectionNumber, Intent intent) {
			FirstFragment fragment = new FirstFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
					 
			 street = intent.getStringExtra("street");
			 city = intent.getStringExtra("city");
			 state = intent.getStringExtra("state");
			 zip = intent.getStringExtra("zip");
			 hyplink = intent.getStringExtra("hyplink");
		    ptype = intent.getStringExtra("ptype");
		     ybuilt=intent.getStringExtra("ybuilt");
			 lotsize1=intent.getStringExtra("lotsize1");
			 finishedarea1=intent.getStringExtra("finishedarea1");
			 bathroom=intent.getStringExtra("bathroom");
			 bedrooms=intent.getStringExtra("bedrooms");
			 tayear=intent.getStringExtra("tayear");
			 ta1=intent.getStringExtra("ta1");
			 lastsoldprice1=intent.getStringExtra("lastsoldprice1");
			 if(lastsoldprice1.equals("")){lastsoldprice1="0.00";}
			 date1=intent.getStringExtra("date1");
			 zestimateamt1=intent.getStringExtra("zestimateamt1");
			 zestimatedate12=intent.getStringExtra("zestimatedate12");
			if(zestimatedate12.equals("")){zestimatedate12 ="N/A";}
			 thirtydaysoverall=intent.getStringExtra("thirtydaysoverall");
			 thirtydaysoverall12=intent.getStringExtra("thirtydaysoverall12");
			 alltimeproprange11=intent.getStringExtra("alltimeproprange11");
			 alltimeproprange12=intent.getStringExtra("alltimeproprange12");
			 rentzestimateamt1=intent.getStringExtra("rentzestimateamt1");
			 rentzestimatedate12=intent.getStringExtra("rentzestimatedate12");
			if(rentzestimatedate12.equals("")){rentzestimatedate12 ="N/A";}
			thirtydaysrent=intent.getStringExtra("thirtydaysrent");
			 thirtydaysrent12=intent.getStringExtra("thirtydaysrent12");
			 alltimerentrange11=intent.getStringExtra("alltimerentrange11");
			 alltimerentrange12=intent.getStringExtra("alltimerentrange12");
			 
		    
			return fragment;
		}

		public FirstFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_result_activity1, container, false);
			

			
			TableLayout t1 = (TableLayout)rootView.findViewById(R.id.main_table1);
			
		    TextView textview1 = (TextView)rootView.findViewById(R.id.moredetails);		   
		    textview1.setTextColor(Color.BLACK);
		    textview1.setPadding(5, 0, 0, 0);
		    textview1.setText("See more details on Zillow:");
		    ImageView imageview1 = (ImageView)rootView.findViewById(R.id.fb);
		    imageview1.setPadding(0, 10, 0, 0);
		    
		    TextView textview2 = (TextView)rootView.findViewById(R.id.link);		    
		    textview2.setPadding(5, 0, 0, 0);
		 	textview2.setText(Html.fromHtml("<a href='"+hyplink+"'><b>"+street+", "+city+", "+state+"-"+zip+"</b></a>"));
		    textview2.setMovementMethod(LinkMovementMethod.getInstance());
		   
		
		    TableRow tr3 = new TableRow(context);
		    tr3.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview3 = new TextView(context);
		    textview3.setTextColor(Color.BLACK);
		    textview3.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview3.setPadding(5, 0, 0, 0);
		    textview3.setText("Property Type");
		    TextView textview4 = new TextView(context);
		    textview4.setTextColor(Color.BLACK);
		    textview4.setPadding(0, 0, 5, 0);
		    textview4.setText(ptype);
		    textview4.setGravity(Gravity.RIGHT);
		    tr3.addView(textview3);
		    tr3.addView(textview4);
		    t1.addView(tr3, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		    TableRow tr4 = new TableRow(context);
		    tr4.setBackgroundColor(Color.WHITE);
		    tr4.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview5 = new TextView(context);
		    textview5.setTextColor(Color.BLACK);
		    textview5.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview5.setPadding(5, 0, 0, 0);
		    textview5.setText("Year Built");
		    TextView textview6 = new TextView(context);
		    textview6.setTextColor(Color.BLACK);
		    textview6.setPadding(0, 0, 5, 0);
		    textview6.setText(ybuilt);
		    textview6.setGravity(Gravity.RIGHT);
		    tr4.addView(textview5);
		    tr4.addView(textview6);
		    t1.addView(tr4, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr5 = new TableRow(context);
		    tr5.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview7 = new TextView(context);
		    textview7.setTextColor(Color.BLACK);
		    textview7.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview7.setPadding(5, 0, 0, 0);
		    textview7.setText("Lot Size");
		    TextView textview8 = new TextView(context);
		    textview8.setPadding(0, 0, 5, 0);
		    textview8.setTextColor(Color.BLACK);
		    if(lotsize1.equals("")){textview8.setText("N/A");}
		    else {textview8.setText(lotsize1+" sq. ft.");}
		    textview8.setGravity(Gravity.RIGHT);
		    tr5.addView(textview7);
		    tr5.addView(textview8);
		    t1.addView(tr5, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr6 = new TableRow(context);
		    tr6.setBackgroundColor(Color.WHITE);
		    tr6.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview9 = new TextView(context);
		    textview9.setTextColor(Color.BLACK);
		    textview9.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview9.setPadding(5, 0, 0, 0);
		    textview9.setText("Finished Area");
		    TextView textview10 = new TextView(context);
		    textview10.setPadding(0, 0, 5, 0);
		    textview10.setTextColor(Color.BLACK);
		    if(finishedarea1.equals("")){textview10.setText("N/A");}
		    else{textview10.setText(finishedarea1+" sq. ft.");}
		    textview10.setGravity(Gravity.RIGHT);
		    tr6.addView(textview9);
		    tr6.addView(textview10);
		    t1.addView(tr6, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr7 = new TableRow(context);
		    tr7.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview11 = new TextView(context);
		    textview11.setTextColor(Color.BLACK);
		    textview11.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview11.setPadding(5, 0, 0, 0);
		    textview11.setText("Bathroom(s)");
		    TextView textview12 = new TextView(context);
		    textview12.setPadding(0, 0, 5, 0);
		    textview12.setTextColor(Color.BLACK);
		    textview12.setText(bathroom);
		    textview12.setGravity(Gravity.RIGHT);
		    tr7.addView(textview11);
		    tr7.addView(textview12);
		    t1.addView(tr7, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr8 = new TableRow(context);
		    tr8.setBackgroundColor(Color.WHITE);
		    tr8.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview13 = new TextView(context);
		    textview13.setTextColor(Color.BLACK);
		    textview13.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview13.setPadding(5, 0, 0, 0);
		    textview13.setText("Bedroom(s)");
		    TextView textview14 = new TextView(context);
		    textview14.setPadding(0, 0, 5, 0);
		    textview14.setTextColor(Color.BLACK);
		    textview14.setText(bedrooms);
		    textview14.setGravity(Gravity.RIGHT);
		    tr8.addView(textview13);
		    tr8.addView(textview14);
		    t1.addView(tr8, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr9 = new TableRow(context);
		    tr9.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview15 = new TextView(context);
		    textview15.setTextColor(Color.BLACK);
		    textview15.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview15.setPadding(5, 0, 0, 0);
		    textview15.setText("Tax Assessment Year");
		    TextView textview16 = new TextView(context);
		    textview16.setPadding(0, 0, 5, 0);
		    textview16.setTextColor(Color.BLACK);
		    if(tayear.equals("")){textview16.setText("N/A");}
		    else{textview16.setText(tayear);}
		    textview16.setGravity(Gravity.RIGHT);
		    tr9.addView(textview15);
		    tr9.addView(textview16);
		    t1.addView(tr9, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr10 = new TableRow(context);
		    tr10.setBackgroundColor(Color.WHITE);
		    tr10.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview17 = new TextView(context);
		    textview17.setTextColor(Color.BLACK);
		    textview17.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview17.setPadding(5, 0, 0, 0);
		    textview17.setText("Tax Assessment");
		    TextView textview18 = new TextView(context);
		    textview18.setPadding(0, 0, 5, 0);
		    textview18.setTextColor(Color.BLACK);
		    if(ta1.equals("")){textview18.setText("N/A");}
		    else{textview18.setText("$"+ta1);}
		    textview18.setGravity(Gravity.RIGHT);
		    tr10.addView(textview17);
		    tr10.addView(textview18);
		    t1.addView(tr10, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr11 = new TableRow(context);
		    tr11.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview19 = new TextView(context);
		    textview19.setTextColor(Color.BLACK);
		    textview19.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview19.setPadding(5, 0, 0, 0);
		    textview19.setText("Last Sold Price");
		    TextView textview20 = new TextView(context);
		    textview20.setPadding(0, 0, 5, 0);
		    textview20.setTextColor(Color.BLACK);
		    if(lastsoldprice1.equals("0.00")){textview20.setText("N/A");}
		    else{textview20.setText("$"+lastsoldprice1);}
		    textview20.setGravity(Gravity.RIGHT);
		    tr11.addView(textview19);
		    tr11.addView(textview20);
		    t1.addView(tr11, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr12 = new TableRow(context);
		    tr12.setBackgroundColor(Color.WHITE);
		    tr12.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview21 = new TextView(context);
		    textview21.setTextColor(Color.BLACK);
		    textview21.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview21.setPadding(5, 0, 0, 0);
		    textview21.setText("Last Sold Date");
		    TextView textview22 = new TextView(context);
		    textview22.setPadding(0, 0, 5, 0);
		    textview22.setTextColor(Color.BLACK);
		    textview22.setText(date1);
		    textview22.setGravity(Gravity.RIGHT);
		    tr12.addView(textview21);
		    tr12.addView(textview22);
		    t1.addView(tr12, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr13 = new TableRow(context);
		    tr13.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview23 = new TextView(context);
		    textview23.setTextColor(Color.BLACK);
		    textview23.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview23.setPadding(5, 0, 0, 0);
		    textview23.setText("Zestimate \u00AE Property Estimate as of "+zestimatedate12);
		    TextView textview24 = new TextView(context);
		    textview24.setPadding(0, 0, 5, 0);
		    textview24.setTextColor(Color.BLACK);
		    if(zestimateamt1.equals("")){textview24.setText("N/A");}
		    else{textview24.setText("$"+zestimateamt1);}
		    textview24.setGravity(Gravity.RIGHT);
		    tr13.addView(textview23);
		    tr13.addView(textview24);
		    t1.addView(tr13, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr14 = new TableRow(context);
		    tr14.setBackgroundColor(Color.WHITE);
		    tr14.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview25 = new TextView(context);
		    textview25.setTextColor(Color.BLACK);
		    textview25.setLayoutParams(new TableRow.LayoutParams(15 , LayoutParams.WRAP_CONTENT, 15));
		    textview25.setPadding(5, 0, 0, 0);
		    textview25.setText("30 Days Overall Change");
		    ImageView imageview26 = new ImageView(context);
		    
		    TextView textview26 = new TextView(context);
		    textview26.setPadding(0, 0, 5, 0);
		    textview26.setTextColor(Color.BLACK);		    
		    if(thirtydaysoverall12.equals("0.00")){textview26.setText("N/A");}
		    else{
		    	int thirtydaysoverallx=Integer.parseInt(thirtydaysoverall);
		    	if(thirtydaysoverallx<0){
		        imageview26.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT,1));		        
		        imageview26.setImageDrawable(getResources().getDrawable(R.drawable.red));
		    	textview26.setText("$"+thirtydaysoverall12);
		    	}
		    	else{
		    		imageview26.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT,1));
		    	    imageview26.setImageDrawable(getResources().getDrawable(R.drawable.green));
			    	textview26.setText("$"+thirtydaysoverall12);
		    	}
		    }
		    textview26.setGravity(Gravity.RIGHT);
		    tr14.addView(textview25);
		    tr14.addView(imageview26);
		    tr14.addView(textview26);
		    t1.addView(tr14, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr15 = new TableRow(context);
		    tr15.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview27 = new TextView(context);
		    textview27.setTextColor(Color.BLACK);
		    textview27.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview27.setPadding(5, 0, 0, 0);
		    textview27.setText("All Time Property Range");
		    TextView textview28 = new TextView(context);
		    textview28.setPadding(0, 0, 5, 0);
		    textview28.setTextColor(Color.BLACK);
		    if(alltimeproprange11.equals("") || alltimeproprange12.equals("") ){textview28.setText("N/A");}
		    else{textview28.setText("$"+alltimeproprange11+" - $"+alltimeproprange12);}
		    textview28.setGravity(Gravity.RIGHT);
		    tr15.addView(textview27);
		    tr15.addView(textview28);
		    t1.addView(tr15, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr16 = new TableRow(context);
		    tr16.setBackgroundColor(Color.WHITE);
		    tr16.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview29 = new TextView(context);
		    textview29.setTextColor(Color.BLACK);
		    textview29.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview29.setPadding(5, 0, 0, 0);
		    textview29.setText("Rent Zestimate \u00AE Valuation as of "+rentzestimatedate12);
		    TextView textview30 = new TextView(context);
		    textview30.setPadding(0, 0, 5, 0);
		    textview30.setTextColor(Color.BLACK);
		    if(rentzestimateamt1.equals("")){textview30.setText("N/A");}
		    else{textview30.setText("$"+rentzestimateamt1);}
		    textview30.setGravity(Gravity.RIGHT);
		    tr16.addView(textview29);
		    tr16.addView(textview30);
		    t1.addView(tr16, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr17 = new TableRow(context);
		    tr17.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview31 = new TextView(context);
		    textview31.setTextColor(Color.BLACK);
		    textview31.setLayoutParams(new TableRow.LayoutParams(40 , LayoutParams.WRAP_CONTENT,40));
		    textview31.setPadding(5, 0, 0, 0);
		    textview31.setText("30 Days Rent Change");
		    ImageView imageview27 = new ImageView(context);
		    TextView textview32 = new TextView(context);
		    textview32.setPadding(0, 0, 5, 0);
		    textview32.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		    textview32.setTextColor(Color.BLACK);
		    if(thirtydaysrent12.equals("0.00")){textview32.setText("N/A");}
		    else{
		    	int thirtydaysrentx=Integer.parseInt(thirtydaysrent);
		    	if(thirtydaysrentx<0){
		        imageview27.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT,1));
		        imageview27.setImageDrawable(getResources().getDrawable(R.drawable.red));
		        textview32.setText("$"+thirtydaysrent12);
		    	}
		    	else{
		    		imageview27.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT,1));
		    	    imageview27.setImageDrawable(getResources().getDrawable(R.drawable.green));
		    		textview32.setText("$"+thirtydaysrent12);
		    	}
		    }
		    textview32.setGravity(Gravity.RIGHT);
		    tr17.addView(textview31);
		    tr17.addView(imageview27);
		    tr17.addView(textview32);
		    t1.addView(tr17, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TableRow tr18 = new TableRow(context);
		    tr18.setBackgroundColor(Color.WHITE);
		    tr18.setLayoutParams(new LayoutParams( LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		    TextView textview33 = new TextView(context);
		    textview33.setTextColor(Color.BLACK);
		    textview33.setLayoutParams(new TableRow.LayoutParams(0 , LayoutParams.WRAP_CONTENT, 1));
		    textview33.setPadding(5, 0, 0, 0);
		    textview33.setText("All Time Rent Range");
		    TextView textview34 = new TextView(context);
		    textview34.setPadding(0, 0, 5, 0);
		    textview34.setTextColor(Color.BLACK);
		    if(alltimerentrange11.equals("") || alltimerentrange12.equals("")){textview34.setText("N/A");}
		    else{textview34.setText("$"+alltimerentrange11+" - $"+alltimerentrange12);}
		    textview34.setGravity(Gravity.RIGHT);
		    tr18.addView(textview33);
		    tr18.addView(textview34);
		    t1.addView(tr18, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		    
		    TextView disc1 = (TextView)rootView.findViewById(R.id.disclaimer1);
		    disc1.setText("\u00A9 Zillow, Inc., 2006-2014");
		    TextView disc2 = (TextView)rootView.findViewById(R.id.disclaimer2);
		    disc2.setText(Html.fromHtml("Use is subject to "+"<a href='http://www.zillow.com/corp/Terms.htm'>Terms of Use</a>"));
		    disc2.setMovementMethod(LinkMovementMethod.getInstance());
		    TextView disc3 = (TextView)rootView.findViewById(R.id.disclaimer3);
		    disc3.setText(Html.fromHtml("<a href='http://www.zillow.com/zestimate/'>What's a Zestimate</a>"));
		    disc3.setMovementMethod(LinkMovementMethod.getInstance());
			
			return rootView;
		}
	}
	public static class SecondFragment extends Fragment {

		/**
		 * The fragment argument representing the section number for context
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of context fragment for the given section number.
		 */
		public static SecondFragment newInstance(int sectionNumber, Intent intent) {
			SecondFragment fragment = new SecondFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
		}

		public SecondFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_result_activity2, container, false);
			
			

		      
		      TextView disc1 = (TextView)rootView.findViewById(R.id.tv11);
			    disc1.setText("\u00A9 Zillow, Inc., 2006-2014");
			    TextView disc2 = (TextView)rootView.findViewById(R.id.tv22);
			    disc2.setText(Html.fromHtml("Use is subject to "+"<a href='http://www.zillow.com/corp/Terms.htm'>Terms of Use</a>"));
			    disc2.setMovementMethod(LinkMovementMethod.getInstance());
			    TextView disc3 = (TextView)rootView.findViewById(R.id.tv33);
			    disc3.setText(Html.fromHtml("<a href='http://www.zillow.com/zestimate/'>What's a Zestimate</a>"));
			    disc3.setMovementMethod(LinkMovementMethod.getInstance());
            
			    
			      getNextGraph(currentGraph+1);
			      (parentObj).new DownloadImageTask((ImageView) rootView.findViewById(R.id.imageSwitcher1))
		          .execute(getGraph()); 
				   TextView head = (TextView) rootView.findViewById(R.id.head1);
				   head.setText(getGraphText());
				   
				   TextView head2 = (TextView) rootView.findViewById(R.id.head2);
				   head2.setText(street+", "+city+", "+state+"-"+zip);
					          
		   	return rootView;
		}
	
		   
	}

    
	public  void next(View v1){
	      Toast.makeText(context, "Next Image", 
	      Toast.LENGTH_LONG).show();
	      getNextGraph(currentGraph+1);
	       new DownloadImageTask((ImageView) findViewById(R.id.imageSwitcher1))
          .execute(getGraph()); 
		   TextView head = (TextView) findViewById(R.id.head1);
		   head.setText(getGraphText());
		   
	   }
	   public  void previous(View v2){
	      Toast.makeText(context, "Previous Image", 
	      Toast.LENGTH_LONG).show();
	      getNextGraph(currentGraph-1);
	      new DownloadImageTask((ImageView) findViewById(R.id.imageSwitcher1))
          .execute(getGraph());
	      TextView head = (TextView) findViewById(R.id.head1);
	      head.setText(getGraphText());
	   }
	   public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	        ImageView bmImage;

	        public DownloadImageTask(ImageView bmImage) {
	            this.bmImage = bmImage;
	        }

	        protected Bitmap doInBackground(String... urls) {
	            String urldisplay = urls[0];
	            Bitmap mIcon11 = null;
	            try {
	                InputStream in = new java.net.URL(urldisplay).openStream();
	                mIcon11 = BitmapFactory.decodeStream(in);
	            } catch (Exception e) {
	                Log.e("Error", e.getMessage());
	                e.printStackTrace();
	            }
	            return mIcon11;
	        }

	        protected void onPostExecute(Bitmap result) {
	            bmImage.setImageBitmap(result);
	            
	        }

	    }  
	   

	   @Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	       super.onActivityResult(requestCode, resultCode, data);

	       uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	           @Override
	           public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	               Log.e("Activity", String.format("Error: %s", error.toString()));
	           }

	           @Override
	           public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	               Log.i("Activity", "Success!");
	           }
	       });
	   }
	   
	   @Override
	   protected void onResume() {
	       super.onResume();
	       uiHelper.onResume();
	   }

	   @Override
	   protected void onSaveInstanceState(Bundle outState) {
	       super.onSaveInstanceState(outState);
	       uiHelper.onSaveInstanceState(outState);
	   }

	   @Override
	   public void onPause() {
	       super.onPause();
	       uiHelper.onPause();
	   }

	   @Override
	   public void onDestroy() {
	       super.onDestroy();
	       uiHelper.onDestroy();
	   }
	   
	   
	   public void onClickFB(View v){
		   System.out.println("FB entered");
       	final Dialog fbdialog = new Dialog(ResultActivity1.this);
			fbdialog.setContentView(R.layout.fbpost);
			Button fbpost = (Button)fbdialog.findViewById(R.id.fbpost);
			Button fbcancel = (Button)fbdialog.findViewById(R.id.fbcancel);
			fbdialog.setTitle("Post to Facebook");
			
			
			fbpost.setOnClickListener(new View.OnClickListener() {
				   
                   @Override
                   public void onClick(View view) {
                       // TODO Auto-generated method stub
                	   if (Session.getActiveSession() == null || !Session.getActiveSession().isOpened()) {
           		        Session.openActiveSession(ResultActivity1.this, true, callback);
           		    } else {
           		        publishFeedDialog();
           		     fbdialog.dismiss();
           		    }
                   	//Toast.makeText(getApplicationContext(), "You clicked on Post", Toast.LENGTH_SHORT).show();
                   	
                   	
                   }

               });
               fbcancel.setOnClickListener(new View.OnClickListener() {

                   @Override
                   public void onClick(View view) {
                       // TODO Auto-generated method stub
                   	Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
                       fbdialog.dismiss();

                   }
               });
               fbdialog.show();
		
		}
			
		   
	   
	   
	   private Session.StatusCallback callback = new Session.StatusCallback() {
			
			@Override
			public void call(Session session, SessionState state, Exception exception) {
				
				if (state.isOpened()) {
					publishFeedDialog();
				}
			}
		};

	   private void publishFeedDialog() {
		   
		    Bundle params = new Bundle();

    	    params.putString("name", street+", "+city+", "+state+"-"+zip);
    	    params.putString("caption", "Property Information from Zillow.com");
    	    params.putString("description","Last Sold Price: $"+lastsoldprice1+", 30 Days Overall Change: $"+thirtydaysoverall12);
    	    params.putString("link",hyplink);
    	    params.putString("picture",graph1);

		    WebDialog feedDialog = (
		        new WebDialog.FeedDialogBuilder(this,
		            Session.getActiveSession(),
		            params))
		        .setOnCompleteListener(new OnCompleteListener() {

		            @Override
		            public void onComplete(Bundle values,
		                FacebookException error) {
		                if (error == null) {
		                    // When the story is posted, echo the success
		                    // and the post Id.
		                    final String postId = values.getString("post_id");
		                    if (postId != null) {
		                        Toast.makeText(context,
		                            "Posted Story, Id: "+postId,
		                            Toast.LENGTH_SHORT).show();
		                    } else {
		                        // User clicked the Cancel button
		                        Toast.makeText(context.getApplicationContext(), 
		                            "Post cancelled", 
		                            Toast.LENGTH_SHORT).show();
		                    }
		                } else if (error instanceof FacebookOperationCanceledException) {
		                    // User clicked the "x" button
		                    Toast.makeText(context.getApplicationContext(), 
		                        "Post cancelled", 
		                        Toast.LENGTH_SHORT).show();
		                } else {
		                    // Generic, ex: network error
		                    Toast.makeText(context.getApplicationContext(), 
		                        "Error posting story", 
		                        Toast.LENGTH_SHORT).show();
		                }
		            }

		        })
		        .build();
		    feedDialog.show();
		}
}
