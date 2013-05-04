package com.carouseldemo.main;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class EmergencyActivity extends TabActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency);
		
		TabHost tabHost= getTabHost();
		
		TabSpec medicSpec= tabHost.newTabSpec("Medical");
		medicSpec.setIndicator("Medical", getResources().getDrawable(R.drawable.emergency));
		Intent medicIntent= new Intent( this, MedicalAcivity.class);
		medicSpec.setContent(medicIntent);
		
		TabSpec secSpec= tabHost.newTabSpec("Security");
		secSpec.setIndicator("Security", getResources().getDrawable(R.drawable.emergency));
		Intent secIntent= new Intent( this, SecurityActivity.class);
		secSpec.setContent(secIntent);
		
		tabHost.addTab(medicSpec);
		tabHost.addTab(secSpec);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        
		TextView txt = (TextView) findViewById(R.id.pageTitle);  
        txt.setTypeface(font);
        
	
	}
	
	
	
}
