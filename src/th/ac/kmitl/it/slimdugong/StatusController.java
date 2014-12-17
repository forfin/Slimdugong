package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;
import java.util.Date;

import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;

public class StatusController {
	
	private static StatusController mInstance = null;
	
	private static Date lastConsumeDate;
	private static Date lastExerciseDate;
	
	private static Date currentDate;
	
	private StatusController(){
		
	}
	
	public int getTodayEnergyEat() {
		int todayEnergyEat = 0;
		ArrayList<Consume> consumes = SlimDugong.getInstance().getDatabase().getTodayConsume();
		for (Consume consume : consumes) {
			todayEnergyEat += consume.getFoodEnergy();
		}
		return todayEnergyEat;
	}
	
	public int getTodayEnergyBurn() {
		int todayEnergyBurn = 0;
		ArrayList<Exercise> exercises = SlimDugong.getInstance().getDatabase().getTodayExercise();
		for (Exercise exercise : exercises) {
			todayEnergyBurn += exercise.getEnegyBurn();
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
		lastConsumeDate = SlimDugong.getInstance().getDatabase().getLastCosumeDate();
		lastExerciseDate = SlimDugong.getInstance().getDatabase().getLastExerciseDate();
	}
	
	public static long difference(Date d1, Date d2){
		return (d2.getTime()-d1.getTime())/1000;
	}
	
	public static long difference(Date d1){
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
	
	public Integer getCorrentStatusIdString(){
		if(lastConsumeDate.after(lastExerciseDate)){
			int hungry = getCurrentConsumeStatus();
			switch (hungry) {
			case 0:
				return R.string.status_consume_full;
			case 1:
				return R.string.status_nothing_to_say;
			case 2:
				return R.string.status_consume_hungry;
			}
		}else{
			int Strong = getCurrentExerciseStatus();
			switch (Strong) {
			case 0:
				return R.string.status_exercise_strong;
			case 1:
				return R.string.status_nothing_to_say;
			case 2:
				return R.string.status_exercise_weak;
			}
		}
		return null;
	}

}
