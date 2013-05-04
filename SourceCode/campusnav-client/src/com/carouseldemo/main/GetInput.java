package com.carouseldemo.main;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.hellomap.MainActivity;
import com.example.hellomap.ServerCommunication;

public class GetInput extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_input);
		
		//auto complete suggestions
		//locations[0] =  "...";

		// Get a reference to the AutoCompleteTextView in the layout
		final AutoCompleteTextView textViewSrc = (AutoCompleteTextView) findViewById(R.id.autocomplete_source);
		
		// Create the adapter and set it to the AutoCompleteTextView 
		ArrayAdapter<String> adapterSrc = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.locations);
		textViewSrc.setAdapter(adapterSrc);
		
		final AutoCompleteTextView textViewDest = (AutoCompleteTextView) findViewById(R.id.autocomplete_dest);
		ArrayAdapter<String> adapterDest = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, MainActivity.locations);
		textViewDest.setAdapter(adapterDest);
		
		
      
      Button submitl = (Button) findViewById(R.id.submitx);
      
      submitl.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("Src", textViewSrc.getText().toString());
				intent.putExtra("Dest", textViewDest.getText().toString());
				//startActivity(intent);
				
				Log.i("in get input src:dest",textViewSrc.getText().toString()+":"+textViewDest.getText().toString());
				setResult(100,intent);
				
				finish();
				
			}
		
      });
		
      
      Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
      
      TextView txt = (TextView) findViewById(R.id.textView_dest);  
      txt.setTypeface(font);
      
      txt= (TextView) findViewById(R.id.textView_source);
      txt.setTypeface(font);
      
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_get_input, menu);
		return true;
	}

}
