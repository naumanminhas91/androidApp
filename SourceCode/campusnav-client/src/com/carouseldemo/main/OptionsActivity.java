package com.carouseldemo.main;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class OptionsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        
		TextView txt = (TextView) findViewById(R.id.textView1);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView2);  
        txt.setTypeface(font);
	}



}
