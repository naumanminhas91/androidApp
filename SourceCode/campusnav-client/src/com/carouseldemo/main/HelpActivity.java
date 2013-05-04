package com.carouseldemo.main;

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

public class HelpActivity extends Activity{

	private ExpandableListView mExpandableList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		mExpandableList = (ExpandableListView)findViewById(R.id.expandableListView1);
		
		ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<String> arrayChildren = new ArrayList<String>();
        
        String[] children= getResources().getStringArray(R.array.help);
        String[] parents= getResources().getStringArray(R.array.help_parent);
        
        for (int i = 0; i < children.length; i++){
            //for each "i" create a new Parent object to set the title and the children
            Parent parent = new Parent();
            parent.setTitle(parents[i]);
             
            
            arrayChildren = new ArrayList<String>();
            arrayChildren.add(children[i]);
            parent.setArrayChildren(arrayChildren);
 
            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }
 
        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(this,arrayParents));
 
        Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        
		TextView txt = (TextView) findViewById(R.id.textView1);  
        txt.setTypeface(font);
        
        txt = (TextView) findViewById(R.id.textView2);  
        txt.setTypeface(font);
        
		
	}


	
}
