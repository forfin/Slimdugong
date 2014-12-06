package th.ac.kmitl.it.slimdugong.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import th.ac.kmitl.it.slimdugong.database.entity.Barcode;
import th.ac.kmitl.it.slimdugong.database.entity.Food;
import th.ac.kmitl.it.slimdugong.database.entity.FoodType;
import android.app.Application;

public class SlimDugong extends Application{
	
	private DatabaseManager database;
	private ArrayList<Food> foodList;
	private ArrayList<FoodType> foodTypeList;
	private ArrayList<Barcode> barcodeList;
	private ArrayList<Athletic> athleticList;
	
	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public DatabaseManager getDatabase() {
		return database;
	}

	public void setDatabase(DatabaseManager database) {
		this.database = database;
	}

	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<Food> foodList) {
		this.foodList = foodList;
	}

	public ArrayList<FoodType> getFoodTypeList() {
		return foodTypeList;
	}

	public void setFoodTypeList(ArrayList<FoodType> foodTypeList) {
		this.foodTypeList = foodTypeList;
	}

	public ArrayList<Barcode> getBarcodeList() {
		return barcodeList;
	}

	public void setBarcodeList(ArrayList<Barcode> barcodeList) {
		this.barcodeList = barcodeList;
	}

	public ArrayList<Athletic> getAthleticList() {
		return athleticList;
	}

	public void setAthleticList(ArrayList<Athletic> athleticList) {
		this.athleticList = athleticList;
	}
	
	
	
	
}