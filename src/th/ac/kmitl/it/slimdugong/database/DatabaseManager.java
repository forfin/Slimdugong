package th.ac.kmitl.it.slimdugong.database;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.SlimDugong;
import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import th.ac.kmitl.it.slimdugong.database.entity.Barcode;
import th.ac.kmitl.it.slimdugong.database.entity.Food;
import th.ac.kmitl.it.slimdugong.database.entity.FoodType;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import th.ac.kmitl.it.slimdugong.database.entity.local.User;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class DatabaseManager {
	
	public static final String KEY_TOTAL = "KEY_TOTAL";    
    private static final String URL_CHECK_UPDATE = "https://dl.dropboxusercontent.com/s/geo8qml81gbd0z9/version.txt";
    public static final String KEY_VERSION = "KEY_VERSION";
    public static final String KEY_ISLOADED = "KEY_ISLOADED";
    
    private Context mContext;
    private Activity activity;
    
    private SlimDugong app;
    
    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_CONSUME = "DATABASE_CONSUME";
	public static final String DATABASE_BARCODE = "DATABASE_BARCODE";
	public static final String DATABASE_EXERCISE = "DATABASE_EXERCISE";
	public static final String DATABASE_VERSION = "DATABASE_VERSION";
	
	private TinyDB user_preference;  
    private TinyDB consume_preference;  
    private TinyDB exercise_preference;
    private TinyDB version_preference;
    
    public DatabaseManager(Context mContext) {
    	app = SlimDugong.getInstance();
    	this.mContext = mContext;
    	this.user_preference = new TinyDB(mContext, DATABASE_USER, Context.MODE_PRIVATE);
    	this.consume_preference = new TinyDB(mContext, DATABASE_CONSUME, Context.MODE_PRIVATE);
    	this.exercise_preference = new TinyDB(mContext, DATABASE_EXERCISE, Context.MODE_PRIVATE);
    	this.version_preference = new TinyDB(mContext, DATABASE_VERSION, Context.MODE_PRIVATE);
	}
	
	public void doUpdate(Activity activity){
		this.activity = activity;
		UpdateTaskRunner runner = new UpdateTaskRunner();
		runner.execute();
	}
	
	public void loadDatabase() {
		loadDataFromSQLite();
	}
	
	private void loadDataFromSQLite(){
		SQLiteDatabaseHelper mDbHelper = new SQLiteDatabaseHelper(mContext);
		SQLiteDatabase db = mDbHelper.getSQLiteDatabase();
		
		Cursor c = db.query(Food.TABLE_NAME, null, null, null, null, null, null);		
		ArrayList<Food> foodList = new ArrayList<Food>();
		c.moveToPosition(-1);
		while (c.moveToNext()) {
			Food entity = new Food();
			entity.setFoodId(c.getInt(c.getColumnIndexOrThrow(Food.COLUMN_NAME_ENTRY_ID)));
			entity.setFoodName(c.getString(c.getColumnIndexOrThrow(Food.COLUMN_NAME_ENTRY_NAME)));
			entity.setFoodCal(c.getInt(c.getColumnIndexOrThrow(Food.COLUMN_NAME_ENTRY_CAL)));
			entity.setFoodTypeId(c.getInt(c.getColumnIndexOrThrow(Food.COLUMN_NAME_ENTRY_FOODTYPE_ID)));
			foodList.add(entity);
		}
		c.close();
		app.setFoodList(foodList);
		
		
		c = db.query(FoodType.TABLE_NAME, null, null, null, null, null, null);		
		ArrayList<FoodType> foodTypeList = new ArrayList<FoodType>();
		c.moveToPosition(-1);
		while (c.moveToNext()) {
			FoodType entity = new FoodType();
			entity.setFoodTypeId(c.getInt(c.getColumnIndexOrThrow(FoodType.COLUMN_NAME_ENTRY_ID)));
			entity.setFoodTypeName(c.getString(c.getColumnIndexOrThrow(FoodType.COLUMN_NAME_ENTRY_NAME)));
			foodTypeList.add(entity);
		}
		c.close();
		app.setFoodTypeList(foodTypeList);
		
		
		c = db.query(Barcode.TABLE_NAME, null, null, null, null, null, null);		
		ArrayList<Barcode> barcodeList = new ArrayList<Barcode>();
		c.moveToPosition(-1);
		while (c.moveToNext()) {
			Barcode entity = new Barcode();
			entity.setBarId(c.getInt(c.getColumnIndexOrThrow(Barcode.COLUMN_NAME_ENTRY_ID)));
			entity.setBarCode(c.getString(c.getColumnIndexOrThrow(Barcode.COLUMN_NAME_ENTRY_CODE)));
			entity.setFoodId(c.getInt(c.getColumnIndexOrThrow(Barcode.COLUMN_NAME_ENTRY_FOOD_ID)));
			barcodeList.add(entity);
		}
		c.close();
		app.setBarcodeList(barcodeList);
		
		
		c = db.query(Athletic.TABLE_NAME, null, null, null, null, null, null);		
		ArrayList<Athletic> athleticList = new ArrayList<Athletic>();
		c.moveToPosition(-1);
		while (c.moveToNext()) {
			Athletic entity = new Athletic();
			entity.setAthId(c.getInt(c.getColumnIndexOrThrow(Athletic.COLUMN_NAME_ENTRY_ID)));
			entity.setAthName(c.getString(c.getColumnIndexOrThrow(Athletic.COLUMN_NAME_ENTRY_NAME)));
			entity.setAthBph(c.getInt(c.getColumnIndexOrThrow(Athletic.COLUMN_NAME_ENTRY_BPH)));
			athleticList.add(entity);
		}
		c.close();
		app.setAthleticList(athleticList);
		
	}
	
	public Food getFoodById(Integer Id) {
		for(Food f : app.getFoodList()){
			if(f.getFoodId().equals(Id)){
				return f;
			}
		}
		return null;
	}
	
	public Athletic getAthleticById(Integer Id) {
		for(Athletic f : app.getAthleticList()){
			if(f.getAthId().equals(Id)){
				return f;
			}
		}
		return null;
	}
	
	public Barcode getBarcodebyBarCode(String barcode) {
		for(Barcode b : app.getBarcodeList()){
			if(b.getBarCode().equals(barcode)){
				return b;
			}
		}
		return null;
	}
	
	public void consumeCommit(Consume consume) {		
		int total = consume_preference.getInt(KEY_TOTAL);
		ArrayList<String> marray = new ArrayList<String>();		
		marray.add(total+"");
		marray.add(consume.getFoodId().toString());
		marray.add(SlimDugong.dateFormat.format(consume.getConsumeTime()));
		marray.add(consume.getFoodEnergy().toString());
		consume_preference.putList(total+"", marray);
		consume_preference.putInt(KEY_TOTAL, total+1);
		
		user_preference.putLong(User.KEY_LAST_CONSUME_DATE, consume.getConsumeTime().getTime());
		
	}
	
	public ArrayList<Consume> getAllConsume(){		
		ArrayList<Consume> res = new ArrayList<Consume>();
		int total = consume_preference.getInt(KEY_TOTAL);
		try {
			for(int i=0;i<total;i++){
				Consume consume = new Consume();
				ArrayList<String> marray = consume_preference.getList(i+"");
				consume.setConsumeId(Integer.valueOf(marray.get(0)));
				consume.setFoodId(Integer.valueOf(marray.get(1)));
				consume.setConsumeTime(SlimDugong.dateFormat.parse(marray.get(2)));			
				consume.setFoodEnergy(Integer.valueOf(marray.get(3)));		
				res.add(consume);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}
	
	public void setUserHeightWeight(int h, int w) {
		user_preference.putInt(User.KEY_HEIGHT, h);
		user_preference.putInt(User.KEY_WEIGHT, w);
	}
	
	public void exerciseCommit(Exercise exer) {		
		int total = exercise_preference.getInt(KEY_TOTAL);
		ArrayList<String> marray = new ArrayList<String>();		
		marray.add(total+"");
		marray.add(exer.getAthId().toString());
		marray.add(exer.getEnegyBurn().toString());
		marray.add(exer.getExerDuration().toString());
		marray.add(SlimDugong.dateFormat.format(exer.getExerTime()));
		exercise_preference.putList(total+"", marray);
		exercise_preference.putInt(KEY_TOTAL, total+1);		
		
		user_preference.putLong(User.KEY_LAST_EXERCISE_DATE, exer.getExerTime().getTime());
		
	}
	
	public ArrayList<Exercise> getAllExercise(){		
		ArrayList<Exercise> res = new ArrayList<Exercise>();
		int total = exercise_preference.getInt(KEY_TOTAL);
		try {
			for(int i=0;i<total;i++){
				Exercise exer = new Exercise();
				ArrayList<String> marray = exercise_preference.getList(i+"");
				exer.setExerId(Integer.valueOf(marray.get(0)));
				exer.setAthId(Integer.valueOf(marray.get(1)));
				exer.setEnegyBurn(Integer.valueOf(marray.get(2)));
				exer.setExerDuration(Integer.valueOf(marray.get(3)));
				exer.setExerTime(SlimDugong.dateFormat.parse(marray.get(4)));				
				res.add(exer);				
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}
	
	public void setUserName(String name) {
		user_preference.putString(User.KEY_NAME, name);		
	}
	
	public void setUserCharacter(Integer[] character) {
		user_preference.putListInt(User.KEY_CHARACTER, character);
	}
	
	public boolean isNoUser() {
		return user_preference.getString(User.KEY_NAME).equals("");
	}
	
	public ArrayList<Integer> getUserCharacter() {
		return user_preference.getListInt(User.KEY_CHARACTER);
	}
	
	public String getUserName() {
		return user_preference.getString(User.KEY_NAME);
	}
	
	public Integer getUserWeight() {
		return user_preference.getInt(User.KEY_WEIGHT);
	}
	
	public Integer getUserHeight() {
		return user_preference.getInt(User.KEY_HEIGHT);
	}
	
	public Date getUserLastCosumeDate() {
		return new Date(user_preference.getLong(User.KEY_LAST_CONSUME_DATE));
	}
	
	public Date getUserLastExerciseDate() {
		return new Date(user_preference.getLong(User.KEY_LAST_EXERCISE_DATE));
	}
	
	private class UpdateTaskRunner extends AsyncTask<String, String, String>{
		 
		 private String title;
		 private String message;
		 private int totalSize;
		 private int downloadedSize;
		 private ProgressDialog dialog;
		 
		 private InputStream getInputStream(String str_url) throws IOException{
			 URL url = new URL(str_url);
			 HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
			 
			 //set up some things on the connection
			 urlConnection.setRequestMethod("GET");
			 urlConnection.setDoOutput(true);
			 urlConnection.connect();
			 totalSize = urlConnection.getContentLength();
			 downloadedSize = 0;
			 return urlConnection.getInputStream();
		 }
		 
		 @Override
		 protected String doInBackground(String... params) {
			 String res = null;
			 try {
				 dialog.setMessage(mContext.getText(R.string.update_on_checking));;
				 InputStream inputStream = getInputStream(URL_CHECK_UPDATE);
				 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				 long version = Long.valueOf(bufferedReader.readLine());
				 if(version > version_preference.getLong(KEY_VERSION)){
					 dialog.dismiss();
					 
					 activity.runOnUiThread(new Runnable() {
						 public void run() {
							  dialog = new ProgressDialog(activity);
							  dialog.setTitle(R.string.update_action);
							  dialog.setIcon(R.drawable.ic_action_update);
							  dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
							  dialog.setMessage(mContext.getText(R.string.update_on_downloading));
							  dialog.setProgress(0);
							  dialog.setMax(totalSize);
							  dialog.show();
						 }
					 });
					 
					 String url_download = bufferedReader.readLine();
					 bufferedReader.close();
					 inputStream.close();					 
					 inputStream = getInputStream(url_download);
					 bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

					 
					 FileOutputStream fileOutput = mContext.openFileOutput(SQLiteDatabaseHelper.DATABASE_LASTEST, Context.MODE_PRIVATE);
					 byte[] buffer = new byte[1024];
						
					 int bufferLength = 0; //used to store a temporary size of the buffer
						
					 //now, read through the input buffer and write the contents to the file
					 while ( (bufferLength = inputStream.read(buffer)) > 0 ){
						 fileOutput.write(buffer, 0, bufferLength);
						 downloadedSize += bufferLength;
						 dialog.setProgress(downloadedSize);
					 }
					 bufferedReader.close();
					 inputStream.close();
					 fileOutput.close();
					 version_preference.putLong(KEY_VERSION, version);
					 version_preference.putBoolean(KEY_ISLOADED, false);
					 title = (String) mContext.getText(R.string.update_success) + " (" + version + ")";
				 }else{
					 title = (String) mContext.getText(R.string.update_already_latest) + " (" + version_preference.getLong(KEY_VERSION) + ")";
				 }
			 } catch (MalformedURLException e) {
				 title = (String) mContext.getText(R.string.defualt_title_error);
				 message = e.getMessage();
				 res = "";
			 } catch (IOException e) {
				 title = (String) mContext.getText(R.string.defualt_title_error);
				 message = e.getMessage();
				 res = "";
			 } catch (NumberFormatException e) {
				 title = (String) mContext.getText(R.string.defualt_title_error);
				 message = e.getMessage();
				 res = "";
			 }
			 
			 return res;
		 }
		 
		  @Override
		  protected void onPostExecute(String result) {
			  dialog.dismiss();
			  int icon = result==null?R.drawable.ic_action_success:R.drawable.ic_action_alert;
			  new AlertDialog.Builder(activity)
			  .setTitle(title)
			  .setIcon(icon)
			  .setMessage(message)
			  .setPositiveButton(R.string.defualt_ok, null)
			  .show();
			  
		 }
		  
		  @Override
		  protected void onPreExecute() {
			  dialog = new ProgressDialog(activity);
			  dialog.setTitle(R.string.update_action);
			  dialog.setIcon(R.drawable.ic_action_update);
			  dialog.show();
		 }
		  
		  @Override
		  protected void onProgressUpdate(String... text) {
			  dialog.setMessage(text[0]);
		  }
	}
	
}
