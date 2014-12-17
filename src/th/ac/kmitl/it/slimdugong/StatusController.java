package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.R.string;
import android.text.format.DateUtils;

public class StatusController {
	
	private static StatusController mInstance = null;
	
	private static Date lastConsumeDate;
	private static Date lastExerciseDate;
	
	private static Date currentDate;
	
	private StatusController(){
		
	}
	
	public int getTodayEnergyEat() {
		int todayEnergyEat = 0;
		ArrayList<Consume> consumes = SlimDugong.getInstance().getDatabase().getAllConsume();
		for (Consume consume : consumes) {
			if(DateUtils.isToday(consume.getConsumeTime().getTime())){
				todayEnergyEat += consume.getFoodEnergy();
			}
		}
		return todayEnergyEat;
	}
	
	public int getTodayEnergyBurn() {
		int todayEnergyBurn = 0;
		ArrayList<Exercise> exercises = SlimDugong.getInstance().getDatabase().getAllExercise();
		for (Exercise exercise : exercises) {
			if(DateUtils.isToday(exercise.getExerTime().getTime())){
				todayEnergyBurn += exercise.getEnegyBurn();
			}
		}
		return todayEnergyBurn;
	}
	
	public static StatusController getInstance() {
		if(null == mInstance){
			mInstance = new StatusController();
		}
		prepare();
		return mInstance;
	}
	
	private static void prepare(){
		currentDate = new Date();
		lastConsumeDate = SlimDugong.getInstance().getDatabase().getUserLastCosumeDate();
		lastExerciseDate = SlimDugong.getInstance().getDatabase().getUserLastExerciseDate();
	}
	
	private static long difference(Date d1, Date d2){
		return (d2.getTime()-d1.getTime())/1000;
	}
	
	private static long difference(Date d1){
		return difference(d1, currentDate);
	}
	
	public int getCurrentConsumeStatus(){
		long hour = difference(lastConsumeDate)/3600;
//		System.err.println("lastConsumeDate = " + lastConsumeDate.toLocaleString());
//		System.err.println("currentDate = " + currentDate.toLocaleString());
//		System.err.println("difference(lastConsumeDate) = " + difference(lastConsumeDate));
		if(hour<6){
			// Fully
			return 0;
		}else if(hour<12){
			// Normal
			return 1;
		}else{
			// Hungry
			return 2;
		}
	}
	
	public int getCurrentExerciseStatus(){
		long hour = difference(lastExerciseDate)/3600;
		if(hour<24){
			// Strong
			return 0;
		}else if(hour<48){
			// Normal
			return 1;
		}else{
			// weak
			return 2;
		}
	}
	
	public String getCorrentStatusText(){
		if(lastConsumeDate.after(lastExerciseDate)){
			int hungry = getCurrentConsumeStatus();
			switch (hungry) {
			case 0:
				return "Full";
			case 1:
				return "Normal";
			case 2:
				return "Hungry";
			}
		}else{
			int Strong = getCurrentExerciseStatus();
			switch (Strong) {
			case 0:
				return "Strong";
			case 1:
				return "Normal";
			case 2:
				return "Weak";
			}
		}
		return null;
	}

}
