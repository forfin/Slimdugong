																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																package th.ac.kmitl.it.slimdugong;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import th.ac.kmitl.it.slimdugong.R;
import th.ac.kmitl.it.slimdugong.custom.widget.FoodAdapter;
import th.ac.kmitl.it.slimdugong.database.DatabaseManager;
import th.ac.kmitl.it.slimdugong.database.entity.Barcode;
import th.ac.kmitl.it.slimdugong.database.entity.Food;
import th.ac.kmitl.it.slimdugong.database.entity.FoodType;
import th.ac.kmitl.it.slimdugong.database.entity.local.Consume;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class SelectDishActivity extends ActionBarActivity {	
	
	private DatabaseManager mDatabaseManager;
	private SlimDugong app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dish);
        app = SlimDugong.getInstance();
        
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        mDatabaseManager = app.getDatabase();
        
        Tab recent_tab = actionBar.newTab()
    			.setTag(-1)
                .setText(R.string.select_dish_recent)
                .setTabListener(new mTab());
    	actionBar.addTab(recent_tab);
        
        for(FoodType ft:app.getFoodTypeList()){
        	Tab tab = actionBar.newTab()
        			.setTag(ft.getFoodTypeId())
                    .setText(ft.getFoodTypeName())
                    .setTabListener(new mTab());
        	actionBar.addTab(tab);
        }       

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_dish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	if(item.getItemId() == R.id.action_select_dish_search){
    		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//start scanning
			scanIntegrator.initiateScan();
    	}
        return super.onOptionsItemSelected(item);
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			String scanFormat = scanningResult.getFormatName();
			
			Barcode b = mDatabaseManager.getBarcodebyBarCode(scanContent);
			
			if(null != b){
				
				Food food = mDatabaseManager.getFoodById(b.getFoodId());
				new DishSelectedDialogFragment(food).show(getSupportFragmentManager(), null);
				
			}else{
				
				new AlertDialog.Builder(this)
				.setTitle("Barcode not found")
				.setMessage("Barcode "+scanContent+" not found")
				.setPositiveButton("OK", null)
				.show();
				return;
				
			}
			
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
    }
    
    private ArrayList<Food> getRecentFoodList(){
    	ArrayList<Food> res = new ArrayList<Food>();
    	final Map<Food, Integer> list = new HashMap<Food, Integer>();
    	for(Consume c : mDatabaseManager.getAllConsume()){
			Food id = mDatabaseManager.getFoodById(c.getFoodId());
			if(list.containsKey(id)){
				list.put(id, list.get(id)+1);
			}else{
				list.put(id, 1);
			}				
		}					
		res.addAll(list.keySet());			
		Collections.sort(res, new Comparator<Food>() {
	        @Override
	        public int compare(Food  food1, Food  food2){		        	
	            return list.get(food2)-list.get(food1);
	        }
	    });
    	return res;
    }
    
    class mTab implements TabListener{

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
			ListView listDish = (ListView) findViewById(R.id.listDish);
			
			ArrayList<Food> toShowList = new ArrayList<Food>();
			
			if(tab.getTag().equals(-1)){
				for(Food food:getRecentFoodList()){
					toShowList.add(food);
				}	
			}else{
				for(Food food:app.getFoodList()){
					if(tab.getTag().equals(food.getFoodTypeId())){
						toShowList.add(food);
					}
				}	
			}						
			
			ArrayAdapter<Food> modeAdapter = new FoodAdapter(SelectDishActivity.this, toShowList);
	        listDish.setAdapter(modeAdapter);
	        listDish.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					// TODO Auto-generated method stub
					
					Food food = (Food) parent.getItemAtPosition(position);
					new DishSelectedDialogFragment(food).show(getSupportFragmentManager(), null);
					
				}
			});
			
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			
		}   	
    }
    
    public class DishSelectedDialogFragment extends DialogFragment {
    	
    	Food food;
    	
    	DishSelectedDialogFragment(Food food){
    		super();
    		this.food = food;
    	}
    	
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());            
            builder.setTitle(food.getFoodName())
            	   .setMessage(SelectDishActivity.this.getText(R.string.select_dish_dialog_energy) + " " + food.getFoodCal())
            	   .setIcon(R.drawable.ic_action_dish)
                   .setPositiveButton(R.string.defualt_ok, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                    	   Consume c = new Consume(Integer.valueOf(food.getFoodId()), new Date());
                    	   mDatabaseManager.consumeCommit(c);
                       }
                   })
                   .setNegativeButton(R.string.defualt_cancel, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    
    
}
