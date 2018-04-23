package hdriel.riddle_dwarves;

public class riddleDrwarves {

	boolean lamps[];        // the lamps 
	boolean answerLamps[];  // the final state lamps
	int size;               // the amount of the lamps
	int drwaves;            // the drwaves who click on the lamps
	boolean clicked[];
	final int SIZE = 100;
	
	// ctor
	public riddleDrwarves(int size, int drwaves){
		// set value in the variable
		if(size >= 0) this.size = size;
		else          this.size = SIZE;
		
		if(drwaves >= 0) this.drwaves = drwaves;
		else             this.drwaves = SIZE;
		clicked = new boolean[this.drwaves];
		for(int i = 0 ; i < this.drwaves ; i++ )
			clicked[i] = false;
		
		lamps = new boolean[this.size];
		answerLamps = new boolean[this.size];
		
		// reset lamps
		for(int i= 0;i < size; i++){
			lamps[i] = false;
			answerLamps[i] = false;
		}
		
		// get the answer of riddle drwarves
		for(int i = 1; i <= this.drwaves; i++){
			for(int j = 1; j <= this.size; j++){
				if(j % i == 0){
					answerLamps[j-1] = !answerLamps[j-1];
				}
			}
		}
	}
	// ctor
	public riddleDrwarves(){
		// set value in the variable
		this.size = SIZE;
		lamps = new boolean[this.size];
		answerLamps = new boolean[this.size];
		this.drwaves = SIZE;
		
		clicked = new boolean[this.drwaves];
		for(int i = 0 ; i < this.drwaves ; i++ )
			clicked[i] = false;
		
		// reset lamps
		for(int i= 0;i < size; i++){
			lamps[i] = false;
			answerLamps[i] = false;
		}
		
		// get the answer of riddle drwarves
		for(int i = 1; i <= this.drwaves; i++){
			for(int j = 1; j <= this.size; j++){
				if(j % i == 0){
					answerLamps[j-1] = !answerLamps[j-1];
				}
			}
		}
	}
	
	// click on the lamp in index i
	public void click(int i){
		if(i >= 0 && i < size)
			lamps[i] = !lamps[i];
	}
	
	// get if the lamp in on or off
	public boolean getStateLamp(int i) { 
		if(i >= 0 && i < size)
			return lamps[i]; 
		else 
			return false;
	}
	
	// get the array lamps
	public boolean[] getLampsArr(){ 
		return lamps;
	}
	
	public int getSizeDrwaves(){
		return drwaves;
	}
	// get the amount of the lamps
	public int getSizeLamps() {
		return size;
	}
	
	// check the answer of the user
	public boolean checkAnswer(){
		for(int i = 0; i < size; i++){
			if(lamps[i] != answerLamps[i])
				return false;
		}
		return true;
	}	
	
	
	public void setStateLamp(int i, boolean state){
		if(i >= 0 && i < size)
			lamps[i] = state;
	}
	
	public void getAnswer(){
		for(int i = 0; i < size; i++)
			lamps[i] = answerLamps[i];
	}
	
	public void setclickDrwaft(int i, boolean flag){
		if(i >= 0 && i < this.drwaves)
			clicked[i] = flag;
	}
	public void clickDrwaft(int i){
		if(i >= 0 && i < this.drwaves)
			clicked[i] = !clicked[i];
	}
	public boolean getClickedDrwaft(int i){
		if(i >= 0 && i < this.drwaves)
			return clicked[i];
		return true;
	}
	
	
}
