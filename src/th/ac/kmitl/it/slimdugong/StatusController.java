package th.ac.kmitl.it.slimdugong;

import java.util.Date;

import android.R.string;

public class StatusController {
	
	private static Date lastConsumeDate;
	private static Date lastExerciseDate;
	
	private static int todayEnergyEat;
	private static int todayEnergyBurn;
	
	private static Date currentDate;
	
	public static void prepare(){
		currentDate = new Date();
		lastConsumeDate = SlimDugong.getInstance().getDatabase().getUserLastCosumeDate();
		lastExerciseDate = SlimDugong.getInstance().getDatabase().getUserLastExerciseDate();
	}
	
	private static long difference(Date d1, Date d2){
		return (d2.getTime()-d1.getTime())/1000;
	}
	
	private static long difference(Date d1){
		return difference(currentDate, d1);
	}
	
	public static int getCurrentConsumeStatus(){
		long hour = difference(lastConsumeDate)/60;
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
	
	public static int getCurrentExerciseStatus(){
		long hour = difference(lastExerciseDate)/60;
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
	
	public static String getCorrentStatusText(){
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
