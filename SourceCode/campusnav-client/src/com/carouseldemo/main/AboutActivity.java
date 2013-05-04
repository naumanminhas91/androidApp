package com.carouseldemo.main;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class AboutActivity extends Activity{

	private ExpandableListView mExpandableList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		mExpandableList = (ExpandableListView)findViewById(R.id.expandableListView2);
		
		ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<String> arrayChildren = new ArrayList<String>();
        
        String[] children= getResources().getStringArray(R.array.about);
        String[] parents= getResources().getStringArray(R.array.about_parent);
        
        Parent parent = new Parent();
        parent.setTitle(parents[0]);
        arrayChildren = new ArrayList<String>();
        arrayChildren.add(children[0]);
        parent.setArrayChildren(arrayChildren);
        arrayParents.add(parent);
        
        parent= new Parent();
        parent.setTitle(parents[1]);
        arrayChildren = new ArrayList<String>();
        arrayChildren.add(children[1]);
        parent.setArrayChildren(arrayChildren);
        arrayParents.add(parent);
        
        parent = new Parent();
        parent.setTitle(parents[2]);
        arrayChildren = new ArrayList<String>();
        arrayChildren.add(children[2]);
        arrayChildren.add(children[3]);
        arrayChildren.add(children[4]);
        parent.setArrayChildren(arrayChildren);
        arrayParents.add(parent);
        
        mExpandableList.setAdapter(new MyCustomAdapter(this,arrayParents));
        
        
        TextView txt = (TextView) findViewById(R.id.about_title);  
        Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBold.otf");  
        txt.setTypeface(font); 
        
        
	}

	
	
}
