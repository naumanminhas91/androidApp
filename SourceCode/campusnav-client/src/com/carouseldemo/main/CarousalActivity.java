package com.carouseldemo.main;

import java.text.DecimalFormat;

import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;
import com.carouseldemo.controls.CarouselAdapter.OnItemClickListener;
import com.carouseldemo.controls.CarouselAdapter.OnItemSelectedListener;
import com.carouseldemo.controls.CarouselItem;
import com.example.hellomap.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;


public class CarousalActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainx);


        
        Carousel carousel = (Carousel)findViewById(R.id.carousel);
        carousel.setOnItemClickListener(new OnItemClickListener(){

			public void logHeap() {
		        Double allocated = new Double(Debug.getNativeHeapAllocatedSize())/new Double((1048576));
		        Double available = new Double(Debug.getNativeHeapSize())/1048576.0;
		        Double free = new Double(Debug.getNativeHeapFreeSize())/1048576.0;
		        DecimalFormat df = new DecimalFormat();
		        df.setMaximumFractionDigits(2);
		        df.setMinimumFractionDigits(2);

		       System.out.println( "debug. =================================");
		       System.out.println( "debug.heap native: allocated " + df.format(allocated) + "MB of " + df.format(available) + "MB (" + df.format(free) + "MB free)");
		       System.out.println( "debug.memory: allocated: " + df.format(new Double(Runtime.getRuntime().totalMemory()/1048576)) + "MB of " + df.format(new Double(Runtime.getRuntime().maxMemory()/1048576))+ "MB (" + df.format(new Double(Runtime.getRuntime().freeMemory()/1048576)) +"MB free)");
		    }
			
			public void onItemClick(CarouselAdapter<?> parent, View view,
					int position, long id) {	
				
				String name= ((CarouselItem)parent.getChildAt(position)).getName();
				
				System.out.println(name);
				if (name.compareTo("Map") == 0)
				{
					logHeap();					
					//i.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) ;
					
			        Intent i  = new Intent( CarousalActivity.this, MainActivity.class );
					startActivity(i);
				}
				else if (name.compareTo("Settings")==0)
				{
					logHeap();
					startActivity(new Intent( CarousalActivity.this, OptionsActivity.class));
				}
				else if (name.compareTo("About")==0)
				{
					startActivity(new Intent( CarousalActivity.this, AboutActivity.class));
				}
				else if (name.compareTo("Help")==0)
				{
					startActivity(new Intent( CarousalActivity.this, HelpActivity.class));
				}
				else if (name.compareTo("Emergency")==0)
				{
					startActivity( new Intent( CarousalActivity.this, EmergencyActivity.class));
				}
				 
//				Toast.makeText(CarousalActivity.this, 
//						String.format("%s has been clicked", 
//						((CarouselItem)parent.getChildAt(position)).getName()), 
//						Toast.LENGTH_SHORT).show();				
			}
        	
        });

        carousel.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(CarouselAdapter<?> parent, View view,
					int position, long id) {
				
		        final TextView txt = (TextView)(findViewById(R.id.selected_item));
		        
				switch(position){
				case 0:
					txt.setText("The cat (Felis catus), also known as the domestic cat or housecat to distinguish it from other felids and felines, is a small, usually furry, domesticated, carnivorous mammal that is valued by humans for its companionship and for its ability to hunt vermin and household pests. Cats have been associated with humans for at least 9,500 years, and are currently the most popular pet in the world. Owing to their close association with humans, cats are now found almost everywhere in the world.");
					break;
				case 1:
					txt.setText("The hippopotamus (Hippopotamus amphibius), or hippo, from the ancient Greek for \"river horse\" (ἱπποπόταμος), is a large, mostly herbivorous mammal in sub-Saharan Africa, and one of only two extant species in the family Hippopotamidae (the other is the Pygmy Hippopotamus.) After the elephant, the hippopotamus is the third largest land mammal and the heaviest extant artiodactyl.");
					break;
				case 2:
					txt.setText("A monkey is a primate, either an Old World monkey or a New World monkey. There are about 260 known living species of monkey. Many are arboreal, although there are species that live primarily on the ground, such as baboons. Monkeys are generally considered to be intelligent. Unlike apes, monkeys usually have tails. Tailless monkeys may be called \"apes\", incorrectly according to modern usage; thus the tailless Barbary macaque is called the \"Barbary ape\".");
					break;
				case 3:
					txt.setText("A mouse (plural: mice) is a small mammal belonging to the order of rodents. The best known mouse species is the common house mouse (Mus musculus). It is also a popular pet. In some places, certain kinds of field mice are also common. This rodent is eaten by large birds such as hawks and eagles. They are known to invade homes for food and occasionally shelter.");
					break;
				case 4:
					txt.setText("The giant panda, or panda (Ailuropoda melanoleuca, literally meaning \"black and white cat-foot\") is a bear native to central-western and south western China.[4] It is easily recognized by its large, distinctive black patches around the eyes, over the ears, and across its round body. Though it belongs to the order Carnivora, the panda's diet is 99% bamboo.");
					break;
				case 5:
					txt.setText("Rabbits (or, colloquially, bunnies) are small mammals in the family Leporidae of the order Lagomorpha, found in several parts of the world. There are eight different genera in the family classified as rabbits, including the European rabbit (Oryctolagus cuniculus), cottontail rabbits (genus Sylvilagus; 13 species), and the Amami rabbit (Pentalagus furnessi, an endangered species on Amami Ōshima, Japan)");
					break;
				}
				
			}

			public void onNothingSelected(CarouselAdapter<?> parent) {
			}
        	
        }
        );
        
        TextView txt = (TextView) findViewById(R.id.selected_item);  
        Typeface font = Typeface.createFromAsset(getAssets(), "CandelaBook.otf");  
        txt.setTypeface(font); 
        
    }
    
    
}
