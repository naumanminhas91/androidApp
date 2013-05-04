package com.carouseldemo.main;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecurityActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security);
		
		Button b1= (Button) findViewById(R.id.button_sec_1);
		
		b1.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneCallUri = "tel:042111115867";
			    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			    phoneCallIntent.setData(Uri.parse(phoneCallUri));
			    startActivity(phoneCallIntent);
			}
		});
		
		Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        
		TextView txt = (TextView) findViewById(R.id.sec_dial);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.security);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.sec1);  
        txt.setTypeface(font);
	}


}
