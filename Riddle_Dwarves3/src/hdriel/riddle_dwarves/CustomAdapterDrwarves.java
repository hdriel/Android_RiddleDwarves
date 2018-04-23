package hdriel.riddle_dwarves;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterDrwarves extends BaseAdapter {

	 private Context context;
	 int size;
	 //int draft[];
	 riddleDrwarves riddledrwarves;
	 
	 
    public CustomAdapterDrwarves(Context context, riddleDrwarves riddledrwarves) {
        this.context = context;
        this.size = riddledrwarves.getSizeDrwaves();
        this.riddledrwarves = riddledrwarves;
        //draft = new int[size];
        for(int i = 0; i < size; i++){
        	//draft[i] = i;
        }
 }
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
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
           gridView = inflater.inflate(R.layout.tab_item_drwaft, parent, false);	                
       } 
       else {
           gridView = (View) convertView;
       }

       // get reference to view in tab_item
       TextView numberDrwaft = (TextView) gridView.findViewById(R.id.number_drwaft);
       ImageView imageDrwaft = (ImageView) gridView.findViewById(R.id.image_hatDrwaft);
       
       // set value into view in tab_item
       numberDrwaft.setText("" + (position + 1));     
       imageDrwaft.setBackgroundResource(R.drawable.hat_icon);
              
       if(riddledrwarves.getClickedDrwaft(position))
    	   gridView.setBackgroundColor(Color.rgb(204, 255, 204));  // ירוק בהיר            
    	else 
    		gridView.setBackgroundColor(0);
      
       return gridView;
   }
	
}

