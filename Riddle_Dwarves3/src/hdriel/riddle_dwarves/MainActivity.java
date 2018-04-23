package hdriel.riddle_dwarves;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;

@SuppressLint("NewApi") 
public class MainActivity extends ActionBarActivity {

	Menu menu;
	GridView gridview, gridDrwafts;
	Button btnReset;
	TextView title;
	MediaPlayer mplayWin, mClick;
	ImageView info, help;
	AudioManager audio;
	CustomAdapter adapter;
	CustomAdapterDrwarves adapterD;
	riddleDrwarves riddle_drwarves;
	PopupWindow popUpWindowInfo;
	HorizontalScrollView hsv;
	Context c;	
	Handler mHandler = new Handler();
	Handler handler = new Handler();
	Runnable runnable;
	TooltipWindow tipWindow;
	SharedPreferences sharedPref;
	final String SAVE = "save data", 
			     STATE_LAMPS = "the state of the lamps",
			     SOUND = "sound of the app",
			     START = "check if you need to reset the board";
	int size , index_j = 0;
	boolean sound , start, A = false,B = false, inProcess = false , doubleBackToExitPressedOnce , getHelp = false;	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tipWindow = new TooltipWindow(MainActivity.this);
        riddle_drwarves = new riddleDrwarves();
        size = riddle_drwarves.getSizeLamps();
        mplayWin      =  MediaPlayer.create(this, R.raw.winner);
        c = this;
        
        sharedPref = getSharedPreferences(SAVE, MODE_PRIVATE);            
        int count = 0;
	    for (int i = 0; i < size; i++){    
	    	riddle_drwarves.setStateLamp(i,sharedPref.getBoolean("BooleanValue_" + count, false));	    				
	    	count++;
	    }	
	    count = 0;
	    for (int i = 0; i < size; i++){    
	    	riddle_drwarves.setclickDrwaft(i,sharedPref.getBoolean("BooleanValueDwarft_" + count, false));	    				
	    	count++;
	    }	
	    
	    sound         = sharedPref.getBoolean(SOUND, true);
	    start         = sharedPref.getBoolean(START, false);
	    
	    info          = (ImageView)findViewById(R.id.image_info);
	    help          = (ImageView)findViewById(R.id.image_help);
        btnReset      = (Button)   findViewById(R.id.btn_reset);
        title         = (TextView) findViewById(R.id.title);
        gridview      = (GridView) findViewById(R.id.gridView_soldiers);
        gridDrwafts   = (GridView) findViewById(R.id.gridView_drwarves);
        hsv           = (HorizontalScrollView) findViewById(R.id.HorizontalScrollView_drwafr);
        
        adapter       = new CustomAdapter(c, riddle_drwarves);
        adapterD      = new CustomAdapterDrwarves(c, riddle_drwarves);
        gridview.setNumColumns(10);
        gridview.setAdapter(adapter);
        gridDrwafts.setAdapter(adapterD);
        
        
        runnable = new Runnable() {
		    public void run() {
		    	for(int i = 0; i < riddle_drwarves.getSizeDrwaves(); i++)
	    		{
	    			if((i + 1) % (index_j + 1) == 0)
	        			riddle_drwarves.click(i);
	    		}
	        	riddle_drwarves.clickDrwaft(index_j);
	    		View viewItem = gridDrwafts.getChildAt(index_j);
	    		viewItem.setBackgroundColor(Color.rgb(204, 255, 204));
	    		gridview.setAdapter(new CustomAdapter(c, riddle_drwarves));
	    		index_j++;
	    		
	    		int index = index_j;
	    		hsv.scrollTo((index)*115, 0);
	    		
	    		Log.d("EXECUTE Runnable: ", "start loop " + index_j); 
	    		
	    		if(index_j < riddle_drwarves.getSizeDrwaves())
	    			if(index_j >= 49)
	    				handler.postDelayed(runnable, 400);
	    			else if(index_j >= 20)
	    				handler.postDelayed(runnable, 600);
	    			else 
	    				handler.postDelayed(runnable, 1000);
	    		else {
	    			inProcess = false;
	    		}
		    }
		};
		
		
        title.setOnLongClickListener(new OnLongClickListener() { 
            @Override
            public boolean onLongClick(View v) {
            	//if(sound) mChit.start();
            	getHelp = true;
            	/*
            	riddle_drwarves.getAnswer();
		        adapter       = new CustomAdapter(c, riddle_drwarves);
		        gridview.setAdapter(adapter);
		        Toast.makeText(c, "הפעלת צ\'יט לניצחון.",Toast.LENGTH_SHORT).show();
                */
                return true;
            }
        });
        
        info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) 
			{
				if(getHelp){
					AlertDialog builder = new AlertDialog.Builder(c).create();
	                builder.setTitle(getResources().getString(R.string.title_description));
	                builder.setMessage( getResources().getString(R.string.description));
	                builder.setButton(AlertDialog.BUTTON1, getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int id) { } }); 
	                builder.setButton(AlertDialog.BUTTON2, getResources().getString(R.string.SOLVE), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int id) {
					    	  help.setVisibility(View.VISIBLE);
					
					      }}); 
	                builder.show();
				}
				else {
					AlertDialog builder = new AlertDialog.Builder(c).create();
	                builder.setTitle(getResources().getString(R.string.title_description));
	                builder.setMessage( getResources().getString(R.string.description));
	                builder.setButton(AlertDialog.BUTTON1, getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
					      public void onClick(DialogInterface dialog, int id) { } }); 
	                builder.show();
				}
				
			}
		});
        
        help.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TextView msg = new TextView(c);
				msg.setText(Html.fromHtml(getResources().getString(R.string.helpTip)));
				AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle(getResources().getString(R.string.title_helpTip));
                builder.setMessage(getResources().getString(R.string.helpTip));
                builder.setPositiveButton(getResources().getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
			}
		});

        gridDrwafts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				if(A && B)
				{
					if(!start)
					{
						Toast.makeText(c, getResources().getString(R.string.chit), Toast.LENGTH_SHORT).show();
						inProcess = true;
						riddle_drwarves = new riddleDrwarves();
						adapter = new CustomAdapter(c, riddle_drwarves);
	            		gridview.setAdapter(adapter);

	            		handler.post(runnable);	
	            		start = true;
	            		
	            		Toast.makeText(c, getResources().getString(R.string.well_done), Toast.LENGTH_SHORT).show();
	            		if(sound) mplayWin.start();
					}
				}
				
				if(position == 0){ 
					A = true;
					Toast.makeText(c, getResources().getString(R.string.A), Toast.LENGTH_SHORT).show();
				}
				if(position == 1){  
					B = true;
					Toast.makeText(c, getResources().getString(R.string.B), Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
        
        
        gridDrwafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
            	if(!start)
            	{
            		riddle_drwarves.clickDrwaft(position);
            		gridDrwafts.setAdapter(new CustomAdapterDrwarves(c, riddle_drwarves));
            		
            		/*
            		if(riddle_drwarves.getClickedDrwaft(position))
                 	   v.setBackgroundColor(Color.rgb(204, 255, 204));  // ירוק בהיר            
                	else 
                	   v.setBackgroundColor(0);
                	*/
                	
            		for(int i = 0; i < size; i++){
                		
            			//if(position == 0) {if(isPrime(i+1) && i >= 1) riddle_drwarves.click(i);} else
                		if((i + 1) % (position + 1) == 0) riddle_drwarves.click(i);
                	}
            		
                	adapter = new CustomAdapter(c, riddle_drwarves);
            		gridview.setAdapter(adapter);
            		
            		if(riddle_drwarves.checkAnswer())
    	            {

    	            	// stop clicks on items
    	            	//gridview.setEnabled(false);
    	            	   	        	    
    	            	Toast.makeText(c, getResources().getString(R.string.well_done), Toast.LENGTH_SHORT).show();
    	        	    //if(sound) mplayWin.start();
    	        	    
    	        	    btnReset.setText( getResources().getString(R.string.silent_win_sound));
    	            }
            	}
            	else{
            		Toast.makeText(c, getResources().getString(R.string.reset), Toast.LENGTH_SHORT).show();
            	}
            }
        });
        
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				String str = "";
				int count = 0;
				
				for (int i = 0; i <= position; i++)
				{
					if(i < riddle_drwarves.getSizeDrwaves() && (position+1) % (i+1) == 0)
					{
						str += "" + (i+1) + ", ";
						count++;
					}
				}
				if(position == 0)
				{
					String res = "1 =" + ": [1] (" + 1 + ")";
					if(!tipWindow.isTooltipShown())
						tipWindow.showToolTip(v, res);
				}
				else
				{
					String res = "" + (position + 1) + ": [" + str.substring(0,str.length()-2) +"]" + "(" +count + ")";
					if(!tipWindow.isTooltipShown())
						tipWindow.showToolTip(v, res);
				}
				
				return true;
			}
        	
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	start = riddle_drwarves.checkAnswer();
            	if(inProcess)
            		Toast.makeText(c, getResources().getString(R.string.wait_to_drwaft), Toast.LENGTH_SHORT).show();
            	else if(!start)
            	{
            		riddle_drwarves.click(position);
            		adapter = new CustomAdapter(c, riddle_drwarves);
            		gridview.setAdapter(adapter);
            		start = riddle_drwarves.checkAnswer();
    	            if(start)
    	            {
    	            	    	            	
    	            	// stop clicks on items
    	            	//gridview.setEnabled(false);
    	            	   	        	    
    	            	Toast.makeText(c, getResources().getString(R.string.well_done), Toast.LENGTH_SHORT).show();
    	        	    if(sound)
    	        	    	btnReset.setText(getResources().getString(R.string.silent_win_sound));
    	        	    else 
    	        	    	btnReset.setText(getResources().getString(R.string.reset_board));
    	        	    
    	        	    if(sound) mplayWin.start();
    	        	    
    	            }
            	}
            	else 
            		Toast.makeText(c, getResources().getString(R.string.reset), Toast.LENGTH_SHORT).show();
            }
        });
        
        
        btnReset.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				handler.removeCallbacks(runnable);
				inProcess = false;
				getHelp = false;
				if(mplayWin.isPlaying()) stopPlayingWin();
				
				//stopPlayingClick();
				//if(sound) mClick.start();
				help.setVisibility(View.GONE);
				btnReset.setText(getResources().getString(R.string.reset_board));
				AlertDialog.Builder builder1 = new AlertDialog.Builder(c);
				builder1.setMessage(getResources().getString(R.string.do_reset));
				builder1.setCancelable(true);

				builder1.setPositiveButton( getResources().getString(R.string.yes_sure), new DialogInterface.OnClickListener() 
					{
				        public void onClick(DialogInterface dialog, int id) 
				        {
				        	gridview.setEnabled(true);
				        	
				        	riddle_drwarves = new riddleDrwarves();
					        adapter       = new CustomAdapter(c, riddle_drwarves);
					        adapterD       = new CustomAdapterDrwarves(c, riddle_drwarves);
					        gridview.setAdapter(adapter);
					        gridDrwafts.setAdapter(adapterD);
					        start = false;
					        //stopPlayingClick();
					        //if(sound) mClick.start();
				            dialog.cancel();
				        }
				    });

					builder1.setNegativeButton( getResources().getString(R.string.no_sure),new DialogInterface.OnClickListener() 
					{
				        public void onClick(DialogInterface dialog, int id) 
				        {
				            dialog.cancel();
				        }
					});

					AlertDialog alert11 = builder1.create();
					alert11.show();
				}
		});
    }

    private void stopPlayingWin() {
        if (mplayWin != null) {
        	mplayWin.stop();
        	mplayWin.release();
        	mplayWin = null;
        	mplayWin =  MediaPlayer.create(this, R.raw.winner);
       }
    }
    
 /*  
    private void stopPlayingClick() {
        if (mClick != null) {
        	mClick.stop();
        	mClick.release();
        	mClick = null;
        	//mClick =  MediaPlayer.create(this, R.raw.beep);
       }
    }
 */   
	
    @Override
    protected void onPause(){
    	super.onPause();
    	
    	SharedPreferences sharedPref = getSharedPreferences(SAVE, MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPref.edit();
	     
	    editor.putBoolean(SOUND, sound);
	    editor.putBoolean(START, start);
	    int count = 0;
	    for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValue_" + count, riddle_drwarves.getStateLamp(j));
    		count++;
    	}
	    count = 0;
    	for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValueDwarft_" + count, riddle_drwarves.getClickedDrwaft(j));
    		count++;
    	}
	    
	    editor.commit();
	    
	    if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
	    handler.removeCallbacks(runnable);
	    inProcess = false;
	    
	    if(tipWindow != null && tipWindow.isTooltipShown())
	    	tipWindow.dismissTooltip();
    }
    
    @Override
	protected void onStop() {
		super.onStop();
		
		SharedPreferences sharedPref = getSharedPreferences(SAVE, MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPref.edit();
	     
	    editor.putBoolean(SOUND, sound);
	    editor.putBoolean(START, start);
	    int count = 0;
    	for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValue_" + count, riddle_drwarves.getStateLamp(j));
    		count++;
    	}
    	count = 0;
    	for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValueDwarft_" + count, riddle_drwarves.getClickedDrwaft(j));
    		count++;
    	}
	    
	    editor.commit();
	    
	    if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
	    handler.removeCallbacks(runnable);
	    inProcess = false;
	    
	    if(tipWindow != null && tipWindow.isTooltipShown())
	    	tipWindow.dismissTooltip();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		SharedPreferences sharedPref = getSharedPreferences(SAVE, MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPref.edit();
	     
	    editor.putBoolean(SOUND, sound);
	    editor.putBoolean(START, start);
	    int count = 0;
    	for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValue_" + count, riddle_drwarves.getStateLamp(j));
    		count++;
    	}
    	count = 0;
    	for(int j = 0; j < size; j++)
    	{
    		editor.putBoolean("BooleanValueDwarft_" + count, riddle_drwarves.getClickedDrwaft(j));
    		count++;
    	}
	    
	    editor.commit();
	    
	    if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
	    
	    if(tipWindow != null && tipWindow.isTooltipShown())
	    	tipWindow.dismissTooltip();
	    
	    handler.removeCallbacks(runnable);
	    inProcess = false;
	}
	
	boolean isPrime(int n) {
	    for(int i=2;i<n;i++) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
	
	private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;                       
        }
    };

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "לחץ על הכפתור BACK שוב כדי לצאת.", Toast.LENGTH_SHORT).show();
        mHandler.postDelayed(mRunnable, 2000);
    }
        
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        if(sound){
        	menu.findItem(R.id.action_volume).setTitle("השתק");
    		mplayWin.setVolume(1,1); 
    	}
    	else{
    		menu.findItem(R.id.action_volume).setTitle("צליל");
    		mplayWin.setVolume(1,1); 
    	}
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	MenuItem MenuItemVolume = menu.findItem(R.id.action_volume);
    	
    	switch (item.getItemId()) 
    	{  
        case R.id.action_settings:                
        	Toast.makeText(c, "לשימוש אישי לצורך הפרוייקט לפיתוח חשיבה מתמטית.", Toast.LENGTH_LONG).show();
     	return true;
        case R.id.action_volume:
        	sound = !sound;
        	if(sound){
        		MenuItemVolume.setTitle("השתק");
        		mplayWin.setVolume(1,1); 
        	}
        	else{
        		MenuItemVolume.setTitle("צליל");
        		mplayWin.setVolume(1,1); 
        	}
        default:        
     		return super.onOptionsItemSelected(item);
    	}
    }
}
