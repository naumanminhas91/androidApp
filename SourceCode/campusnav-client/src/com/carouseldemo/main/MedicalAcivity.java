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

public class MedicalAcivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical_acivity);
		
		final String[] phones = getResources().getStringArray(R.array.med_number);
		
		Button b0=(Button) findViewById(R.id.button6);
		b0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String phoneCallUri = "tel:042111115867";
			    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			    phoneCallIntent.setData(Uri.parse(phoneCallUri));
			    startActivity(phoneCallIntent);
			}
		});
		

		Button b1=(Button) findViewById(R.id.button1);
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String phoneCallUri = "tel:03453053692";
			    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			    phoneCallIntent.setData(Uri.parse(phoneCallUri));
			    startActivity(phoneCallIntent);
			}
		});
		

		Button b2=(Button) findViewById(R.id.button2);
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String phoneCallUri = "tel:03244495820";
			    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			    phoneCallIntent.setData(Uri.parse(phoneCallUri));
			    startActivity(phoneCallIntent);
			}
		});
		

		Button b3=(Button) findViewById(R.id.button3);
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String phoneCallUri = "tel:03333588329";
			    Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
			    phoneCallIntent.setData(Uri.parse(phoneCallUri));
			    startActivity(phoneCallIntent);
			}
		});
		
		
		Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        
		TextView txt = (TextView) findViewById(R.id.textView1);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView2);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView3);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView6);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView5);  
        txt.setTypeface(font);

        txt = (TextView) findViewById(R.id.textView4);  
        txt.setTypeface(font);
	}



}
