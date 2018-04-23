package hdriel.riddle_dwarves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

	 private Context context;
	 riddleDrwarves riddledrwarves;
	 	     
    public CustomAdapter(Context context, riddleDrwarves riddledrwarves) {
        this.context = context;
        this.riddledrwarves = riddledrwarves;
 }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return riddledrwarves.getSizeLamps();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	 public View getView(int position, View convertView, ViewGroup parent) {

       View gridView;

       if (convertView == null) // if it's not recycled, initialize some attributes
       {

           gridView = new View(context);
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // get layout from mobile.xml
           gridView = inflater.inflate(R.layout.tab_item, parent, false);	                
       } 
       else {
           gridView = (View) convertView;
       }

       // get reference to view in tab_item
       TextView numberLamp = (TextView) gridView.findViewById(R.id.number_lamp);
       ImageView imageLamp = (ImageView) gridView.findViewById(R.id.image_lamp);
       
       // set value into view in tab_item
       numberLamp.setText("" + (position + 1));
       if(riddledrwarves.getStateLamp(position))
    	   imageLamp.setBackgroundResource(R.drawable.on);
       else
    	   imageLamp.setBackgroundResource(R.drawable.off);
           
       return gridView;
   }
	
}

