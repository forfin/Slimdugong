package th.ac.kmitl.it.slimdugong.database;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.SlimDugong;
import th.ac.kmitl.it.slimdugong.database.entity.Athletic;
import th.ac.kmitl.it.slimdugong.database.entity.Barcode;
import th.ac.kmitl.it.slimdugong.database.entity.Food;
import th.ac.kmitl.it.slimdugong.database.entity.FoodType;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import th.ac.kmitl.it.slimdugong.database.entity.local.Exercise;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class DatabaseManager {		
    
    private static final String NAMESPACE = "http://sb/";
    private static final String DOMAIN = "http://192.168.1.114:8080";
    
    private static final String URL_FOODLIST_SERVICE = DOMAIN + "/FoodFacadeService/FoodFacade?WSDL";
    private static final String FOODLIST_SERVICE_GET_FOOD_LIST = "getFoodList";
    
    private static final String URL_FOODTYPE_SERVICE = DOMAIN + "/FoodTypeFacadeService/FoodTypeFacade?WSDL";
    private static final String FOODTYPE_SERVICE_GET_FOOD_LIST = "getFoodTypeList";
    
    private static final String URL_BARCCODE_SERVICE = DOMAIN + "/BarcodeFacadeService/BarcodeFacade?WSDL";
    private static final String BARCCODE_SERVICE_GET_BARCCODE_LIST = "getBarcodeList";    
    
    private static final String URL_ATHLETIC_SERVICE = DOMAIN + "/AthleticFacadeService/AthleticFacade?WSDL";
    private static final String ATHLETIC_SERVICE_GET_BARCCODE_LIST = "getAthleticList";    
    
    private static final int NUM_ATTRIBUTE_FOOD = 5;
    private static final int NUM_ATTRIBUTE_FOOD_TYPE = 2;
    private static final int NUM_ATTRIBUTE_BARCODE = 3;
    private static final int NUM_ATTRIBUTE_ATHLETIC = 3;
    private static final String TOTAL = "TOTAL";
    
    private static final String SUCESS = "SUCESS";
    
    private boolean isDataAlreadyLoaded = false;
    
    private SlimDugong app;
    private ProgressDialog dialog;
    
    private TinyDB food_list_preference;
    private TinyDB food_type_preference;
    private TinyDB barcode_preference;
    private TinyDB athletic_preference;
    private TinyDB consume_preference;  
    private TinyDB exercise_preference;  
    
    public DatabaseManager(SlimDugong app,
    		TinyDB food_list_preference,
    		TinyDB food_type_preference,
    		TinyDB barcode_preference,
    		TinyDB athletic_preference,
    		TinyDB consume_preference,
    		TinyDB exercise_preference) {
    	this.app = app;
    	this.food_list_preference = food_list_preference;
    	this.food_type_preference = food_type_preference;
    	this.barcode_preference = barcode_preference;
    	this.athletic_preference = athletic_preference;
    	this.consume_preference = consume_preference;
    	this.exercise_preference = exercise_preference;
	}
	
	public void doUpdate(ProgressDialog dialog){
		this.dialog = dialog;
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}
	
	public void loadDatabase() {
		if(!isDataAlreadyLoaded){
			loadDataFromSQLite();
		}
	}
	
	private void loadDataFromSQLite(){
		SQLiteDatabaseHelper mDbHelper = new SQLiteDatabaseHelper(app);
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
		
		isDataAlreadyLoaded = true;
		
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
		int total = consume_preference.getInt(TOTAL);
		ArrayList<String> marray = new ArrayList<String>();		
		marray.add(total+"");
		marray.add(consume.getFoodId().toString());
		marray.add(SlimDugong.dateFormat.format(consume.getConsumeTime()));
		consume_preference.putList(total+"", marray);
		consume_preference.putInt(TOTAL, total+1);
		
	}
	
	public ArrayList<Consume> getAllConsume(){		
		ArrayList<Consume> res = new ArrayList<Consume>();
		int total = consume_preference.getInt(TOTAL);
		try {
			for(int i=0;i<total;i++){
				Consume consume = new Consume();
				ArrayList<String> marray = consume_preference.getList(i+"");
				consume.setConsumeId(Integer.valueOf(marray.get(0)));
				consume.setFoodId(Integer.valueOf(marray.get(1)));
				consume.setConsumeTime(SlimDugong.dateFormat.parse(marray.get(2)));				
				res.add(consume);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return res;
	}
	
	public void exerciseCommit(Exercise exer) {		
		int total = exercise_preference.getInt(TOTAL);
		ArrayList<String> marray = new ArrayList<String>();		
		marray.add(total+"");
		marray.add(exer.getAthId().toString());
		marray.add(exer.getEnegyBurn().toString());
		marray.add(exer.getExerDuration().toString());
		marray.add(SlimDugong.dateFormat.format(exer.getExerTime()));
		exercise_preference.putList(total+"", marray);
		exercise_preference.putInt(TOTAL, total+1);				
	}
	
	public ArrayList<Exercise> getAllExercise(){		
		ArrayList<Exercise> res = new ArrayList<Exercise>();
		int total = exercise_preference.getInt(TOTAL);
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
	
	 public boolean isDataAlreadyLoaded() {
		return isDataAlreadyLoaded;
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String>{
		 
		 private String resp;
		 
		 private SoapSerializationEnvelope callService(String ServiceName, String MethodName) throws HttpResponseException, IOException, XmlPullParserException{
			 publishProgress(app.getString(R.string.update_on_fetching)); // Calls onProgressUpdate()
			 SoapObject request = new SoapObject(NAMESPACE, MethodName);
			 SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			 envelope.setOutputSoapObject(request);
			
			 HttpTransportSE androidHttpTransport = new HttpTransportSE(ServiceName);

			 androidHttpTransport.call(NAMESPACE + MethodName, envelope); // will make exceptions
			 
			 return envelope;
		 }
		 
		 private void getPatternData(SoapSerializationEnvelope envelope, TinyDB preferece, int num_attribute ) throws SoapFault{
			 if (envelope.bodyIn != null) {
				 publishProgress(app.getString(R.string.update_on_applying));			 
				 Vector<SoapPrimitive> result=(Vector<SoapPrimitive>)envelope.getResponse();
				 
				 ArrayList<String> res;				 
				 int num = result.size()/num_attribute;					 

				 for(int i=0;i<num;i++){
					 res = new ArrayList<String>();
					 for(int j=0;j<num_attribute;j++){
						 int index = j+(i*num_attribute);
						 res.add(result.get(index).toString());
					 }						 
					 preferece.putList(i+"", res);
				 }
				 preferece.putInt(TOTAL, num);
			 }
		 }
		 
		 private void getFoodList() throws HttpResponseException, IOException, XmlPullParserException{			 
			 SoapSerializationEnvelope envelope = callService(URL_FOODLIST_SERVICE, FOODLIST_SERVICE_GET_FOOD_LIST);
			 getPatternData(envelope, food_list_preference, NUM_ATTRIBUTE_FOOD);			 
		 }
		 
		 private void getFoodTypeList() throws HttpResponseException, IOException, XmlPullParserException{			 
			 SoapSerializationEnvelope envelope = callService(URL_FOODTYPE_SERVICE, FOODTYPE_SERVICE_GET_FOOD_LIST);
			 getPatternData(envelope, food_type_preference, NUM_ATTRIBUTE_FOOD_TYPE);			 
		 }
		 
		 private void getBarcodeList() throws HttpResponseException, IOException, XmlPullParserException{			 
			 SoapSerializationEnvelope envelope = callService(URL_BARCCODE_SERVICE, BARCCODE_SERVICE_GET_BARCCODE_LIST);
			 getPatternData(envelope, barcode_preference, NUM_ATTRIBUTE_BARCODE);			 
		 }
		 
		 private void getAthleticList() throws HttpResponseException, IOException, XmlPullParserException{			 
			 SoapSerializationEnvelope envelope = callService(URL_ATHLETIC_SERVICE, ATHLETIC_SERVICE_GET_BARCCODE_LIST);
			 getPatternData(envelope, athletic_preference, NUM_ATTRIBUTE_ATHLETIC);			 
		 }
		 
		 @Override
		 protected String doInBackground(String... params) {	
			 try {				 
				 getFoodList();		
				 getFoodTypeList();
				 getBarcodeList();
				 getAthleticList();
				 resp = SUCESS;
				 } 
			 catch (Exception e) {
				 e.printStackTrace();
				 resp = e.getMessage();
				 }
			 return resp;
			 }
		 
		  @Override
		  protected void onPostExecute(String result) {
			  // execution of result of Long time consuming operation
			  // In this example it is the return value from the web service
			  if(SUCESS.equals(result)){
				  loadDataFromSQLite();
				  dialog.dismiss();
			  }else{
				  dialog.setMessage(result);
			  }
//			  Set<String> res = preferences.getStringSet("1", new HashSet<String>());
//			  textView.setText(res.toString());
			  }
		  
		  @Override
		  protected void onPreExecute() {
			  // Things to be done before execution of long running operation. For
			  // example showing ProgessDialog
			  }
		  
		  @Override
		  protected void onProgressUpdate(String... text) {
			  dialog.setMessage(text[0]);
			  // Things to be done while execution of long running operation is in
			  // progress. For example updating ProgessDialog
		  }
		}

}
